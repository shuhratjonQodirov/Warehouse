package uz.qodirov.warehouse.dto.res;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;


@Data
@Builder
public class StockResDto {
    private Long productId;
    private String productName;
    private String categoryName;
    private String unit;
    private BigDecimal totalQuantity;
    private BigDecimal totalReserved;
    private BigDecimal totalAvailable;
    private List<WarehouseStockDto> warehouses;
}