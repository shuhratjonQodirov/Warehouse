package uz.qodirov.warehouse.service;

import jakarta.validation.Valid;
import uz.qodirov.warehouse.dto.req.UserReqDto;
import uz.qodirov.warehouse.utils.ApiResponse;

public interface UserService {
    ApiResponse<?> getAllUsersByFiltered(String filter, int page, int size);

    ApiResponse<?> checkUsername(String username);

    ApiResponse<?> create(@Valid UserReqDto dto);

    ApiResponse<?> update(Long id, @Valid UserReqDto dto);

    ApiResponse<?> getById(Long id);

    ApiResponse<?> delete(Long id);

    ApiResponse<?> resetPassword(String username);
}
