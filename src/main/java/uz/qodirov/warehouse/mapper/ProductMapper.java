package uz.qodirov.warehouse.mapper;

import org.springframework.stereotype.Component;
import uz.qodirov.warehouse.dto.req.ProductReqDto;
import uz.qodirov.warehouse.dto.res.ProductResDto;
import uz.qodirov.warehouse.model.Category;
import uz.qodirov.warehouse.model.Product;

@Component
public class ProductMapper {
    public ProductResDto toDto(Product product) {
        return ProductResDto
                .builder()
                .id(product.getId())
                .name(product.getName())
                .categoryName(product.getCategory() == null ? null : product.getCategory().getName())
                .categoryId(product.getCategory() == null ? null : product.getCategory().getId())
                .unit(product.getUnit())
                .currentPrice(product.getCurrentPrice()).
                criticalLimit(product.getCriticalLimit()).
                description(product.getDescription()).
                build();
    }

    public Product toEntity(ProductReqDto dto, Category category) {
        return Product
                .builder()
                .name(dto.getName())
                .unit(dto.getUnit())
                .currentPrice(dto.getCurrentPrice())
                .criticalLimit(dto.getCriticalLimit())
                .category(category)
                .description(dto.getDescription())
                .build();

    }

    public void toUpdate(Product product, Category category, ProductReqDto dto) {
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setCriticalLimit(dto.getCriticalLimit());
        product.setCurrentPrice(dto.getCurrentPrice());
        product.setUnit(dto.getUnit());
        product.setCategory(category);
    }
}
