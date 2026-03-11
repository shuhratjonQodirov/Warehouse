package uz.qodirov.warehouse.tgBot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import uz.qodirov.warehouse.tgBot.MyGoldTelegramBot;

@Configuration
public class BotConfig {

    @Bean
    public TelegramBotsLongPollingApplication telegramBotsApplication(MyGoldTelegramBot bot, @Value("${telegram.bot.token}") String botToken) {
        try {
            TelegramBotsLongPollingApplication botsApplication = new TelegramBotsLongPollingApplication();
            botsApplication.registerBot(botToken, bot);
            System.out.println("✅ Telegram bot muvaffaqiyatli ishga tushdi!");
            return botsApplication;
        } catch (TelegramApiException e) {
            throw new RuntimeException("Bot ishga tushmadi!", e);

        }
    }
}
