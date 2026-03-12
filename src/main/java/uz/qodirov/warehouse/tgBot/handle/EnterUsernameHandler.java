package uz.qodirov.warehouse.tgBot.handle;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import uz.qodirov.warehouse.model.User;
import uz.qodirov.warehouse.repository.UserRepository;
import uz.qodirov.warehouse.tgBot.model.BotUser;
import uz.qodirov.warehouse.tgBot.repository.BotUserRepository;
import uz.qodirov.warehouse.tgBot.state.BotState;

@Component
public class EnterUsernameHandler extends BaseHandler {

    private final UserRepository userRepository;

    public EnterUsernameHandler(BotUserRepository botUserRepository, TelegramClient telegramClient, UserRepository userRepository, UserRepository userRepository1) {
        super(botUserRepository, telegramClient, userRepository);
        this.userRepository = userRepository1;
    }

    @Override
    public BotState getState() {
        return BotState.ENTER_USERNAME;
    }

    @SneakyThrows
    @Override
    public void handle(Update update, BotUser botUser) {
        Long chatId = botUser.getChatId();
        String username = update.getMessage().getText().trim();

        User user = userRepository.findByUsernameAndDeletedFalse(username).orElse(null);
        if (user == null) {
            telegramClient.execute(new SendMessage(chatId.toString(), "Username topilmadi"));
            return;
        }
        botUser.setTempUsername(username);
        changeState(botUser, BotState.ENTER_PASSWORD);
        telegramClient.execute(new SendMessage(
                chatId.toString(), "🔐 Parolni kiriting:"));
    }
}
