package uz.qodirov.warehouse.service;

import uz.qodirov.warehouse.dto.req.ProductNormReqDto;
import uz.qodirov.warehouse.utils.ApiResponse;

public interface ProductNormService {
    ApiResponse<?> create(ProductNormReqDto dto);

    ApiResponse<?> getAll(int page, int size);
}
