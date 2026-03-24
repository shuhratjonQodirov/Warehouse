package uz.qodirov.warehouse.service;

import uz.qodirov.warehouse.utils.ApiResponse;

public interface StockSnapshotService {
    ApiResponse<?> getData(int page, int size);
}
