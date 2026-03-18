package uz.qodirov.warehouse.dto.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;


public interface OrderListResDto {
    Long getId();

    String getKindergartenName();

    String getDriverName();

    BigDecimal getTotalAmount();

    Date getOrderDate();

    String getStatus();
}
