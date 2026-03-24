package uz.qodirov.warehouse.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.qodirov.warehouse.dto.req.KindergartenReqDto;
import uz.qodirov.warehouse.service.KindergartenService;
import uz.qodirov.warehouse.utils.ApiResponse;

import static uz.qodirov.warehouse.utils.ApiConstanta.*;

@RestController
@RequestMapping("/api/v1/kindergarten")
@RequiredArgsConstructor
public class KindergartenController {
    private final KindergartenService kindergartenService;

    @PostMapping(CREATE)
    public HttpEntity<?> create(@RequestBody @Valid KindergartenReqDto dto) {
        ApiResponse<?> response = kindergartenService.create(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-by/{id}")
    public HttpEntity<?> getById(@PathVariable Long id) {
        ApiResponse<?> response = kindergartenService.getById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping(GET_ALL)
    public HttpEntity<?> getAll(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size) {
        ApiResponse<?> response = kindergartenService.getAll(page, size);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/region-filter/{regionId}")
    public HttpEntity<?> getKinderByFiltered(@RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "10") int size,
                                             @PathVariable Long regionId) {
        ApiResponse<?> response = kindergartenService.filteredByRegionId(page, size, regionId);
        return ResponseEntity.ok(response);
    }


    @DeleteMapping(DELETE)
    public HttpEntity<?> delete(@PathVariable Long id) {
        ApiResponse<?> response = kindergartenService.delete(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping(GET_ONE)
    public HttpEntity<?> getKGByUserId(@PathVariable Long id) {
        ApiResponse<?> response = kindergartenService.getKgByUserId(id);
        return ResponseEntity.ok(response);
    }

}
