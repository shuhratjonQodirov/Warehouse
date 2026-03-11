package uz.qodirov.warehouse.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.qodirov.warehouse.dto.req.UserReqDto;
import uz.qodirov.warehouse.service.UserService;
import uz.qodirov.warehouse.service.WorkingDayService;
import uz.qodirov.warehouse.utils.ApiResponse;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final WorkingDayService service;

    @GetMapping("/get-all")
    public HttpEntity<?> getAll(@RequestParam(defaultValue = "all") String filter,
                                @RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size
    ) {
        ApiResponse<?> response = userService.getAllUsersByFiltered(filter, page, size);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/check-username")
    public HttpEntity<?> checkUsername(@RequestParam String username) {
        ApiResponse<?> response = userService.checkUsername(username);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public HttpEntity<?> getById(@PathVariable Long id) {
        ApiResponse<?> response = userService.getById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create")
    public HttpEntity<?> createNewUser(@Valid @RequestBody UserReqDto dto) {
        ApiResponse<?> response = userService.create(dto);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{id}")
    public HttpEntity<?> update(@PathVariable Long id, @RequestBody @Valid UserReqDto dto) {
        ApiResponse<?> response = userService.update(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Long id) {
        ApiResponse<?> response = userService.delete(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/reset-password")
    public HttpEntity<?> resetPassword(@RequestParam String username) {
        ApiResponse<?> response = userService.resetPassword(username);
        return ResponseEntity.ok(response);
    }


}
