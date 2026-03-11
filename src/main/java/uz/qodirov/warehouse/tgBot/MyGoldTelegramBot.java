package uz.qodirov.warehouse.tgBot;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import uz.qodirov.warehouse.tgBot.handle.HandlerFactory;

@Component
@RequiredArgsConstructor
public class MyGoldTelegramBot implements LongPollingSingleThreadUpdateConsumer {

    private final HandlerFactory handlerFactory;

    @Override
    public void consume(Update update) {

        Message message = update.getMessage();

        if (update.hasMessage()) {



        } else if (message.hasLocation()) {
            /// to do
        }
    }
}
