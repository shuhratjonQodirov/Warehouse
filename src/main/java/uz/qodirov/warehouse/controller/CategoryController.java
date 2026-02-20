package uz.qodirov.warehouse.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.qodirov.warehouse.dto.req.CategoryReqDto;
import uz.qodirov.warehouse.service.CategoryService;
import uz.qodirov.warehouse.utils.ApiResponse;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;


    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public HttpEntity<?> create(@Valid @RequestBody CategoryReqDto dto) {
        ApiResponse<?> response = categoryService.create(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','DRIVER','MUDIR')")
    public HttpEntity<?> getOneById(@PathVariable Long id) {
        ApiResponse<?> response = categoryService.getById(id);
        return ResponseEntity.ok(response);

    }

    @GetMapping("/getAll")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','DRIVER','MUDIRA')")
    public HttpEntity<?> getAll() {
        ApiResponse<?> response = categoryService.getAll();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public HttpEntity<?> update(@PathVariable Long id, @RequestBody @Valid CategoryReqDto dto) {
        ApiResponse<?> response = categoryService.update(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public HttpEntity<?> delete(@PathVariable Long id) {
        ApiResponse<?> response = categoryService.deleteCate(id);
        return ResponseEntity.ok(response);
    }


}
