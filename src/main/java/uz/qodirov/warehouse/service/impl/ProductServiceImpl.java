package uz.qodirov.warehouse.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.qodirov.warehouse.dto.req.ProductReqDto;
import uz.qodirov.warehouse.dto.res.ProductResDto;
import uz.qodirov.warehouse.error.ByIdException;
import uz.qodirov.warehouse.error.ExistsNameException;
import uz.qodirov.warehouse.mapper.ProductMapper;
import uz.qodirov.warehouse.model.Category;
import uz.qodirov.warehouse.model.Product;
import uz.qodirov.warehouse.repository.CategoryRepository;
import uz.qodirov.warehouse.repository.ProductRepository;
import uz.qodirov.warehouse.service.ProductService;
import uz.qodirov.warehouse.utils.ApiResponse;
import uz.qodirov.warehouse.utils.PaginationUtil;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper mapper;
    private final PaginationUtil paginationUtil;

    @Override
    @Transactional
    public ApiResponse<?> create(ProductReqDto dto) {

        if (productRepository.existsByNameIgnoreCaseAndDeletedFalse(dto.getName())) {
            throw new ExistsNameException("This product name already exits");
        }

        Category category = categoryRepository.findByIdAndDeletedFalse(dto.getCategoryId()).orElseThrow(() -> new ByIdException("Category not found"));

        Product product = mapper.toEntity(dto, category);
        productRepository.save(product);
        return new ApiResponse<>("New category created", true);
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<?> getAll(int page, int size) {
        Pageable pageable = paginationUtil.createPageable(page, size);

        Page<Product> all = productRepository.findAllByDeletedFalseOrderByCreatedAtAsc(pageable);

        Map<String, Object> meta = paginationUtil.createMeta(all.getTotalElements(), page, size, all.getTotalPages());

        List<ProductResDto> list = all.stream().map(mapper::toDto).toList();

        return new ApiResponse<>("List of products", true, list, meta);
    }

    @Override
    @Transactional
    public ApiResponse<?> deleted(Long id) {
        Product product = productRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new ByIdException("Product not found"));
        product.setDeleted(Boolean.TRUE);
        productRepository.save(product);
        return new ApiResponse<>("Product deleted succes fully", true);
    }

    @Override
    @Transactional
    public ApiResponse<?> update(Long id, ProductReqDto dto) {
        Product product = productRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new ByIdException("Product not found"));
        Category category = categoryRepository.findByIdAndDeletedFalse(dto.getCategoryId()).orElseThrow(() -> new ByIdException("Category not found"));
        if (dto.getName() != null) {
            String newName = dto.getName().trim();
            if (!newName.equalsIgnoreCase(product.getName()) &&
                    productRepository.existsByNameIgnoreCaseAndDeletedFalse(newName)) {
                throw new ExistsNameException("Bu maxsulot nomi allaqachon mavjud");
            }
        }

        mapper.toUpdate(product, category, dto);
        productRepository.save(product);
        return new ApiResponse<>();
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<?> getById(Long id) {
        Product product = productRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new ByIdException("Product not found"));
        ProductResDto dto = mapper.toDto(product);
        return new ApiResponse<>("Product get by Id",true,dto);
    }
}
