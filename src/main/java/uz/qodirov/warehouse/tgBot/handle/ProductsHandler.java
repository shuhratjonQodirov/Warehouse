package uz.qodirov.warehouse.tgBot.handle;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import uz.qodirov.warehouse.dto.res.StockProjection;
import uz.qodirov.warehouse.model.Product;
import uz.qodirov.warehouse.model.User;
import uz.qodirov.warehouse.repository.ProductRepository;
import uz.qodirov.warehouse.repository.StockRepository;
import uz.qodirov.warehouse.repository.UserRepository;
import uz.qodirov.warehouse.service.ProductService;
import uz.qodirov.warehouse.tgBot.model.BotUser;
import uz.qodirov.warehouse.tgBot.repository.BotUserRepository;
import uz.qodirov.warehouse.tgBot.service.GenerateProductList;
import uz.qodirov.warehouse.tgBot.state.BotConstance;
import uz.qodirov.warehouse.tgBot.state.BotState;

import uz.qodirov.warehouse.enums.RoleName;
import uz.qodirov.warehouse.dto.req.DistributionInfoReqDto;
import uz.qodirov.warehouse.dto.res.DistributionInfoResDto;
import uz.qodirov.warehouse.model.Kindergarten;
import uz.qodirov.warehouse.model.Warehouse;
import uz.qodirov.warehouse.repository.KindergartenRepository;
import uz.qodirov.warehouse.repository.WarehouseRepository;
import uz.qodirov.warehouse.service.DistributionService;
import uz.qodirov.warehouse.utils.ApiResponse;

import java.time.YearMonth;
import java.util.List;

@Component
public class ProductsHandler extends BaseHandler {
    private final UserRepository userRepository;
    private final MainMenuHandler mainMenuHandler;
    private final ProductService productService;
    private final ProductRepository productRepository;
    private final StockRepository stockRepository;
    private final GenerateProductList generateProductList;
    private final DistributionService distributionService;
    private final KindergartenRepository kindergartenRepo;
    private final WarehouseRepository warehouseRepo;

    public ProductsHandler(BotUserRepository botUserRepository, TelegramClient telegramClient, UserRepository userRepository, UserRepository userRepository1, MainMenuHandler mainMenuHandler, ProductService productService, ProductRepository productRepository, StockRepository stockRepository, GenerateProductList generateProductList, DistributionService distributionService, KindergartenRepository kindergartenRepo, WarehouseRepository warehouseRepo) {
        super(botUserRepository, telegramClient, userRepository);
        this.userRepository = userRepository1;
        this.mainMenuHandler = mainMenuHandler;
        this.productService = productService;
        this.productRepository = productRepository;
        this.stockRepository = stockRepository;
        this.generateProductList = generateProductList;
        this.distributionService = distributionService;
        this.kindergartenRepo = kindergartenRepo;
        this.warehouseRepo = warehouseRepo;
    }

    @Override
    public BotState getState() {
        return BotState.PRODUCTS;
    }

    @SneakyThrows
    @Override
    public void handle(Update update, BotUser botUser) {

            Long chatId = botUser.getChatId();
            User user = userRepository.findByChatIdAndDeletedFalse(botUser.getChatId()).orElse(null);

            String text = update.getMessage().getText();

            if (text.equals(BotConstance.BACK_MENU)) {
                changeState(botUser, BotState.MAIN_MENU);
                mainMenuHandler.sendMainMenu(chatId, user, botUser);
                return;
            }

        Long productId = getProductId(text);

        Product product = productRepository.findByIdAndDeletedFalse(productId).orElse(null);
        SendMessage message = new SendMessage(String.valueOf(chatId), "");

        if (product != null) {
            if (user != null && user.getRole() == RoleName.MUDIRA) {
                // MUDIRA FLOW
                botUser.setTempUsername(String.valueOf(product.getId()));
                changeState(botUser, BotState.MUDIRA_ENTER_DAYS);

                Kindergarten kindergarten = kindergartenRepo.findByMudir(user).orElse(null);
                Warehouse defaultWarehouse = warehouseRepo.findAllByDeletedFalse(org.springframework.data.domain.PageRequest.of(0, 1)).getContent().get(0);
                
                if (kindergarten != null && defaultWarehouse != null) {
                    DistributionInfoReqDto reqDto = new DistributionInfoReqDto();
                    reqDto.setKindergartenId(kindergarten.getId());
                    reqDto.setProductId(product.getId());
                    reqDto.setWarehouseId(defaultWarehouse.getId());
                    reqDto.setYearMonth(YearMonth.now().toString());

                    ApiResponse<?> response = distributionService.getInfo(reqDto);
                    if (response.isSuccess() && response.getData() != null) {
                        DistributionInfoResDto info = (DistributionInfoResDto) response.getData();
                        String data = """
                                📦 *Mahsulot:* %s
                                
                                📊 *Oylik Holat:*
                                • Jami ish kunlari: %d
                                • Olib bo'lingan kunlar: %d
                                • Qolgan ish kunlari: %d
                                
                                ⚖️ Omborda mavjud: %s %s
                                📏 Tavsiya qilingan kunlik norma: %s %s
                                
                                ✍️ Necha ish kunlik qismini olmoqchisiz? Raqamda kiriting:
                                """.formatted(
                                product.getName(),
                                info.getTotalWorkingDays(), info.getSuppliedWorkingDays(), info.getRemainingWorkingDays(),
                                info.getAvailableProduct(), product.getUnit(),
                                info.getRecommendedQuantity(), product.getUnit()
                        );
                        message.setText(data);
                        message.setParseMode("Markdown");
                        telegramClient.execute(message);
                        return;
                    }
                }
                message.setText("Xatolik: Sizning bog'changiz topilmadi yoki omborda muammo bor.");
                telegramClient.execute(message);
                return;
            } else {
                // ADMIN OR OTHER ROLE FLOW
                List<StockProjection> stocks = stockRepository.findAllWithWarehouse(productId);
                StringBuilder stockText = new StringBuilder();

                if (stocks.isEmpty()) {
                    stockText.append("Omborda mavjud emas ❌\n");
                } else {
                    for (StockProjection stock : stocks) {
                        stockText.append("🏬 ")
                                .append(stock.getWarehouseName())
                                .append(" : ")
                                .append(stock.getPhysicalQuantity())
                                .append(" ")
                                .append(product.getUnit())
                                .append("\n");
                    }
                }
                String data = """
                        -----Maxsulot xaqida----
                        Nomi: %s
                        Birlik: %s
                        Narxi: %s
                        Tugash soni: %s
                        Tasnif: %s
                        -----Ombordagi qoldiqlar----
                        %s
                        """
                        .formatted(product.getName(), product.getUnit(), product.getCurrentPrice(), product.getCriticalLimit()
                                , product.getDescription(),
                                stockText.toString());
                message.setText(data);
            }
        } else {
            generateProductList.generateList(message);
        }
        telegramClient.execute(message);
    }

    private Long getProductId(String text) {
        if (text.contains("[") && text.contains("]")) {
            String idStr = text.substring(
                    text.indexOf("[") + 1,
                    text.indexOf("]")
            );

            return Long.parseLong(idStr);
        }
        return 0L;
    }


}