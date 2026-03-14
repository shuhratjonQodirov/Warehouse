package uz.qodirov.warehouse.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.qodirov.warehouse.dto.res.StockProjection;
import uz.qodirov.warehouse.dto.res.StockResDto;
import uz.qodirov.warehouse.dto.res.WarehouseStockDto;
import uz.qodirov.warehouse.mapper.ProductMapper;
import uz.qodirov.warehouse.mapper.WarehouseMapper;
import uz.qodirov.warehouse.repository.StockRepository;
import uz.qodirov.warehouse.service.StockService;
import uz.qodirov.warehouse.utils.ApiResponse;
import uz.qodirov.warehouse.utils.PaginationUtil;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {
    private final StockRepository stockRepository;
    private final PaginationUtil paginationUtil;

    private final ProductMapper productMapper;

    @Override
    public ApiResponse<?> getAll(int page, int size) {
        List<StockProjection> all = stockRepository.findAllByActive();

        Map<Long, List<StockProjection>> groped = all.stream().collect(Collectors.groupingBy(StockProjection::getProductId));

        List<StockResDto> list = groped.values().stream().map(productMapper::mapProductStock).toList();

        int totalPages = (int) Math.ceil((double) list.size() / size);

        Map<String, Object> meta = paginationUtil.createMeta(list.size(), page, size, totalPages);
        return new ApiResponse<>("List of ", true, list, meta);
    }

}
