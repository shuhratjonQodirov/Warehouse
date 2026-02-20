package uz.qodirov.warehouse.mapper;

import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Component;
import uz.qodirov.warehouse.dto.req.SupplierReqDto;
import uz.qodirov.warehouse.dto.res.SupplierResDto;
import uz.qodirov.warehouse.model.Supplier;

@Component
public class SupplierMapper {

    public Supplier toEntity(SupplierReqDto dto) {
        return Supplier.builder()
                .name(dto.getName())
                .contactPerson(dto.getContactPerson())
                .phoneNumber(dto.getPhoneNumber())
                .tin(dto.getTin())
                .address(dto.getAddress())
                .active(dto.getActive())
                .build();
    }

    public SupplierResDto toDto(Supplier supplier) {
        return SupplierResDto.builder()
                .id(supplier.getId())
                .name(supplier.getName())
                .contactPerson(supplier.getContactPerson())
                .phoneNumber(supplier.getPhoneNumber())
                .tin(supplier.getTin())
                .address(supplier.getAddress())
                .active(supplier.getActive())
                .build();
    }

    public void updateEntityFromDto(SupplierReqDto dto, Supplier supplier) {

        if (StringUtils.isNotBlank(dto.getName())) {
            supplier.setName(dto.getName());
        }

        if (StringUtils.isNotBlank(dto.getContactPerson())) {
            supplier.setContactPerson(dto.getContactPerson());
        }

        if (StringUtils.isNotBlank(dto.getPhoneNumber())){
            supplier.setPhoneNumber(dto.getPhoneNumber());
        }

        if (StringUtils.isNotBlank(dto.getTin())) {
            supplier.setTin(dto.getTin());
        }

        if (StringUtils.isNotBlank(dto.getAddress())) {
            supplier.setAddress(dto.getAddress());
        }

        supplier.setActive(dto.getActive());
    }
}
