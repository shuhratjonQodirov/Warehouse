package uz.qodirov.warehouse.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.qodirov.warehouse.service.OrderService;

@RestController
@RequestMapping("/api/v1/order-item")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderItemService;

    @GetMapping("/way-billing/{orderId}")
    public ResponseEntity<byte[]> getWayBillings(@PathVariable Long orderId) {

        byte[] wayBillingPdf = orderItemService.generateWayBillings(orderId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "yuk_xati_" + orderId + ".pdf");

        return new ResponseEntity<>(wayBillingPdf, headers, HttpStatus.OK);

    }
}
