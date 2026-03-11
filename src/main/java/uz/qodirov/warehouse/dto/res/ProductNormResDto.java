package uz.qodirov.warehouse.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ProductNormResDto {
    private Long id;
    private Long productId;
    private String productName;
    private String ageGroup;
    private String stayDuration;
    private BigDecimal dailyPerChild;
    private String consumptionType;
    private String unit;
    private Integer repeatDays;
    private String sourceDocument;
    private Boolean active;
}
