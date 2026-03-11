package uz.qodirov.warehouse.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import uz.qodirov.warehouse.dto.req.ProductNormReqDto;
import uz.qodirov.warehouse.dto.res.ProductNormResDto;
import uz.qodirov.warehouse.error.ByIdException;
import uz.qodirov.warehouse.mapper.ProductNormMapper;
import uz.qodirov.warehouse.model.Product;
import uz.qodirov.warehouse.model.ProductNorm;
import uz.qodirov.warehouse.repository.ProductNormRepository;
import uz.qodirov.warehouse.repository.ProductRepository;
import uz.qodirov.warehouse.service.ProductNormService;
import uz.qodirov.warehouse.utils.ApiResponse;
import uz.qodirov.warehouse.utils.PaginationUtil;

import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class ProductNormServiceImpl implements ProductNormService {
    private final ProductRepository productRepository;
    private final ProductNormRepository productNormRepository;
    private final PaginationUtil paginationUtil;
    private final ProductNormMapper mapper;

    @Override
    public ApiResponse<?> create(ProductNormReqDto dto) {
        Product product = productRepository.findByIdAndDeletedFalse(dto.getProductId()).orElseThrow(() -> new ByIdException("Product not found"));
        ProductNorm productNorm = mapper.toEntity(product, dto);
        productNormRepository.save(productNorm);
        return new ApiResponse<>("New product norm created", true);
    }

    @Override
    public ApiResponse<?> getAll(int page, int size) {
        Pageable pageable = paginationUtil.createPageable(page, size);
        Page<ProductNorm> all = productNormRepository.findAllByDeletedFalse(pageable);
        Map<String, Object> meta = paginationUtil.createMeta(all.getTotalElements(), page, size, all.getTotalPages());
        List<ProductNormResDto> list = all
                .getContent()
                .stream()
                .map(mapper::toDto)
                .toList();
        return new ApiResponse<>("List of product norm", true, list, meta);
    }


}
