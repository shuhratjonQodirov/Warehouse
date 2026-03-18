package uz.qodirov.warehouse.dto.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemReq {
    private Long productId;
    private int days;
    private BigDecimal quantity;
}
