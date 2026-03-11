package uz.qodirov.warehouse.service;

import jakarta.validation.Valid;
import uz.qodirov.warehouse.dto.req.ProductReqDto;
import uz.qodirov.warehouse.utils.ApiResponse;

public interface ProductService {
    ApiResponse<?> create(@Valid ProductReqDto dto);

    ApiResponse<?> getAll(int page, int size);

    ApiResponse<?> deleted(Long id);

    ApiResponse<?> update(Long id, @Valid ProductReqDto dto);

    ApiResponse<?> getById(Long id);
}
