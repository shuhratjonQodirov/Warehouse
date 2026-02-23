package uz.qodirov.warehouse.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KindergartenListDto {
    private Long id;
    private String name;
    private String regionName;
    private String mudirName;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Integer totalChildren;
}
