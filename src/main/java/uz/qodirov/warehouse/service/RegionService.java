package uz.qodirov.warehouse.service;

import jakarta.validation.Valid;
import uz.qodirov.warehouse.dto.req.RegionReqDto;
import uz.qodirov.warehouse.utils.ApiResponse;

public interface RegionService {
    ApiResponse<?> create(@Valid RegionReqDto dto);

    ApiResponse<?> update(@Valid RegionReqDto dto, Long id);

    ApiResponse<?> getAll();

    ApiResponse<?> getById(Long id);

    ApiResponse<?> delete(Long id);
}
