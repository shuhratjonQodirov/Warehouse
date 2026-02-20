package uz.qodirov.warehouse.service;

import jakarta.validation.Valid;
import uz.qodirov.warehouse.dto.req.SupplierReqDto;
import uz.qodirov.warehouse.utils.ApiResponse;

public interface SupplierService {
    ApiResponse<?> createNewSupplier(@Valid SupplierReqDto dto);

     ApiResponse<?> getOneById(Long id);

    ApiResponse<?> getAllWithPagination(int page, int size);

    ApiResponse<?> searchWithTinOrName(int page,int size,String tinOrName);

    ApiResponse<?> update(Long id, @Valid SupplierReqDto dto);

    ApiResponse<?> deleteSupplierById(Long id);
}
