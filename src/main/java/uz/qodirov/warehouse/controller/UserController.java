package uz.qodirov.warehouse.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.qodirov.warehouse.dto.req.UserReqDto;
import uz.qodirov.warehouse.dto.res.UserResDto;
import uz.qodirov.warehouse.service.UserService;
import uz.qodirov.warehouse.utils.ApiResponse;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    @GetMapping("/get-all")
    public HttpEntity<?> getAll(@RequestParam(defaultValue = "all") String filter,
                                @RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size) {
        ApiResponse<?> response = userService.getAllUsersByFiltered(filter, page, size);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/check-username")
    public HttpEntity<?> checkUsername(@RequestParam String username){
        ApiResponse<?> response=userService.checkUsername(username);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/create")
    public HttpEntity<?> createNewUser(@Valid @RequestBody UserReqDto dto){
        ApiResponse<?> response=userService.create(dto);
        return ResponseEntity.ok(response);
    }
}
