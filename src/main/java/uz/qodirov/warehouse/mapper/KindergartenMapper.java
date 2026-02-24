package uz.qodirov.warehouse.mapper;

import org.springframework.stereotype.Component;
import uz.qodirov.warehouse.dto.req.KindergartenReqDto;
import uz.qodirov.warehouse.dto.res.KindergartenResDto;
import uz.qodirov.warehouse.model.Kindergarten;
import uz.qodirov.warehouse.model.Region;
import uz.qodirov.warehouse.model.User;

@Component
public class KindergartenMapper {

    public Kindergarten toEntity(User user, Region region, KindergartenReqDto dto) {
        return Kindergarten
                .builder()
                .mudir(user)
                .region(region)
                .name(dto.getName()).latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .totalChildren(dto.getTotalChildren())
                .totalChildren1to3(dto.getTotalChildren1to3())
                .children1to3_3h(dto.getChildren1to3_3h())
                .children1to3_9h(dto.getChildren1to3_9h())
                .children1to3_12h(dto.getChildren1to3_12h())
                .children1to3_24h(dto.getChildren1to3_24h())
                .totalChildren3to7(dto.getTotalChildren3to7())
                .children3to7_3h(dto.getChildren3to7_3h())
                .children3to7_9h(dto.getChildren3to7_9h())
                .children3to7_12h(dto.getChildren3to7_12h())
                .children3to7_24h(dto.getChildren3to7_24h())
                .build();
    }

    public KindergartenResDto toDto(Kindergarten k) {
        return KindergartenResDto
                .builder()
                .id(k.getId())
                .name(k.getName())
                .regionId(k.getRegion().getId())
                .regionName(k.getRegion().getName())
                .mudirId(k.getMudir() == null ? null : k.getMudir().getId())
                .mudirName(k.getMudir() == null ? null : k.getMudir().getFullName())
                .longitude(k.getLongitude())
                .latitude(k.getLatitude())
                .totalChildren(k.getTotalChildren())
                .totalChildren1to3(k.getTotalChildren1to3())
                .children1to3_3h(k.getChildren1to3_3h())
                .children1to3_9h(k.getChildren1to3_9h())
                .children1to3_12h(k.getChildren1to3_12h())
                .children1to3_24h(k.getChildren1to3_24h())
                .totalChildren3to7(k.getTotalChildren3to7())
                .children3to7_3h(k.getChildren3to7_3h())
                .children3to7_9h(k.getChildren3to7_9h())
                .children3to7_12h(k.getChildren3to7_12h())
                .children3to7_24h(k.getChildren3to7_24h())
                .build();
    }
}
