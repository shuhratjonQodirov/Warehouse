package uz.qodirov.warehouse.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.qodirov.warehouse.dto.req.AssignDriver;
import uz.qodirov.warehouse.dto.req.DistributionInfoReqDto;
import uz.qodirov.warehouse.dto.req.OrderReqDto;
import uz.qodirov.warehouse.service.DistributionService;
import uz.qodirov.warehouse.utils.ApiResponse;

@RestController
@RequestMapping("/api/v1/distribution")
@RequiredArgsConstructor
public class DistributionController {
    private final DistributionService distributionService;


    @PostMapping("/info")
    public HttpEntity<?> getInfo(@RequestBody DistributionInfoReqDto dto) {
        ApiResponse<?> response = distributionService.getInfo(dto);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/assign-driver")
    public HttpEntity<?> assignToDriver(@RequestBody AssignDriver assignDriver) {
        ApiResponse<?> response = distributionService.assignDriver(assignDriver);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create")
    public HttpEntity<?> create(@RequestBody OrderReqDto dto) {
        ApiResponse<?> response = distributionService.create(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-all")
    public HttpEntity<?> getAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        ApiResponse<?> response = distributionService.getAll(page, size);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getAll(@PathVariable Long id) {
        ApiResponse<?> response = distributionService.getOneByid(id);
        return ResponseEntity.ok(response);
    }


}
