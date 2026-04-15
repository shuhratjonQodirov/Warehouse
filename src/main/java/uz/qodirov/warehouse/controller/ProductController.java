package uz.qodirov.warehouse.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.qodirov.warehouse.annotation.EncryptResponse;
import uz.qodirov.warehouse.dto.req.ProductReqDto;
import uz.qodirov.warehouse.dto.res.ProductResDto;
import uz.qodirov.warehouse.service.ProductService;
import uz.qodirov.warehouse.utils.ApiResponse;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("/create")
    public HttpEntity<?> create(@RequestBody @Valid ProductReqDto dto) {
        ApiResponse<?> response = productService.create(dto);
        return ResponseEntity.ok(response);
    }

    @EncryptResponse(encrypted = true)
    @GetMapping("/get-all")
    public HttpEntity<?> getAll(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size) {
        ApiResponse<List<ProductResDto>> response = productService.getAll(page, size);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{id}")
    public HttpEntity<?> update(@PathVariable Long id, @RequestBody @Valid ProductReqDto dto) {
        ApiResponse<?> response = productService.update(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Long id) {
        ApiResponse<?> response = productService.deleted(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getById(@PathVariable Long id) {
        ApiResponse<?> response = productService.getById(id);
        return ResponseEntity.ok(response);
    }


}
