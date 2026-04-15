package uz.qodirov.warehouse.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.qodirov.warehouse.dto.req.WarehouseReqDto;
import uz.qodirov.warehouse.dto.res.WarehouseResDto;
import uz.qodirov.warehouse.error.ByIdException;
import uz.qodirov.warehouse.mapper.WarehouseMapper;
import uz.qodirov.warehouse.model.Warehouse;
import uz.qodirov.warehouse.repository.WarehouseRepository;
import uz.qodirov.warehouse.service.WarehouseService;
import uz.qodirov.warehouse.utils.ApiResponse;
import uz.qodirov.warehouse.utils.PaginationUtil;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class WarehouseServiceImpl implements WarehouseService {
    private final WarehouseRepository warehouseRepository;
    private final WarehouseMapper mapper;
    private final PaginationUtil pagination;

    @Override
    public ApiResponse<?> create(WarehouseReqDto dto) {
        Warehouse warehouse = mapper.toEntity(dto);
        warehouseRepository.save(warehouse);
        return new ApiResponse<>("New warehouse created successfully", true);
    }

    @Override
    public ApiResponse<?> getOneById(Long id) {
        Warehouse warehouse = warehouseRepository
                .findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ByIdException("Warehouse not found"));
        WarehouseResDto dto = mapper.toDto(warehouse);
        return new ApiResponse<>("Warehouse get By id", true, dto);
    }

    @Override
    public ApiResponse<List<WarehouseResDto>> getAll(int page, int size) {
        Pageable pageable = pagination.createPageable(page, size);
        Page<Warehouse> all = warehouseRepository.findAllByDeletedFalse(pageable);

        List<WarehouseResDto> list =
                all
                        .getContent()
                        .stream()
                        .map(mapper::toDto)
                        .toList();

        Map<String, Object> meta = pagination
                .createMeta(all.getTotalElements(), page, size, all.getTotalPages());
        return new ApiResponse<>("Warehouse list with pagination", true, list, meta);
    }

    @Override
    public ApiResponse<?> searchWithName(int page, int size, String name) {
        name = name.trim();
        Pageable pageable = pagination.createPageable(page, size);
        Page<Warehouse> warehousePage = warehouseRepository
                .findByNameContainingIgnoreCase(name, pageable);
        Map<String, Object> meta = pagination
                .createMeta(warehousePage.getTotalElements(), page, size, warehousePage.getTotalPages());
        List<WarehouseResDto> list = warehousePage.getContent().stream().map(mapper::toDto).toList();
        return new ApiResponse<>("Search with name", true, list, meta);
    }

    @Override
    public ApiResponse<?> update(WarehouseReqDto dto, Long id) {
        Warehouse warehouse = warehouseRepository
                .findByIdAndDeletedFalse(id).orElseThrow(() -> new ByIdException("Warehouse not found"));
        mapper.updateWarehouse(warehouse, dto);
        warehouseRepository.save(warehouse);
        return new ApiResponse<>("warehouse updated", true);
    }

    @Override
    public ApiResponse<?> delete(Long id) {
        Warehouse warehouse = warehouseRepository
                .findByIdAndDeletedFalse(id).orElseThrow(() -> new ByIdException("Warehouse not found"));
        warehouse.setDeleted(true);
        warehouseRepository.save(warehouse);
        return new ApiResponse<>("Warehouse successfully deleted", true);
    }
}
