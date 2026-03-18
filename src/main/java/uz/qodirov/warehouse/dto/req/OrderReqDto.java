package uz.qodirov.warehouse.dto.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class OrderReqDto {
    private Long kindergartenId;
    private Long warehouseId;
    private String yearMonth;
    private List<OrderItemReq> items;
}
