package uz.qodirov.warehouse.dto.req;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AssignDriver {
    private Long orderId;
    private Long driverId;
}
