package uz.qodirov.warehouse.dto.res;

import java.math.BigDecimal;

public interface OrderItemResDto {
     Long getProductId();
     Long getWarehouseId();
     String getProductName();
     BigDecimal getQuantity();
     String getUnit();
     Integer getDays();
     String getYearMonth();
     String getWarehouseName();
}
