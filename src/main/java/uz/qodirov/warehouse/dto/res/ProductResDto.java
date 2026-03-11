package uz.qodirov.warehouse.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductResDto {
    private Long id;

    private String name;
    private String categoryName;
    private Long categoryId;

    private String unit;

    private BigDecimal currentPrice;

    private BigDecimal criticalLimit;

    private String description;
}
