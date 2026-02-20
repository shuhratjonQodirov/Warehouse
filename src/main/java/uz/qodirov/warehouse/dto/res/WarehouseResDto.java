package uz.qodirov.warehouse.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseResDto {
    private Long id;
    private String name;
    private String address;
    private String description;
    private BigDecimal latitude;
    private BigDecimal longitude;
}
