package uz.qodirov.warehouse.tgBot.handle;

import org.telegram.telegrambots.meta.api.objects.Update;
import uz.qodirov.warehouse.model.User;
import uz.qodirov.warehouse.tgBot.state.BotState;

public interface BotStateHandler {

    BotState getState();   // qaysi state uchun ishlaydi

    void handle(Update update, User user); // o‘sha state logikasi
}
