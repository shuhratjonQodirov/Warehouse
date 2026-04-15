package uz.qodirov.warehouse.tgBot.handle;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import uz.qodirov.warehouse.dto.res.ProductResDto;
import uz.qodirov.warehouse.enums.RoleName;
import uz.qodirov.warehouse.model.User;
import uz.qodirov.warehouse.repository.UserRepository;
import uz.qodirov.warehouse.service.ProductService;
import uz.qodirov.warehouse.tgBot.model.BotUser;
import uz.qodirov.warehouse.tgBot.repository.BotUserRepository;
import uz.qodirov.warehouse.tgBot.service.GenerateMainMenu;
import uz.qodirov.warehouse.tgBot.service.GenerateProductList;
import uz.qodirov.warehouse.tgBot.service.GenerateWarehouseBtn;
import uz.qodirov.warehouse.tgBot.state.BotConstance;
import uz.qodirov.warehouse.tgBot.state.BotState;
import uz.qodirov.warehouse.utils.ApiResponse;

import uz.qodirov.warehouse.dto.req.OrderListResDto;
import uz.qodirov.warehouse.dto.res.StockProjection;
import uz.qodirov.warehouse.repository.OrderRepository;
import uz.qodirov.warehouse.repository.StockRepository;
import uz.qodirov.warehouse.repository.KindergartenRepository;
import uz.qodirov.warehouse.model.Kindergarten;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class MainMenuHandler extends BaseHandler {
    private final UserRepository userRepository;
    private final GenerateMainMenu generateMainMenu;
    private final ProductService productService;
    private final GenerateProductList generateProductList;
    private final GenerateWarehouseBtn generateWarehouseBtn;
    private final StockRepository stockRepository;
    private final OrderRepository orderRepository;
    private final KindergartenRepository kindergartenRepository;

    public MainMenuHandler(BotUserRepository botUserRepository, TelegramClient telegramClient, UserRepository userRepository, UserRepository userRepository1, GenerateMainMenu generateMainMenu, ProductService productService, GenerateProductList generateProductList, GenerateWarehouseBtn generateWarehouseBtn, StockRepository stockRepository, OrderRepository orderRepository, KindergartenRepository kindergartenRepository) {
        super(botUserRepository, telegramClient, userRepository);
        this.userRepository = userRepository1;
        this.generateMainMenu = generateMainMenu;
        this.productService = productService;
        this.generateProductList = generateProductList;
        this.generateWarehouseBtn = generateWarehouseBtn;
        this.stockRepository = stockRepository;
        this.orderRepository = orderRepository;
        this.kindergartenRepository = kindergartenRepository;
    }

    @Override
    public BotState getState() {
        return BotState.MAIN_MENU;
    }

    @Override
    @Transactional
    public void handle(Update update, BotUser botUser) {
        Long chatId = update.getMessage().getChatId();
        String text = update.getMessage().getText().trim();

        User user = userRepository.findByChatIdAndDeletedFalse(chatId).orElse(null);

        handleButton(update, user, text, chatId, botUser);
    }

    private void handleButton(Update update, User user, String text, Long chatId, BotUser botUser) {
        switch (user.getRole()) {
            case ADMIN -> handleAdmin(update, user, text, chatId, botUser);
            case MANAGER -> handleAManager(update, user, text, chatId);
            case DRIVER -> handleDriver(update, user, text, chatId);
            case MUDIRA -> handleMudira(update, user, text, chatId, botUser);
            default -> sendMainMenu(chatId, user, botUser);
        }
    }

    @SneakyThrows
    public void sendMainMenu(Long chatId, User user, BotUser botUser) {
        SendMessage message = new SendMessage(chatId.toString(), "");
        message.setText("Quyidagilardan birini tanglang");
        message.setReplyMarkup(generateMainMenu.getMenuByRole(user.getRole()));
        changeState(botUser, BotState.MAIN_MENU);
        telegramClient.execute(message);
    }

    @SneakyThrows
    private void handleMudira(Update update, User user, String text, Long chatId, BotUser botUser) {
        SendMessage message = new SendMessage(chatId.toString(), "");

        switch (text) {
            case BotConstance.PRODUCTS -> {
                changeState(botUser, BotState.PRODUCTS);
                generateProductList.generateList(message);
                telegramClient.execute(message);
            }
            case BotConstance.KINDERGARTEN_INFO -> {
                Kindergarten kg = kindergartenRepository.findByMudir(user).orElse(null);
                if (kg != null) {
                    message.setText("🏫 *Bog'cha nomi:* " + kg.getName() + " \n" +
                            "📍 *Manzil (Hudud):* " + (kg.getRegion() != null ? kg.getRegion().getName() : "Kiritilmagan") + " \n" +
                            "👥 *Jami bolalar soni:* " + kg.getTotalChildren());
                } else {
                    message.setText("Sizga bog'cha biriktirilmagan.");
                }
                message.setParseMode("Markdown");
                telegramClient.execute(message);
            }
            case BotConstance.KINDERGARTEN_REPORT -> {
                Kindergarten kg = kindergartenRepository.findByMudir(user).orElse(null);
                if (kg != null) {
                    List<OrderListResDto> orders = orderRepository.findByRes(PageRequest.of(0, 100));
                    List<OrderListResDto> myOrders = orders.stream()
                            .filter(o -> o.getKindergartenName() != null && o.getKindergartenName().equals(kg.getName()))
                            .toList();

                    StringBuilder sb = new StringBuilder("📊 *Oxirgi Arizalar (Hisobot):*\n\n");
                    if (myOrders.isEmpty()) {
                        sb.append("Sizda hozircha arizalar yo'q.");
                    } else {
                        myOrders.stream().limit(10).forEach(o ->
                                sb.append("🔸 ").append(o.getOrderDate()).append(" | ")
                                        .append(o.getStatus()).append(" | ")
                                        .append(o.getTotalAmount() != null ? o.getTotalAmount() : 0).append(" so'm\n"));
                    }
                    message.setText(sb.toString());
                } else {
                    message.setText("Sizga bog'cha biriktirilmagan.");
                }
                message.setParseMode("Markdown");
                telegramClient.execute(message);
            }
            case "Sozlamalar" -> {
                message.setText("⚙️ *Sozlamalar*\n\nProfilni yoki parolni yangilash uchun Admin bilan bog'laning yoki Web portaldan foydalaning.");
                message.setParseMode("Markdown");
                telegramClient.execute(message);
            }
            default -> sendMainMenu(chatId, user, botUser);
        }
    }

    private void handleDriver(Update update, User user, String text, Long chatId) {

    }

    private void handleAManager(Update update, User user, String text, Long chatId) {

    }

    @SneakyThrows
    private void handleAdmin(Update update, User user, String text, Long chatId, BotUser botUser) {
        SendMessage message = new SendMessage(chatId.toString(), "");

        switch (text) {
            case BotConstance.PROFILE -> {
                String profile = """
                        To'liq ismi:
                        %s
                        Telefon nomer:
                        %s
                        Email:
                        %s
                        Role:
                        %s
                        """.
                        formatted(user.getFullName(), user.getPhoneNumber(), user.getEmail(), user.getRole());

                message.setText(profile);
                telegramClient.execute(message);
            }
            case BotConstance.PRODUCTS -> {
                changeState(botUser, BotState.PRODUCTS);
                generateProductList.generateList(message);
                telegramClient.execute(message);
            }
            case BotConstance.WAREHOUSE -> {
                changeState(botUser,BotState.WAREHOUSE);
                generateWarehouseBtn.warehouseBtn(message);
                telegramClient.execute(message);
            }
            case BotConstance.STATISTIC -> {
                StringBuilder stat = new StringBuilder();
                stat.append("📊 *TIZIM HISOBOTI*\n\n");

                // 1. Ombordagi Qoldiqlar qayerda (Warehouse -> Stocks)
                stat.append("🏭 *OMBOR QOLDIQLARI:*\n");
                List<StockProjection> stocks = stockRepository.findAllByActive();
                if (stocks.isEmpty()) {
                    stat.append("  Omborlarda maxsulot yo'q.\n");
                } else {
                    Map<String, List<StockProjection>> byWarehouse = stocks.stream()
                            .collect(Collectors.groupingBy(StockProjection::getWarehouseName));

                    byWarehouse.forEach((warehouseName, wStocks) -> {
                        stat.append(" *").append(warehouseName).append("*:\n");
                        wStocks.forEach(s -> stat.append("  - ").append(s.getProductName())
                                .append(": ").append(s.getPhysicalQuantity()).append(" ").append(s.getUnit()).append("\n"));
                    });
                }
                stat.append("\n");

                // 2. Arizalar xolati
                List<OrderListResDto> orders = orderRepository.findByRes(PageRequest.of(0, 50));

                List<OrderListResDto> preparing = orders.stream()
                        .filter(o -> "PREPARING".equalsIgnoreCase(o.getStatus()))
                        .toList();
                
                stat.append("📥 *KUTILAYOTGAN ARIZALAR:* (").append(preparing.size()).append(" ta)\n");
                if (preparing.isEmpty()) stat.append("  Mavjud emas.\n");
                else {
                    preparing.stream().limit(10).forEach(o -> stat.append("  - ").append(o.getKindergartenName())
                            .append(" (Summa: ").append(o.getTotalAmount()).append(")\n"));
                }
                stat.append("\n");

                List<OrderListResDto> shipped = orders.stream()
                        .filter(o -> "SHIPPED".equalsIgnoreCase(o.getStatus()))
                        .toList();

                stat.append("📤 *CHIQIB KETGAN (Drivers):* (").append(shipped.size()).append(" ta)\n");
                if (shipped.isEmpty()) stat.append("  Mavjud emas.\n");
                else {
                    shipped.stream().limit(10).forEach(o -> stat.append("  - ").append(o.getKindergartenName())
                            .append(" 🚛 (").append(o.getDriverName() != null ? o.getDriverName() : "No'kt").append(")\n"));
                }

                message.setText(stat.toString());
                message.setParseMode("Markdown");
                telegramClient.execute(message);
            }
            case BotConstance.USERS -> {
                List<User> activeUsers = userRepository.findAll().stream()
                        .filter(u -> !u.isDeleted())
                        .toList();

                Map<RoleName, List<User>> usersByRole = activeUsers.stream()
                        .collect(Collectors.groupingBy(User::getRole));

                StringBuilder builder = new StringBuilder();
                builder.append("👥 *FOYDALANUVCHILAR HISOBOTI*\n\n");
                builder.append("Jami faol xodimlar: ").append(activeUsers.size()).append(" ta\n\n");

                usersByRole.forEach((role, list) -> {
                    builder.append("👤 *").append(role.name()).append("*: ").append(list.size()).append(" ta\n");
                    // Haydovchilar ro'yxatini to'liq chiqarish
                    if (role == RoleName.DRIVER || role == RoleName.ADMIN) {
                        list.forEach(u -> builder.append("  - ").append(u.getFullName() != null ? u.getFullName() : u.getUsername())
                                .append(u.getPhoneNumber() != null ? " ("+u.getPhoneNumber()+")" : "")
                                .append("\n"));
                    }
                });

                message.setText(builder.toString());
                message.setParseMode("Markdown");
                telegramClient.execute(message);
            }
            case BotConstance.SETTINGS -> {
                String string = """
                        ⚙️ *TIZIM SOZLAMALARI*
                        
                        Yangi foydalanuvchilar, bog'chalar, omborlar 
                        va mahsulot limitlarini qo'shish uchun 
                        to'liq imkoniyatlarni *Web Dashboard* orqali
                        amalga oshirishingiz mumkin.
                        
                        🌐 [Dasturga o'tish](http://localhost:8080/index.html)
                        
                        _Kerakli menyularni u yerdan boshqaring!_
                        """;
                message.setText(string);
                message.setParseMode("Markdown");
                telegramClient.execute(message);
            }
            default -> {
                sendMainMenu(update.getMessage().getChatId(), user, botUser);
            }
        }
    }
}
