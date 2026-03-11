package uz.qodirov.warehouse.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import uz.qodirov.warehouse.dto.req.MessageReqDto;
import uz.qodirov.warehouse.model.User;
import uz.qodirov.warehouse.service.MessageService;
import uz.qodirov.warehouse.utils.ApiResponse;

@RestController
@RequestMapping("/api/v1/message")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @PostMapping
    public HttpEntity<?> sendMessage(@RequestParam String filter,
                                     @RequestBody MessageReqDto dto,
                                     @AuthenticationPrincipal User sender) {
        ApiResponse<?> response = messageService.sendMessage(filter, dto, sender);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getMessages(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        ApiResponse<?> response = messageService.getAllMessages(page, size);
        return ResponseEntity.ok(response);
    }
}
