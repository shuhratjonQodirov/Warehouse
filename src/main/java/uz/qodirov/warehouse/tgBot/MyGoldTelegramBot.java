package uz.qodirov.warehouse.tgBot;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import uz.qodirov.warehouse.model.User;
import uz.qodirov.warehouse.repository.UserRepository;
import uz.qodirov.warehouse.tgBot.handle.BotStateHandler;
import uz.qodirov.warehouse.tgBot.handle.HandlerFactory;
import uz.qodirov.warehouse.tgBot.model.BotUser;
import uz.qodirov.warehouse.tgBot.repository.BotUserRepository;
import uz.qodirov.warehouse.tgBot.state.BotState;
@Slf4j
@Component
@RequiredArgsConstructor
public class MyGoldTelegramBot implements LongPollingSingleThreadUpdateConsumer {

    private final HandlerFactory handlerFactory;
    private final BotUserRepository botUserRepository;
    protected final TelegramClient telegramClient;
    private final UserRepository userRepository;

    @SneakyThrows
    @Override
    public void consume(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            Long chatId = message.getChatId();

            if (message.hasText()) {
                String text = message.getText().trim();

                if (text.equals("/start")) {
                    handleStart(update, chatId);
                    return;
                }
                BotUser botUser = botUserRepository.findByChatId(chatId).orElse(null);
                if (botUser == null) {
                    telegramClient.execute(new SendMessage(
                            chatId.toString(), "Iltimos /start bosing"));
                    return;
                }
                handleByState(update, botUser);

            } else if (message.hasLocation()) {
                /// todo some
            } else if (message.hasPhoto()) {
                /// todo some
            } else if (message.hasWebAppData()) {
                String data = message.getWebAppData().getData();
                BotUser botUser = botUserRepository.findByChatId(chatId).orElse(null);
                if (botUser != null) {
                    try {
                        telegramClient.execute(new SendMessage(chatId.toString(), "📦 Arizangiz qabul qilindi!\n\nSavatdagi mahsulotlar:\n" + data));
                        // TODO: Save data to Order / DistributionService
                        botUser.setState(BotState.MAIN_MENU.name());
                        botUserRepository.save(botUser);
                        handleByState(update, botUser);
                    } catch (Exception e) {
                        log.error("Error parsing WebAppData: ", e);
                    }
                }
            }

        }

    }

    @SneakyThrows
    private void handleStart(Update update, Long chatId) {
        BotUser botUser = botUserRepository.findByChatId(chatId).orElse(null);
        if (botUser == null) {
            BotUser newBotUser = new BotUser();
            newBotUser.setChatId(chatId);
            newBotUser.setState(BotState.ENTER_USERNAME.name());
            botUserRepository.save(newBotUser);
            telegramClient.execute(new SendMessage(
                    chatId.toString(),
                    "👋 Xush kelibsiz!\n\n👤 Usernamingizni kiriting:"));
        } else {
            User user = userRepository.findByChatIdAndDeletedFalse(chatId).orElse(null);
            if (user == null) {
                botUser.setState(BotState.ENTER_USERNAME.name());
                botUser.setTempUsername(null);
                botUserRepository.save(botUser);
                telegramClient.execute(new SendMessage(
                        chatId.toString(),
                        "👤 Usernamingizni kiriting:"));
            } else {
                botUser.setState(BotState.MAIN_MENU.name());
                botUserRepository.save(botUser);
                handleByState(update, botUser);
            }
        }
    }

    @SneakyThrows
    private void handleByState(Update update, BotUser botUser) {
        Long chatId = botUser.getChatId();
        String stateStr = botUser.getState();
        if (stateStr.equals(BotState.ENTER_USERNAME.name()) ||
                stateStr.equals(BotState.ENTER_PASSWORD.name())) {
            BotStateHandler handler = handlerFactory.getHandler(BotState.valueOf(stateStr));
            if (handler != null) handler.handle(update, botUser);
            return;
        }

        // Qolgan barcha state — User.state ishlatamiz
        User user = userRepository.findByChatIdAndDeletedFalse(chatId).orElse(null);
        if (user == null) {
            telegramClient.execute(new SendMessage(
                    chatId.toString(), "⚠️ /start bosing"));
            return;
        }
        BotState botState;
        try {
            botState = BotState.valueOf(botUser.getState());
        } catch (IllegalArgumentException e) {
            botUser.setState(BotState.MAIN_MENU.name());
            botUserRepository.save(botUser);
            botState = BotState.MAIN_MENU;
        }
        BotStateHandler handler = handlerFactory.getHandler(botState);
        if (handler != null) {
            handler.handle(update, botUser);
        } else {
            telegramClient.execute(new SendMessage(botUser.getChatId().toString(), "Xatolik"));
        }
    }
}
