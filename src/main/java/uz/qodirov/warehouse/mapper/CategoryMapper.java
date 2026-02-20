package uz.qodirov.warehouse.mapper;

import org.springframework.stereotype.Component;
import uz.qodirov.warehouse.dto.req.CategoryReqDto;
import uz.qodirov.warehouse.dto.res.CategoryResDto;
import uz.qodirov.warehouse.model.Category;

@Component
public class CategoryMapper {
    public Category toEntity(CategoryReqDto dto) {
        return Category.builder().
                name(dto.getName())
                .build();
    }

    public CategoryResDto toDto(Category category) {
        return CategoryResDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}
