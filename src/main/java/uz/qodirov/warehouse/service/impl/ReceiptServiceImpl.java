package uz.qodirov.warehouse.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.qodirov.warehouse.dto.req.ReceiptReqDto;
import uz.qodirov.warehouse.dto.req.ReceiptReqItem;
import uz.qodirov.warehouse.dto.res.ReceiptResDto;
import uz.qodirov.warehouse.error.ByIdException;
import uz.qodirov.warehouse.mapper.ReceiptMapper;
import uz.qodirov.warehouse.mapper.StockMapper;
import uz.qodirov.warehouse.mapper.StockSnapshotMapper;
import uz.qodirov.warehouse.model.*;
import uz.qodirov.warehouse.repository.*;
import uz.qodirov.warehouse.service.ReceiptService;
import uz.qodirov.warehouse.utils.ApiResponse;
import uz.qodirov.warehouse.utils.PaginationUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReceiptServiceImpl implements ReceiptService {
    private final ReceiptRepository receiptRepository;
    private final StockRepository stockRepository;
    private final ProductRepository productRepository;
    private final WarehouseRepository warehouseRepository;
    private final SupplierRepository supplierRepository;
    private final ReceiptMapper receiptMapper;
    private final StockSnapshotRepository stockSnapshotRepository;
    private final StockMapper stockMapper;
    private final PaginationUtil paginationUtil;
    private final StockSnapshotMapper snapshotMapper;

    @Override
    @Transactional
    public ApiResponse<?> create(ReceiptReqDto dto) {
        Warehouse warehouse = warehouseRepository.findByIdAndDeletedFalse(dto.getWarehouseId())
                .orElseThrow(() -> new ByIdException("Warehouse not found"));
        Supplier supplier = supplierRepository.findByIdAndDeletedFalse(dto.getSupplierId())
                .orElseThrow(() -> new ByIdException("Supplier not found"));

        List<Long> productIds =
                dto.getItems().stream().map(ReceiptReqItem::getProductId).toList();
        List<Product> productList =
                productRepository.findAllByIdInAndDeletedFalse(productIds);
        Map<Long, Product> productMap =
                productList.stream()
                        .collect(Collectors
                                .toMap(Product::getId, product -> product));

        List<Stock> stocks =
                stockRepository.findAllByWarehouseAndProductIdIn(warehouse, productIds);

        Map<Long, Stock> stockMap = stocks
                .stream()
                .collect(Collectors.toMap(
                        s -> s.getProduct().getId(),
                        s -> s));

        List<Receipt> receipts = new ArrayList<>();
        List<Stock> stocksToSave = new ArrayList<>();
        List<StockSnapshot> stockSnapshots = new ArrayList<>();


        for (ReceiptReqItem item : dto.getItems()) {
            Product product = productMap.get(item.getProductId());

            Receipt receipt = receiptMapper
                    .toEntity(product, warehouse, supplier, dto, item);
            receipts.add(receipt);

            product.setCurrentPrice(item.getPrice());

            Stock stock = stockMap.get(product.getId());

            if (stock == null) {
                stock = stockMapper.toEntity(warehouse, product, item.getQuantity());
                stockMap.put(product.getId(), stock);
            }

            StockSnapshot
                    stockSnapshot = snapshotMapper.toEntity(stock, receipt, item);
            stockSnapshots.add(stockSnapshot);


            stock.setPhysicalQuantity(stock.getPhysicalQuantity().add(item.getQuantity()));
            stocksToSave.add(stock);

        }

        receiptRepository.saveAll(receipts);
        stockRepository.saveAll(stocksToSave);
        stockSnapshotRepository.saveAll(stockSnapshots);


        return new ApiResponse<>("Maxsulot qabul qilindi", true);
    }

    @Override
    public ApiResponse<?> getAll(int page, int size) {
        Pageable pageable = paginationUtil.createPageable(page, size);
        Page<ReceiptResDto> allBy =
                receiptRepository.findAllByRec(pageable);
        Map<String, Object> meta = paginationUtil.createMeta(allBy.getTotalElements(), page, size, allBy.getTotalPages());
        List<ReceiptResDto> list = allBy.getContent();
        return new ApiResponse<>("List of receive", true, list, meta);
    }
}
