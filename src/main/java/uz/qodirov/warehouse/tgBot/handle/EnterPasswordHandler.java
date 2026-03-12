package uz.qodirov.warehouse.tgBot.handle;

import lombok.SneakyThrows;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import uz.qodirov.warehouse.model.User;
import uz.qodirov.warehouse.repository.UserRepository;
import uz.qodirov.warehouse.tgBot.model.BotUser;
import uz.qodirov.warehouse.tgBot.repository.BotUserRepository;
import uz.qodirov.warehouse.tgBot.service.GenerateMainMenu;
import uz.qodirov.warehouse.tgBot.state.BotState;

@Component
public class EnterPasswordHandler extends BaseHandler {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final GenerateMainMenu mainMenu;

    public EnterPasswordHandler(BotUserRepository botUserRepository, TelegramClient telegramClient, UserRepository userRepository, UserRepository userRepository1, PasswordEncoder passwordEncoder, GenerateMainMenu mainMenu) {
        super(botUserRepository, telegramClient, userRepository);
        this.userRepository = userRepository1;
        this.passwordEncoder = passwordEncoder;
        this.mainMenu = mainMenu;
    }


    @Override
    public BotState getState() {
        return BotState.ENTER_PASSWORD;
    }

    @SneakyThrows
    @Override
    public void handle(Update update, BotUser botUser) {
        Long chatId = update.getMessage().getChatId();
        String password = update.getMessage().getText().trim();

        User user
                = userRepository.findByUsernameAndDeletedFalse(botUser.getTempUsername()).orElse(null);

        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            telegramClient.execute(new SendMessage(chatId.toString(), "Parol natog'ri"));
            return;
        }

        user.setChatId(chatId);
        userRepository.save(user);

        botUser.setTempUsername(null);
        changeState(botUser, BotState.MAIN_MENU);

        SendMessage message = new SendMessage(chatId.toString(), "");
        message.setReplyMarkup(
                mainMenu.getMenuByRole(user.getRole()));
        message.setText("Quyidagilardan birini tanglang");
        message.setChatId(chatId);
        telegramClient.execute(message);
    }
}
