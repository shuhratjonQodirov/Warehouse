package uz.qodirov.warehouse.service;

import jakarta.validation.Valid;
import uz.qodirov.warehouse.dto.req.HolidayReqDto;
import uz.qodirov.warehouse.utils.ApiResponse;

public interface HolidayService {
    ApiResponse<?> create(@Valid HolidayReqDto dto);

    ApiResponse<?> getOneById(Long id);

    ApiResponse<?> getAllHolidaysBy(int yearMonth);

    ApiResponse<?> delete(Long id);

    ApiResponse<?> update(Long id, @Valid HolidayReqDto dto);
}
