package uz.qodirov.warehouse.mapper;

import org.springframework.stereotype.Component;
import uz.qodirov.warehouse.dto.req.ReceiptReqItem;
import uz.qodirov.warehouse.model.Receipt;
import uz.qodirov.warehouse.model.Stock;
import uz.qodirov.warehouse.model.StockSnapshot;

@Component
public class StockSnapshotMapper {
    public StockSnapshot toEntity(Stock stock, Receipt receipt, ReceiptReqItem item) {
        return StockSnapshot.builder()
                .stock(stock)
                .receipt(receipt)
                .oldQuantity(stock.getPhysicalQuantity())
                .newQuantity(stock.getPhysicalQuantity().add(item.getQuantity())) // qabul qilinganidan keyingi miqdor
                .reservedQuantity(stock.getReservedQuantity()) // hozirgi rezerv
                .action("RECEIVE")
                .build();
    }
}
