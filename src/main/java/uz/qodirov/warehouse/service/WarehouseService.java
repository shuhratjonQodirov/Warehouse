package uz.qodirov.warehouse.service;

import jakarta.validation.Valid;
import uz.qodirov.warehouse.dto.req.WarehouseReqDto;
import uz.qodirov.warehouse.dto.res.WarehouseResDto;
import uz.qodirov.warehouse.utils.ApiResponse;

import java.util.List;

public interface WarehouseService {
    ApiResponse<?> create(@Valid WarehouseReqDto dto);

    ApiResponse<?> getOneById(Long id);

    ApiResponse<List<WarehouseResDto>> getAll(int page, int size);

    ApiResponse<?> searchWithName(int page, int size, String name);

    ApiResponse<?> update(@Valid WarehouseReqDto dto, Long id);

    ApiResponse<?> delete(Long id);
}
