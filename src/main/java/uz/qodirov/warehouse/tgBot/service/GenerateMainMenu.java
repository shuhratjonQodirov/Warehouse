package uz.qodirov.warehouse.tgBot.service;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import uz.qodirov.warehouse.enums.RoleName;
import uz.qodirov.warehouse.tgBot.state.BotConstance;

import java.util.Arrays;

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
        return buildKeyboard(
                row("📋 Bog'cha hisoboti", "Bog'cha haqida"),
                row("📦 Mahsulotlar", "Sozlamalar")
        );
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
}
