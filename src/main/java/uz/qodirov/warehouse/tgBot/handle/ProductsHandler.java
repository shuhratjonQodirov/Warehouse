package uz.qodirov.warehouse.tgBot.handle;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import uz.qodirov.warehouse.repository.UserRepository;
import uz.qodirov.warehouse.service.ProductService;
import uz.qodirov.warehouse.tgBot.model.BotUser;
import uz.qodirov.warehouse.tgBot.repository.BotUserRepository;
import uz.qodirov.warehouse.tgBot.state.BotState;

@Component
public class ProductsHandler extends BaseHandler {
    private final UserRepository userRepository;
    private final MainMenuHandler mainMenuHandler;
    private final ProductService productService;

    public ProductsHandler(BotUserRepository botUserRepository, TelegramClient telegramClient, UserRepository userRepository, UserRepository userRepository1, MainMenuHandler mainMenuHandler, ProductService productService) {
        super(botUserRepository, telegramClient, userRepository);
        this.userRepository = userRepository1;
        this.mainMenuHandler = mainMenuHandler;
        this.productService = productService;
    }

    @Override
    public BotState getState() {
        return BotState.PRODUCTS;
    }

    @SneakyThrows
    @Override
    public void handle(Update update, BotUser botUser) {

        Long chatId = botUser.getChatId();


    }
}