package uz.qodirov.warehouse.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.qodirov.warehouse.dto.req.ProductNormReqDto;
import uz.qodirov.warehouse.service.ProductNormService;
import uz.qodirov.warehouse.service.RegionService;
import uz.qodirov.warehouse.utils.ApiResponse;

@RestController
@RequestMapping("/api/v1/product-norm")
@RequiredArgsConstructor
public class ProductNormController {
    private final ProductNormService productNormService;
    private final RegionService regionService;


    @PostMapping("/create")
    public HttpEntity<?> create(@RequestBody ProductNormReqDto dto) {
        ApiResponse<?> response = productNormService.create(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-all")
    public HttpEntity<?> getAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "0") int size) {
        ApiResponse<?> response = productNormService.getAll(page, size);
        return ResponseEntity.ok(response);
    }
}
