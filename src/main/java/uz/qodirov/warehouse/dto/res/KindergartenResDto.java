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
public class KindergartenResDto {
    private Long id;

    private String name;

    private Long regionId;
    private String regionName;

    private Long mudirId;
    private String mudirName;
    private BigDecimal latitude;
    private BigDecimal longitude;

    private Integer totalChildren;

    private Integer totalChildren1to3;
    private Integer children1to3_3h;
    private Integer children1to3_9h;
    private Integer children1to3_12h;
    private Integer children1to3_24h;

    private Integer totalChildren3to7;
    private Integer children3to7_3h;
    private Integer children3to7_9h;
    private Integer children3to7_12h;
    private Integer children3to7_24h;
}
