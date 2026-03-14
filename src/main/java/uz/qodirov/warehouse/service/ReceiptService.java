package uz.qodirov.warehouse.service;

import uz.qodirov.warehouse.dto.req.ReceiptReqDto;
import uz.qodirov.warehouse.utils.ApiResponse;

public interface ReceiptService {
    ApiResponse<?> create(ReceiptReqDto dto);

    ApiResponse<?> getAll(int page, int size);
}
