package uz.qodirov.warehouse.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.qodirov.warehouse.dto.req.SupplierReqDto;
import uz.qodirov.warehouse.dto.res.SupplierResDto;
import uz.qodirov.warehouse.error.ByIdException;
import uz.qodirov.warehouse.error.ExistsNameException;
import uz.qodirov.warehouse.mapper.SupplierMapper;
import uz.qodirov.warehouse.model.Supplier;
import uz.qodirov.warehouse.repository.SupplierRepository;
import uz.qodirov.warehouse.service.SupplierService;
import uz.qodirov.warehouse.utils.ApiResponse;
import uz.qodirov.warehouse.utils.PaginationUtil;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {
    private final SupplierRepository supplierRepository;
    private final PaginationUtil paginationUtil;

    private final SupplierMapper mapper;

    @Override
    public ApiResponse<?> createNewSupplier(SupplierReqDto dto) {
        if (supplierRepository.existsByTin(dto.getTin())) {
            throw new ExistsNameException("This supplier name already exists");
        }
        Supplier supplier = mapper.toEntity(dto);
        supplierRepository.save(supplier);
        return new ApiResponse<>("Supplier created successfully", true);
    }

    @Override
    public ApiResponse<?> getOneById(Long id) {
        Supplier supplier = supplierRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new ByIdException("Supplier not found"));
        SupplierResDto dto = mapper.toDto(supplier);
        return new ApiResponse<>("Supplier by Id", true, dto);
    }

    @Override
    public ApiResponse<?> getAllWithPagination(int page, int size) {
        Pageable pageable = paginationUtil.createPageable(page, size);

        Page<Supplier> supplierPage = supplierRepository.findAllByDeletedFalse(pageable);
        List<SupplierResDto> list =
                supplierPage
                        .getContent()
                        .stream()
                        .map(mapper::toDto)
                        .toList();

        Map<String, Object> meta = paginationUtil.createMeta(supplierPage.getTotalElements(), page, size, supplierPage.getTotalPages());

        return new ApiResponse<>("Supplier", true, list, meta);
    }

    @Override
    public ApiResponse<?> searchWithTinOrName(int page, int size, String tinOrName) {
        tinOrName = tinOrName.trim();
        if (tinOrName.length() < 4) {
            return new ApiResponse<>("kamida 4 ta belgi bilan qidirish kerak", false, List.of());
        }
        Page<Supplier> result;
        Pageable pageable = paginationUtil.createPageable(page, size);
        if (tinOrName.chars().allMatch(Character::isDigit)) {
            result = supplierRepository.findByTinContainingIgnoreCase(tinOrName, pageable);
        } else {
            result = supplierRepository.findByNameContainingIgnoreCase(tinOrName, pageable);
        }
        List<SupplierResDto> list = result.getContent()
                .stream()
                .map(mapper::toDto)
                .toList();
        Map<String, Object> meta = paginationUtil.createMeta(result.getTotalElements(), page, size, result.getTotalPages());

        String message = result.getTotalElements() == 0
                ? "Hech qanday element topilmadi"
                : "Search with tin or name result";

        return new ApiResponse<>(message, true, list, meta);
    }

    @Override
    public ApiResponse<?> update(Long id, SupplierReqDto dto) {
        Supplier supplier = supplierRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new ByIdException("Supplier not found"));

        if (!supplier.getTin().equals(dto.getTin())
                && supplierRepository.existsByTin(dto.getTin())) {
            throw new ExistsNameException("This supplier TIN already exists");
        }

        mapper.updateEntityFromDto(dto, supplier);

        supplierRepository.save(supplier);

        return new ApiResponse<>("Updated supplier data", true);
    }

    @Override
    public ApiResponse<?> deleteSupplierById(Long id) {
        Supplier supplier = supplierRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new ByIdException("Supplier not found"));
        supplier.setDeleted(true);
        supplierRepository.save(supplier);
        return new ApiResponse<>("Supplier deleted successfully", true);
    }
}
