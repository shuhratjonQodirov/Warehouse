package uz.qodirov.warehouse.tgBot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import uz.qodirov.warehouse.dto.res.WarehouseResDto;
import uz.qodirov.warehouse.service.WarehouseService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GenerateWarehouseBtn {

    private final WarehouseService warehouseService;
    private final GenerateMainMenu generateMainMenu;

    public void warehouseBtn(SendMessage message) {


        List<WarehouseResDto> list = warehouseService.getAll(0, -1).getData();

        generateMainMenu.generateButtons(
                message,
                list
                , w -> String.format("%s [%d]", w.getName(), w.getId())
                , "Omborxona mavjud emas",
                "Omborlardan birini tanglang !"
        );


    }
}
