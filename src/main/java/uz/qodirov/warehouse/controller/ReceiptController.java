package uz.qodirov.warehouse.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.qodirov.warehouse.dto.req.ReceiptReqDto;
import uz.qodirov.warehouse.service.ReceiptService;
import uz.qodirov.warehouse.utils.ApiResponse;

@RestController
@RequestMapping("/api/v1/receive")
@RequiredArgsConstructor
public class ReceiptController {
    private final ReceiptService receiptService;


    @PostMapping("/create")
    public HttpEntity<?> create(@RequestBody ReceiptReqDto dto) {
        ApiResponse<?> response = receiptService.create(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-all")
    public HttpEntity<?> getAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        ApiResponse<?> response = receiptService.getAll(page, size);
        return ResponseEntity.ok(response);
    }
}
