package uz.qodirov.warehouse.mapper;

import org.springframework.stereotype.Component;
import uz.qodirov.warehouse.model.Product;
import uz.qodirov.warehouse.model.Stock;
import uz.qodirov.warehouse.model.Warehouse;

import java.math.BigDecimal;

@Component
public class StockMapper {
    public Stock toEntity(Warehouse warehouse, Product product, BigDecimal quantity) {
        return Stock.builder()
                .product(product).warehouse(warehouse).physicalQuantity(quantity).reservedQuantity(BigDecimal.ZERO).build();
    }
}
