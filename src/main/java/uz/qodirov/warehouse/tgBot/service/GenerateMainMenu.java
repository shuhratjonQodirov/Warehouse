package uz.qodirov.warehouse.tgBot.service;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import uz.qodirov.warehouse.enums.RoleName;
import uz.qodirov.warehouse.tgBot.state.BotConstance;

import org.telegram.telegrambots.meta.api.objects.webapp.WebAppInfo;

import java.util.*;

@Component
public class GenerateMainMenu {

    public ReplyKeyboardMarkup getMenuByRole(RoleName role) {
        return switch (role) {
            case ADMIN -> adminMenu();
            case DRIVER -> driverMenu();
            case MUDIRA -> mudiraMenu();
            case MANAGER -> managerMenu();
            default -> defaultMenu();
        };
    }

    private ReplyKeyboardMarkup defaultMenu() {
        return buildKeyboard(
                row(BotConstance.BACK_MENU)
        );
    }

    private ReplyKeyboardMarkup managerMenu() {
        return buildKeyboard(
                row("📥 Qabul qilish", "📤 Taqsimlash"),
                row("📊 Hisobotlar")
        );
    }

    private ReplyKeyboardMarkup mudiraMenu() {
        org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton webAppBtn = new org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton(new String());
        webAppBtn.setText("📦 Savat (Web App)");
        
        WebAppInfo webAppInfo = new WebAppInfo("\"https://omborxona-kx4v.onrender.com/telegram-app.html");
        // Uses the publicly accessible URL of the mini app. Fallback to generic url for development if running locally.
        // It requires a valid HTTPS URL when running in production.
        webAppInfo.setUrl("https://omborxona-kx4v.onrender.com/telegram-app.html");
        webAppBtn.setWebApp(webAppInfo);

        KeyboardRow firstRow = new KeyboardRow();
        firstRow.add(webAppBtn);
        firstRow.add(new org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton("📋 Bog'cha hisoboti"));

        KeyboardRow secondRow = new KeyboardRow();
        secondRow.add(new org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton("Bog'cha haqida"));
        secondRow.add(new org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton("Sozlamalar"));

        return buildKeyboard(firstRow, secondRow);
    }

    private ReplyKeyboardMarkup driverMenu() {
        return buildKeyboard(
                row(BotConstance.MY_TRIPS),
                row("⛽ Yoqilg'i")
        );

    }

    private ReplyKeyboardMarkup adminMenu() {
        return buildKeyboard(
                row(BotConstance.PRODUCTS, BotConstance.WAREHOUSE),
                row(BotConstance.USERS, BotConstance.STATISTIC),
                row(BotConstance.SETTINGS, BotConstance.PROFILE)
        );
    }

    private ReplyKeyboardMarkup buildKeyboard(KeyboardRow... rows) {
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup(Arrays.asList(rows));
        markup.setResizeKeyboard(true);
        markup.setOneTimeKeyboard(false);
        return markup;
    }

    private KeyboardRow row(String... buttons) {
        KeyboardRow keyboardRow = new KeyboardRow();
        Arrays.stream(buttons).forEach(keyboardRow::add);
        return keyboardRow;
    }


    public <T> void generateButtons(
            SendMessage message,
            List<T> list,
            java.util.function.Function<T, String> buttonTextFunction,
            String emptyText,
            String successText
    ) {

        List<T> dataList = Optional.ofNullable(list)
                .orElse(Collections.emptyList());

        if (dataList.isEmpty()) {
            message.setText(emptyText);
            return;
        }

        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup(List.of());
        markup.setResizeKeyboard(true);
        markup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();

        for (int i = 0; i < dataList.size(); i += 2) {
            KeyboardRow row = new KeyboardRow();

            row.add(buttonTextFunction.apply(dataList.get(i)));

            if (i + 1 < dataList.size()) {
                row.add(buttonTextFunction.apply(dataList.get(i + 1)));
            }

            keyboard.add(row);
        }

        KeyboardRow backRow = new KeyboardRow();
        backRow.add(BotConstance.BACK_MENU);
        keyboard.add(backRow);

        message.setText(successText);
        markup.setKeyboard(keyboard);
        message.setReplyMarkup(markup);
    }
}
