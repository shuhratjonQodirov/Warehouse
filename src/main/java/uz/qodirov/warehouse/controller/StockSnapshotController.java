package uz.qodirov.warehouse.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.qodirov.warehouse.service.StockSnapshotService;
import uz.qodirov.warehouse.utils.ApiResponse;

@RestController
@RequestMapping("/api/v1/stock-snapshot")
@RequiredArgsConstructor
public class StockSnapshotController {
    private final StockSnapshotService snapshotService;

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getAuditLog(@RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "10") int size) {
        ApiResponse<?> response = snapshotService.getData(page, size);
        return ResponseEntity.ok(response);
    }

}
