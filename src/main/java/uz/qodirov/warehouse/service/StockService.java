package uz.qodirov.warehouse.service;

import uz.qodirov.warehouse.utils.ApiResponse;

public interface StockService {
    ApiResponse<?> getAll(int page, int size);
}
