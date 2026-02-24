package uz.qodirov.warehouse.service;

import jakarta.validation.Valid;
import uz.qodirov.warehouse.dto.req.KindergartenReqDto;
import uz.qodirov.warehouse.utils.ApiResponse;

public interface KindergartenService {
    ApiResponse<?> create(@Valid KindergartenReqDto dto);

    ApiResponse<?> getById(Long id);

    ApiResponse<?> getAll(int page, int size);

    ApiResponse<?> filteredByRegionId(int page, int size, Long regionId);

    ApiResponse<?> delete(Long kindergartenId);

    ApiResponse<?> getKgByUserId(Long userId);
}
