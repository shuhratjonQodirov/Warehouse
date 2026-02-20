package uz.qodirov.warehouse.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.qodirov.warehouse.dto.req.CategoryReqDto;
import uz.qodirov.warehouse.dto.res.CategoryResDto;
import uz.qodirov.warehouse.error.ByIdException;
import uz.qodirov.warehouse.mapper.CategoryMapper;
import uz.qodirov.warehouse.model.Category;
import uz.qodirov.warehouse.repository.CategoryRepository;
import uz.qodirov.warehouse.service.CategoryService;
import uz.qodirov.warehouse.utils.ApiResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper mapper;

    @Override
    public ApiResponse<?> create(CategoryReqDto dto) {
        Category category = mapper.toEntity(dto);
        categoryRepository.save(category);
        return new ApiResponse<>("Category added successfully", true);
    }

    @Override
    public ApiResponse<?> getById(Long id) {
        Category category = categoryRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new ByIdException("Category not found"));
        CategoryResDto dto = mapper.toDto(category);
        return new ApiResponse<>("category by id", true, dto);
    }

    @Override
    public ApiResponse<List<CategoryResDto>> getAll() {
        List<CategoryResDto> list = categoryRepository.findAllByDeletedFalse().stream()
                .map(mapper::toDto)
                .toList();
        return new ApiResponse<>("List of Category", true, list);
    }

    @Override
    public ApiResponse<?> update(Long id, CategoryReqDto dto) {
        Category category = categoryRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new ByIdException("category bot found"));
        category.setName(dto.getName());
        categoryRepository.save(category);
        return new ApiResponse<>("Category successfully updated", true);
    }

    @Override
    public ApiResponse<?> deleteCate(Long id) {
        Category category = categoryRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new ByIdException("Category not found"));
        categoryRepository.delete(category);
        return new ApiResponse<>("Category deleted", true);
    }
}
