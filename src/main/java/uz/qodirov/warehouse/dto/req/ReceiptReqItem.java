package uz.qodirov.warehouse.dto.req;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ReceiptReqItem {
    private Long productId;
    private BigDecimal quantity;
    private BigDecimal price;
}
