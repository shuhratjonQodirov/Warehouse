package uz.qodirov.warehouse.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.qodirov.warehouse.dto.req.SupplierReqDto;
import uz.qodirov.warehouse.service.SupplierService;
import uz.qodirov.warehouse.utils.ApiResponse;

@RestController
@RequestMapping("/api/v1/supplier")
@RequiredArgsConstructor
public class SupplierController {
    private final SupplierService supplierService;

    @PutMapping("/create")
    public HttpEntity<?> createSupplier(@RequestBody @Valid SupplierReqDto dto) {
        ApiResponse<?> response = supplierService.createNewSupplier(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getOneById(@PathVariable Long id) {
        ApiResponse<?> response = supplierService.getOneById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-all")
    public HttpEntity<?> getAll(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size) {
        ApiResponse<?> response = supplierService.getAllWithPagination(page, size);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public HttpEntity<?> searchWithTin(@RequestParam String tinOrName,
                                       @RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size) {
        ApiResponse<?> response = supplierService.searchWithTinOrName(page, size, tinOrName);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{id}")
    public HttpEntity<?> updateSupplier(@PathVariable Long id, @Valid @RequestBody SupplierReqDto dto) {
        ApiResponse<?> response = supplierService.update(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteSupplier(@PathVariable Long id) {
        ApiResponse<?> response = supplierService.deleteSupplierById(id);
        return ResponseEntity.ok(response);
    }

}
