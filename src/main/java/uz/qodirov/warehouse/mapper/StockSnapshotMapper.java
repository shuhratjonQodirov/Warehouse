package uz.qodirov.warehouse.mapper;

import org.springframework.stereotype.Component;
import uz.qodirov.warehouse.dto.req.ReceiptReqItem;
import uz.qodirov.warehouse.model.Receipt;
import uz.qodirov.warehouse.model.Stock;
import uz.qodirov.warehouse.model.StockSnapshot;

import java.math.BigDecimal;

@Component
public class StockSnapshotMapper {
    public StockSnapshot toEntity(Stock stock, Receipt receipt, ReceiptReqItem item) {
        BigDecimal currentQty = (stock != null) ? stock.getPhysicalQuantity() : BigDecimal.ZERO;

        return StockSnapshot.builder()
                .stock(stock)
                .receipt(receipt)
                .oldQuantity(currentQty)
                .newQuantity(currentQty.add(item.getQuantity()))
                .reservedQuantity((stock != null) ? stock.getReservedQuantity() : BigDecimal.ZERO)
                .action("RECEIVE")
                .build();
    }
}
