package uz.qodirov.warehouse.tgBot.handle;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import uz.qodirov.warehouse.model.User;
import uz.qodirov.warehouse.repository.UserRepository;
import uz.qodirov.warehouse.tgBot.model.BotUser;
import uz.qodirov.warehouse.tgBot.repository.BotUserRepository;
import uz.qodirov.warehouse.tgBot.state.BotConstance;
import uz.qodirov.warehouse.tgBot.state.BotState;

@Component
public class WarehouseHandle extends BaseHandler {
    private final UserRepository userRepository;
    private final MainMenuHandler mainMenuHandler;
    public WarehouseHandle(BotUserRepository botUserRepository, TelegramClient telegramClient, UserRepository userRepository, UserRepository userRepository1, MainMenuHandler mainMenuHandler) {
        super(botUserRepository, telegramClient, userRepository);
        this.userRepository = userRepository1;
        this.mainMenuHandler = mainMenuHandler;
    }

    @Override
    public BotState getState() {
        return BotState.WAREHOUSE;
    }

    @Override
    public void handle(Update update, BotUser user) {

    }
}
