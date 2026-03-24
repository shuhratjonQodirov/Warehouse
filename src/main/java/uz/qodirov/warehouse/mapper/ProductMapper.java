package uz.qodirov.warehouse.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.qodirov.warehouse.dto.req.ProductReqDto;
import uz.qodirov.warehouse.dto.res.ProductResDto;
import uz.qodirov.warehouse.dto.res.StockProjection;
import uz.qodirov.warehouse.dto.res.StockResDto;
import uz.qodirov.warehouse.dto.res.WarehouseStockDto;
import uz.qodirov.warehouse.model.Category;
import uz.qodirov.warehouse.model.Product;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductMapper {

    private final WarehouseMapper warehouseMapper;

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

    public StockResDto mapProductStock(List<StockProjection> rows) {
        StockProjection first = rows.get(0);

        BigDecimal totalQuantity = rows
                .stream()
                .map(StockProjection::getPhysicalQuantity)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalReserved = rows
                .stream()
                .map(StockProjection::getReservedQuantity)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        List<WarehouseStockDto> warehouses =
                rows.stream()
                        .map(warehouseMapper::mapWarehouse)
                        .toList();

        return StockResDto.builder()
                .productId(first.getProductId())
                .productName(first.getProductName())
                .categoryName(first.getCategoryName())
                .unit(first.getUnit())
                .totalQuantity(totalQuantity)
                .totalReserved(totalReserved)
                .totalAvailable(totalQuantity.subtract(totalReserved))
                .warehouses(warehouses)
                .build();
    }
}
