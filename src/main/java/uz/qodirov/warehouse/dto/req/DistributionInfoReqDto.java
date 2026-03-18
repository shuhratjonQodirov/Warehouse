package uz.qodirov.warehouse.dto.req;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DistributionInfoReqDto {
    private Long kindergartenId;
    private Long productId;
    private Long warehouseId;
    private String yearMonth;
}