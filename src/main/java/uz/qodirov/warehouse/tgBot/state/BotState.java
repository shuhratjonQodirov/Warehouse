package uz.qodirov.warehouse.tgBot.state;

public enum BotState {
    REGISTRATION,
    MAIN_MENU,
    MY_BALANCE,
    ABOUT_ME,
    MY_TRIP,
    MY_CAR,
    SEND_PHOTO,
    SEND_LOCATION,
    HELP,
    SETTINGS,
    DATA_ABOUT_TRIP,
    // Yoqilg'i qo'shish uchun yangi state'lar
    FUEL_AMOUNT,          // Necha litr?
    FUEL_PRICE_PER_LITER, // Har litr necha puldan?
    FUEL_LOCATION,        // Qayerda?
    FUEL_TOTAL_COST,
    WAITING_IMAGES_CARGO, FUEL_CONFIRMATION
}
