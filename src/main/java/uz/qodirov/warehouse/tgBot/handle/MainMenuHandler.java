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
import uz.qodirov.warehouse.model.User;
import uz.qodirov.warehouse.repository.UserRepository;
import uz.qodirov.warehouse.service.ProductService;
import uz.qodirov.warehouse.tgBot.model.BotUser;
import uz.qodirov.warehouse.tgBot.repository.BotUserRepository;
import uz.qodirov.warehouse.tgBot.service.GenerateMainMenu;
import uz.qodirov.warehouse.tgBot.state.BotConstance;
import uz.qodirov.warehouse.tgBot.state.BotState;
import uz.qodirov.warehouse.utils.ApiResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class MainMenuHandler extends BaseHandler {
    private final UserRepository userRepository;
    private final GenerateMainMenu generateMainMenu;
    private final ProductService productService;

    public MainMenuHandler(BotUserRepository botUserRepository, TelegramClient telegramClient, UserRepository userRepository, UserRepository userRepository1, GenerateMainMenu generateMainMenu, ProductService productService) {
        super(botUserRepository, telegramClient, userRepository);
        this.userRepository = userRepository1;
        this.generateMainMenu = generateMainMenu;
        this.productService = productService;
    }

    @Override
    public BotState getState() {
        return BotState.MAIN_MENU;
    }

    @Override
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
            case MUDIRA -> handleMudira(update, user, text, chatId);
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

    private void handleMudira(Update update, User user, String text, Long chatId) {

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

            }
            case BotConstance.PRODUCTS -> {
                changeState(botUser, BotState.PRODUCTS);
            }
            case BotConstance.WAREHOUSE -> {
            }
            case BotConstance.STATISTIC -> {
            }
            case BotConstance.USERS -> {
            }
            case BotConstance.SETTINGS -> {
            }
            default -> {
                sendMainMenu(update.getMessage().getChatId(), user, botUser);
            }
        }
    }
}
