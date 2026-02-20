package uz.qodirov.warehouse.mapper;

import org.springframework.stereotype.Component;
import uz.qodirov.warehouse.dto.req.RegionReqDto;
import uz.qodirov.warehouse.dto.res.RegionResDto;
import uz.qodirov.warehouse.model.Region;

@Component
public class RegionMapper {
    public Region toEntity(RegionReqDto dto) {
        return Region.builder()
                .name(dto.getName())
                .build();
    }

    public RegionResDto toDto(Region region) {
        return RegionResDto
                .builder()
                .id(region.getId())
                .name(region.getName())
                .build();
    }
}
