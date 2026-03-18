package uz.qodirov.warehouse.dto.res;

import lombok.Builder;
import lombok.Data;
import uz.qodirov.warehouse.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@Builder
public class OrderDetailResDto {
    private Long id;
    private String kindergartenName;
    private String driverName;        // null bo'lsa modal ichida biriktirish formasi chiqadi
    private String status;
    private BigDecimal totalAmount;
    private Date orderDate;
    private List<OrderItemResDto> items;
}
