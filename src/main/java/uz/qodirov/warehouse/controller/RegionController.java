package uz.qodirov.warehouse.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.qodirov.warehouse.dto.req.RegionReqDto;
import uz.qodirov.warehouse.service.RegionService;
import uz.qodirov.warehouse.utils.ApiResponse;

@RestController
@RequestMapping("/api/v1/region")
@RequiredArgsConstructor
public class RegionController {
    private final RegionService regionService;

    @PostMapping
    public HttpEntity<?> create(@RequestBody @Valid RegionReqDto dto) {
        ApiResponse<?> response = regionService.create(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getById(@PathVariable Long id) {
        ApiResponse<?> response = regionService.getById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public HttpEntity<?> getAll() {
        ApiResponse<?> response = regionService.getAll();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> update(@RequestBody @Valid RegionReqDto dto, @PathVariable Long id) {
        ApiResponse<?> response = regionService.update(dto,id);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/{id}")
    public HttpEntity<?> delete (@PathVariable Long id) {
        ApiResponse<?> response = regionService.delete(id);
        return ResponseEntity.ok(response);
    }


}
