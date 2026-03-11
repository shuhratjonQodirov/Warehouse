package uz.qodirov.warehouse.mapper;

import org.springframework.stereotype.Component;
import uz.qodirov.warehouse.dto.req.ProductNormReqDto;
import uz.qodirov.warehouse.dto.res.ProductNormResDto;
import uz.qodirov.warehouse.enums.AgeGroup;
import uz.qodirov.warehouse.enums.ConsumptionType;
import uz.qodirov.warehouse.enums.StayDuration;
import uz.qodirov.warehouse.model.Product;
import uz.qodirov.warehouse.model.ProductNorm;

@Component
public class ProductNormMapper {
    public ProductNorm toEntity(Product product, ProductNormReqDto dto) {
        return ProductNorm.builder()
                .product(product)
                .ageGroup(AgeGroup.valueOf(dto.getAgeGroup()))
                .stayDuration(StayDuration.valueOf(dto.getStayDuration()))
                .dailyPerChild(dto.getDailyPerChild())
                .consumptionType(ConsumptionType.valueOf(dto.getConsumptionType()))
                .repeatDays(dto.getRepeatDays())
                .unit(dto.getUnit())
                .sourceDocument(dto.getSourceDocument())
                .active(dto.getActive())
                .build();
    }

    public ProductNormResDto toDto(ProductNorm pn) {
        return ProductNormResDto
                .builder()
                .id(pn.getId())
                .productId(pn.getProduct() == null ? null : pn.getProduct().getId())
                .productName(pn.getProduct() == null ? null : pn.getProduct().getName())
                .ageGroup(pn.getAgeGroup().name())
                .stayDuration(pn.getStayDuration().name())
                .dailyPerChild(pn.getDailyPerChild())
                .consumptionType(pn.getConsumptionType().name())
                .unit(pn.getUnit())
                .repeatDays(pn.getRepeatDays())
                .sourceDocument(pn.getSourceDocument())
                .active(pn.getActive())
                .build();
    }
}
