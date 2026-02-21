package uz.qodirov.warehouse.mapper;

import org.springframework.stereotype.Component;
import uz.qodirov.warehouse.dto.req.WarehouseReqDto;
import uz.qodirov.warehouse.dto.res.WarehouseResDto;
import uz.qodirov.warehouse.model.Warehouse;

@Component
public class WarehouseMapper {
    public Warehouse toEntity(WarehouseReqDto dto) {
        return Warehouse.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .address(dto.getAddress())
                .longitude(dto.getLongitude())
                .latitude(dto.getLatitude())
                .build();
    }

    public WarehouseResDto toDto(Warehouse h) {
        return WarehouseResDto.builder()
                .id(h.getId())
                .name(h.getName())
                .address(h.getAddress())
                .description(h.getDescription())
                .latitude(h.getLatitude())
                .longitude(h.getLongitude())
                .build();
    }

    public void updateWarehouse(Warehouse warehouse, WarehouseReqDto dto) {
        warehouse.setName(dto.getName());
        warehouse.setAddress(dto.getAddress());
        warehouse.setDescription(dto.getDescription());
        warehouse.setLongitude(dto.getLongitude());
        warehouse.setLatitude(dto.getLatitude());
    }
}
