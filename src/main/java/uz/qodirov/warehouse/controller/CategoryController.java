package uz.qodirov.warehouse.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.qodirov.warehouse.dto.req.CategoryReqDto;
import uz.qodirov.warehouse.service.CategoryService;
import uz.qodirov.warehouse.utils.ApiConstanta;
import uz.qodirov.warehouse.utils.ApiResponse;

import static uz.qodirov.warehouse.utils.ApiConstanta.*;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;


    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public HttpEntity<ApiResponse> create(@Valid @RequestBody CategoryReqDto dto) {
        ApiResponse<?> response = categoryService.create(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping(GET_ONE)
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','DRIVER','MUDIRA')")
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

    @PutMapping(UPDATE)
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public HttpEntity<?> update(@PathVariable Long id, @RequestBody @Valid CategoryReqDto dto) {
        ApiResponse<?> response = categoryService.update(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(DELETE)
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public HttpEntity<?> delete(@PathVariable Long id) {
        ApiResponse<?> response = categoryService.deleteCate(id);
        return ResponseEntity.ok(response);
    }


}
