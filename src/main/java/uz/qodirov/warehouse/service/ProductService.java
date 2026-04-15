package uz.qodirov.warehouse.service;

import jakarta.validation.Valid;
import uz.qodirov.warehouse.dto.req.ProductReqDto;
import uz.qodirov.warehouse.dto.res.ProductResDto;
import uz.qodirov.warehouse.utils.ApiResponse;

import java.util.List;

public interface ProductService {
    ApiResponse<?> create(@Valid ProductReqDto dto);

    ApiResponse<List<ProductResDto>> getAll(int page, int size);

    ApiResponse<?> deleted(Long id);

    ApiResponse<?> update(Long id, @Valid ProductReqDto dto);

    ApiResponse<?> getById(Long id);
}
