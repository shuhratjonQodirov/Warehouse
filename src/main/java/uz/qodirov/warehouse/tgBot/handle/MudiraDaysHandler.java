package uz.qodirov.warehouse.tgBot.handle;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import uz.qodirov.warehouse.dto.req.OrderItemReq;
import uz.qodirov.warehouse.dto.req.OrderReqDto;
import uz.qodirov.warehouse.model.Kindergarten;
import uz.qodirov.warehouse.model.Product;
import uz.qodirov.warehouse.model.ProductNorm;
import uz.qodirov.warehouse.model.User;
import uz.qodirov.warehouse.model.Warehouse;
import uz.qodirov.warehouse.repository.KindergartenRepository;
import uz.qodirov.warehouse.repository.ProductNormRepository;
import uz.qodirov.warehouse.repository.ProductRepository;
import uz.qodirov.warehouse.repository.UserRepository;
import uz.qodirov.warehouse.repository.WarehouseRepository;
import uz.qodirov.warehouse.service.CalculateDailyNorm;
import uz.qodirov.warehouse.service.DistributionService;
import uz.qodirov.warehouse.tgBot.model.BotUser;
import uz.qodirov.warehouse.tgBot.repository.BotUserRepository;
import uz.qodirov.warehouse.tgBot.state.BotConstance;
import uz.qodirov.warehouse.tgBot.state.BotState;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Component
public class MudiraDaysHandler extends BaseHandler {

    private final KindergartenRepository kindergartenRepository;
    private final ProductRepository productRepository;
    private final WarehouseRepository warehouseRepository;
    private final ProductNormRepository productNormRepository;
    private final CalculateDailyNorm calculateDailyNorm;
    private final DistributionService distributionService;
    private final MainMenuHandler mainMenuHandler;

    private final UserRepository userRepository;

    public MudiraDaysHandler(BotUserRepository botUserRepository, TelegramClient telegramClient,
                             KindergartenRepository kindergartenRepository, ProductRepository productRepository,
                             WarehouseRepository warehouseRepository, ProductNormRepository productNormRepository,
                             CalculateDailyNorm calculateDailyNorm, DistributionService distributionService,
                             MainMenuHandler mainMenuHandler, UserRepository userRepository) {
        super(botUserRepository, telegramClient, userRepository);
        this.kindergartenRepository = kindergartenRepository;
        this.productRepository = productRepository;
        this.warehouseRepository = warehouseRepository;
        this.productNormRepository = productNormRepository;
        this.calculateDailyNorm = calculateDailyNorm;
        this.distributionService = distributionService;
        this.mainMenuHandler = mainMenuHandler;
        this.userRepository = userRepository;
    }

    @Override
    public BotState getState() {
        return BotState.MUDIRA_ENTER_DAYS;
    }

    @SneakyThrows
    @Override
    public void handle(Update update, BotUser botUser) {
        Long chatId = botUser.getChatId();
        String text = update.getMessage().getText().trim();
        User user = userRepository.findByChatIdAndDeletedFalse(chatId).orElse(null);

        if (text.equals(BotConstance.BACK_MENU)) {
            changeState(botUser, BotState.MAIN_MENU);
            mainMenuHandler.sendMainMenu(chatId, user, botUser);
            return;
        }

        SendMessage message = new SendMessage(chatId.toString(), "");

        try {
            int days = Integer.parseInt(text);

            if (days <= 0) {
                message.setText("Kunlar soni 0 dan katta bo'lishi kerak.");
                telegramClient.execute(message);
                return;
            }

            Long productId = Long.parseLong(botUser.getTempUsername());
            Kindergarten kindergarten = kindergartenRepository.findByMudir(user).orElseThrow();
            Product product = productRepository.findByIdAndDeletedFalse(productId).orElseThrow();
            Warehouse warehouse = warehouseRepository.findAllByDeletedFalse(org.springframework.data.domain.PageRequest.of(0, 1)).getContent().get(0);

            List<ProductNorm> list = productNormRepository.findByProductAndDeletedFalse(product);
            BigDecimal dailyNorm = calculateDailyNorm.dailyNorm(kindergarten, list);

            BigDecimal requiredQuantity = dailyNorm.multiply(BigDecimal.valueOf(days));

            OrderItemReq itemReq = new OrderItemReq();
            itemReq.setProductId(productId);
            itemReq.setDays(days);
            itemReq.setQuantity(requiredQuantity);

            OrderReqDto orderReq = OrderReqDto.builder()
                    .kindergartenId(kindergarten.getId())
                    .warehouseId(warehouse.getId())
                    .yearMonth(YearMonth.now().toString())
                    .items(List.of(itemReq))
                    .build();

            distributionService.addDraftItem(orderReq);

            message.setText("✅ Mahsulot savatchaga (Arizaga) qo'shildi!\n\nBizda (" + requiredQuantity + " " + product.getUnit() + ") o'zlashtirildi.\n\nYana boshqa mahsulot qo'shasizmi yoki arizani tasdiqlab yuborasizmi?");
            org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup markup = new org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup( new ArrayList<>());
            org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow row1 = new org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow();
            row1.add(BotConstance.PRODUCTS);
            row1.add("📨 Arizani Yuborish");
            org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow row2 = new org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow();
            row2.add(BotConstance.BACK_MENU);
            markup.setKeyboard(java.util.List.of(row1, row2));
            markup.setResizeKeyboard(true);
            message.setReplyMarkup(markup);
            
            telegramClient.execute(message);

            // Change state to allow them to press the submit button
            changeState(botUser, BotState.MUDIRA_CONFIRM_ORDER);

        } catch (NumberFormatException e) {
            message.setText("Iltimos, ish kunini raqamda kiriting (Masalan, 5)");
            telegramClient.execute(message);
        } catch (Exception e) {
            message.setText("❌ Xatolik yuz berdi. Limitingiz yetarli ekanligiga ishonch hosil qiling.");
            telegramClient.execute(message);
        }
    }
}
