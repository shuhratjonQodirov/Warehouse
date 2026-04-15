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

import static uz.qodirov.warehouse.utils.ApiConstanta.*;

@RestController
@RequestMapping("/api/v1/distribution")
@RequiredArgsConstructor
public class DistributionController {
    private final DistributionService distributionService;


    @PostMapping(INFO)
    public HttpEntity<?> getInfo(@RequestBody DistributionInfoReqDto dto) {
        ApiResponse<?> response = distributionService.getInfo(dto);
        return ResponseEntity.ok(response);
    }

    @PostMapping(ASSIGN_DRIVER)
    public HttpEntity<?> assignToDriver(@RequestBody AssignDriver assignDriver) {
        ApiResponse<?> response = distributionService.assignDriver(assignDriver);
        return ResponseEntity.ok(response);
    }

    @PostMapping(CREATE)
    public HttpEntity<?> create(@RequestBody OrderReqDto dto) {
        ApiResponse<?> response = distributionService.create(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping(GET_ALL)
    public HttpEntity<?> getAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        ApiResponse<?> response = distributionService.getAll(page, size);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/approve/{id}")
    public HttpEntity<?> approveOrder(@PathVariable Long id) {
        return ResponseEntity.ok(distributionService.approveOrder(id));
    }

    @PostMapping("/reject/{id}")
    public HttpEntity<?> rejectOrder(@PathVariable Long id, @RequestParam String reason) {
        return ResponseEntity.ok(distributionService.rejectOrder(id, reason));
    }

    @GetMapping(GET_ONE)
    public HttpEntity<?> getAll(@PathVariable Long id) {
        ApiResponse<?> response = distributionService.getOneByid(id);
        return ResponseEntity.ok(response);
    }




}
