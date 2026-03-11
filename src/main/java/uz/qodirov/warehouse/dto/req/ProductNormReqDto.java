package uz.qodirov.warehouse.dto.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductNormReqDto {
    private Long productId;
    private String ageGroup;
    private String stayDuration;
    private BigDecimal dailyPerChild;
    private String consumptionType;
    private String unit;
    private Integer repeatDays;
    private String sourceDocument;
    private Boolean active;
}
