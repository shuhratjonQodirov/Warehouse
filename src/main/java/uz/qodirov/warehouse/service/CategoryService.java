package uz.qodirov.warehouse.service;

import jakarta.validation.Valid;
import uz.qodirov.warehouse.dto.req.CategoryReqDto;
import uz.qodirov.warehouse.utils.ApiResponse;

public interface CategoryService {
    ApiResponse<?> create(@Valid CategoryReqDto dto);

    ApiResponse<?> getById(Long id);

    ApiResponse<?> getAll();

    ApiResponse<?> update(Long id, @Valid CategoryReqDto dto);

    ApiResponse<?> deleteCate(Long id);
}
