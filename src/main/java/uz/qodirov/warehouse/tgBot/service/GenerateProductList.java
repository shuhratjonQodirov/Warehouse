package uz.qodirov.warehouse.tgBot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import uz.qodirov.warehouse.dto.res.ProductResDto;
import uz.qodirov.warehouse.service.ProductService;
import uz.qodirov.warehouse.utils.ApiResponse;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GenerateProductList {
    private final ProductService productService;
    private final GenerateMainMenu generateMainMenu;

    public void generateList(SendMessage message) {
        ApiResponse<List<ProductResDto>> all = productService.getAll(0, -1);
        generateMainMenu.generateButtons(
                message,
                all.getData(),
                p -> String.format("%s [%d]",
                        p.getName(), p.getId()),
                "Maxsulotlar mavjud emas",
                "Maxsulotlardan birini tanlang"
        );
    }
}
