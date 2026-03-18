package uz.qodirov.warehouse.service;

import uz.qodirov.warehouse.dto.req.AssignDriver;
import uz.qodirov.warehouse.dto.req.DistributionInfoReqDto;
import uz.qodirov.warehouse.dto.req.OrderReqDto;
import uz.qodirov.warehouse.utils.ApiResponse;

public interface DistributionService {
    ApiResponse<?> getInfo(DistributionInfoReqDto dto);

    ApiResponse<?> create(OrderReqDto dto);

    ApiResponse<?> getAll(int page, int size);

    ApiResponse<?> getOneByid(Long id);

    ApiResponse<?> assignDriver(AssignDriver assignDriver);
}
