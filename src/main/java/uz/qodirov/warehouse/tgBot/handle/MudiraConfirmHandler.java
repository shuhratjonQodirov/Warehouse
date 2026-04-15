package uz.qodirov.warehouse.tgBot.handle;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import uz.qodirov.warehouse.model.Kindergarten;
import uz.qodirov.warehouse.model.User;
import uz.qodirov.warehouse.repository.KindergartenRepository;
import uz.qodirov.warehouse.repository.UserRepository;
import uz.qodirov.warehouse.service.DistributionService;
import uz.qodirov.warehouse.tgBot.model.BotUser;
import uz.qodirov.warehouse.tgBot.repository.BotUserRepository;
import uz.qodirov.warehouse.tgBot.state.BotConstance;
import uz.qodirov.warehouse.tgBot.state.BotState;

@Component
public class MudiraConfirmHandler extends BaseHandler {

    private final DistributionService distributionService;
    private final KindergartenRepository kindergartenRepository;
    private final MainMenuHandler mainMenuHandler;
    private final UserRepository userRepository;

    public MudiraConfirmHandler(BotUserRepository botUserRepository, TelegramClient telegramClient,
                                DistributionService distributionService, KindergartenRepository kindergartenRepository,
                                MainMenuHandler mainMenuHandler, UserRepository userRepository) {
        super(botUserRepository, telegramClient, userRepository);
        this.distributionService = distributionService;
        this.kindergartenRepository = kindergartenRepository;
        this.mainMenuHandler = mainMenuHandler;
        this.userRepository = userRepository;   
    }

    @Override
    public BotState getState() {
        return BotState.MUDIRA_CONFIRM_ORDER;
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

        if (text.equals(BotConstance.PRODUCTS)) {
            // Forward back to products to add more
            changeState(botUser, BotState.MAIN_MENU); 
            // In MainMenuHandler mudira handles BotConstance.PRODUCTS correctly.
            // We can just rely on the MainMenuHandler or update directly.
            // But actually wait, we can just change state and let the user click again, 
            // or we simulate the click
            update.getMessage().setText(BotConstance.PRODUCTS);
            mainMenuHandler.handle(update, botUser);
            return;
        }

        SendMessage message = new SendMessage(chatId.toString(), "");

        if (text.equals("📨 Arizani Yuborish")) {
            Kindergarten kindergarten = kindergartenRepository.findByMudir(user).orElse(null);
            if (kindergarten != null) {
                try {
                    distributionService.submitDraft(kindergarten.getId());
                    message.setText("🎉 Arizangiz adminga muvaffaqiyatli yuborildi! Tasdiqlanishini kuting.");
                } catch (Exception e) {
                    message.setText("Arizani yuborishda xatolik yuz berdi: " + e.getMessage());
                }
            } else {
                message.setText("Sizga bog'cha biriktirilmagan.");
            }
            telegramClient.execute(message);
            changeState(botUser, BotState.MAIN_MENU);
            mainMenuHandler.sendMainMenu(chatId, user, botUser);
        } else {
            message.setText("Iltimos, Menyulardan birini tanlang");
            telegramClient.execute(message);
        }
    }
}
