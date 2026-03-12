package uz.qodirov.warehouse.tgBot.handle;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import uz.qodirov.warehouse.model.User;
import uz.qodirov.warehouse.repository.UserRepository;
import uz.qodirov.warehouse.tgBot.model.BotUser;
import uz.qodirov.warehouse.tgBot.repository.BotUserRepository;
import uz.qodirov.warehouse.tgBot.state.BotState;

@RequiredArgsConstructor
@Data
public abstract class BaseHandler implements BotStateHandler {
    protected final BotUserRepository botUserRepository;
    protected final TelegramClient telegramClient;
    private final UserRepository userRepository;

    protected void changeState(BotUser user, BotState newState) {
        user.setState(newState.name());
        botUserRepository.save(user);
    }

    protected void changeState(User user, BotState newState) {
        user.setState(newState.name());
        userRepository.save(user);
    }
}
