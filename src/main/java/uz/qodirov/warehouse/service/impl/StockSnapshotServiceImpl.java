package uz.qodirov.warehouse.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uz.qodirov.warehouse.dto.res.StockSnapshotDto;
import uz.qodirov.warehouse.repository.StockSnapshotRepository;
import uz.qodirov.warehouse.service.StockSnapshotService;
import uz.qodirov.warehouse.utils.ApiResponse;
import uz.qodirov.warehouse.utils.PaginationUtil;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockSnapshotServiceImpl implements StockSnapshotService {

    private final StockSnapshotRepository stockSnapshotRepository;
    private final PaginationUtil paginationUtil;

    @Override
    public ApiResponse<?> getData(int page, int size) {
        List<StockSnapshotDto> dtoList = stockSnapshotRepository.findAllSnapshots();
        int totalPages = (int) Math.ceil((double) dtoList.size() / size);
        Map<String, Object> meta = paginationUtil.createMeta(dtoList.size(), page, size, totalPages);
        log.info(dtoList.toString());
        return new ApiResponse<>("List of audti log", true, dtoList, meta);
    }
}
