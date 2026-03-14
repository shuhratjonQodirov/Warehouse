package uz.qodirov.warehouse.dto.res;

import java.math.BigDecimal;

public interface StockProjection {
    Long getId();

    Long getProductId();

    String getProductName();

    String getCategoryName();

    String getUnit();

    Long getWarehouseId();

    String getWarehouseName();

    BigDecimal getPhysicalQuantity();

    BigDecimal getReservedQuantity();
}