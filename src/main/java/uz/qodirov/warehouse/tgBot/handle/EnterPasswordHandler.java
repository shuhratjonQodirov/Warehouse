package uz.qodirov.warehouse.tgBot.handle;

import lombok.SneakyThrows;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.passport.dataerror.PassportElementError;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import uz.qodirov.warehouse.model.User;
import uz.qodirov.warehouse.repository.UserRepository;
import uz.qodirov.warehouse.tgBot.model.BotUser;
import uz.qodirov.warehouse.tgBot.repository.BotUserRepository;
import uz.qodirov.warehouse.tgBot.state.BotState;

@Component
public class EnterPasswordHandler extends BaseHandler {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public EnterPasswordHandler(BotUserRepository botUserRepository, TelegramClient telegramClient, UserRepository userRepository, UserRepository userRepository1, PasswordEncoder passwordEncoder) {
        super(botUserRepository, telegramClient, userRepository);
        this.userRepository = userRepository1;
        this.passwordEncoder = passwordEncoder;
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
        }

        user.setChatId(chatId);
        changeState(user, BotState.MAIN_MENU);

        botUser.setTempUsername(null);
        changeState(botUser, BotState.MAIN_MENU);

        SendMessage message = new SendMessage(chatId.toString(), "");


        telegramClient.execute(new SendMessage(
                chatId.toString(),
                "✅ Xush kelibsiz, " + user.getFullName() + "!\n" +
                        "Rol: " + user.getRole()));
    }
}
