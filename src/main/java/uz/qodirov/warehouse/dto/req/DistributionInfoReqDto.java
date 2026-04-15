package uz.qodirov.warehouse.dto.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DistributionInfoReqDto {
    private Long kindergartenId;
    private Long productId;
    private Long warehouseId;
    private String yearMonth;
}