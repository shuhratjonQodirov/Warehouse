package uz.qodirov.warehouse.dto.res;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class DistributionInfoResDto {
    private int totalWorkingDays;
    private int suppliedWorkingDays;
    private int remainingWorkingDays;
    private BigDecimal recommendedQuantity;
    private BigDecimal availableProduct;
}
