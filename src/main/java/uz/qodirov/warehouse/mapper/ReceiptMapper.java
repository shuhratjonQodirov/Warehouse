package uz.qodirov.warehouse.mapper;

import org.springframework.stereotype.Component;
import uz.qodirov.warehouse.dto.req.ReceiptReqDto;
import uz.qodirov.warehouse.dto.req.ReceiptReqItem;
import uz.qodirov.warehouse.model.Product;
import uz.qodirov.warehouse.model.Receipt;
import uz.qodirov.warehouse.model.Supplier;
import uz.qodirov.warehouse.model.Warehouse;

@Component
public class ReceiptMapper {
    public Receipt toEntity(Product product, Warehouse warehouse, Supplier supplier, ReceiptReqDto dto, ReceiptReqItem item) {
        return Receipt.builder()
                .product(product).warehouse(warehouse).supplier(supplier).quantity(item.getQuantity())
                .price(item.getPrice())
                .totalSum(item.getPrice().multiply(item.getQuantity()))
                .documentNumber(dto.getDocumentNumber())
                .receiveDate(dto.getReceiveDate())
                .build();
    }
}
