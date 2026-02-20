package uz.qodirov.warehouse.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.qodirov.warehouse.dto.req.WarehouseReqDto;
import uz.qodirov.warehouse.service.WarehouseService;
import uz.qodirov.warehouse.utils.ApiResponse;

@RestController
@RequestMapping("/api/v1/warehouse")
@RequiredArgsConstructor
public class WarehouseController {
    private final WarehouseService warehouseService;

    @PostMapping("/create")
    public HttpEntity<?> create(@Valid @RequestBody WarehouseReqDto dto) {
        ApiResponse<?> response = warehouseService.create(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getOneById(@PathVariable Long id) {
        ApiResponse<?> response = warehouseService.getOneById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-all")
    public HttpEntity<?> getAll(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size) {
        ApiResponse<?> response = warehouseService.getAll(page, size);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public HttpEntity<?> searchWithName(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size, @RequestParam String name) {
        ApiResponse<?> response = warehouseService.searchWithName(page, size, name);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{id}")
    public HttpEntity<?> update(@RequestBody @Valid WarehouseReqDto dto, @PathVariable Long id) {
        ApiResponse<?> response = warehouseService.update(dto, id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public HttpEntity<?> delete(@PathVariable Long id) {
        ApiResponse<?> response = warehouseService.delete(id);
        return ResponseEntity.ok(response);
    }

}
