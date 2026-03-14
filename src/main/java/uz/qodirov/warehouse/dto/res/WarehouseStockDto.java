package uz.qodirov.warehouse.dto.res;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

// WarehouseStockDto.java
@Data
@Builder
public class WarehouseStockDto {
    private Long warehouseId;
    private String warehouseName;
    private BigDecimal physicalQuantity;
    private BigDecimal reservedQuantity;
    private BigDecimal available;
}