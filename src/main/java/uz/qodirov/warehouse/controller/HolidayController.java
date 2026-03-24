package uz.qodirov.warehouse.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.qodirov.warehouse.dto.req.HolidayReqDto;
import uz.qodirov.warehouse.service.HolidayService;
import uz.qodirov.warehouse.utils.ApiResponse;
import uz.qodirov.warehouse.utils.IpUtils;

import static uz.qodirov.warehouse.utils.ApiConstanta.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/holidays")
@RequiredArgsConstructor
public class HolidayController {
    private final HolidayService holidayService;


    @PostMapping
    public HttpEntity<?> create(@Valid @RequestBody HolidayReqDto dto) {
        ApiResponse<?> response = holidayService.create(dto);
        log.info( IpUtils.getClientIp());
        return ResponseEntity.ok(response);
    }

    @GetMapping(GET_ONE)
    public HttpEntity<?> getOneById(@PathVariable Long id) {
        ApiResponse<?> response = holidayService.getOneById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public HttpEntity<?> getAllHolidaysBy(@RequestParam int yearMonth) {
        ApiResponse<?> response = holidayService.getAllHolidaysBy(yearMonth);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(DELETE)
    public HttpEntity<?> delete(@PathVariable Long id) {
        ApiResponse<?> response = holidayService.delete(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping(UPDATE)
    public HttpEntity<?> update(@PathVariable Long id, @Valid @RequestBody HolidayReqDto dto) {
        ApiResponse<?> response = holidayService.update(id, dto);
        return ResponseEntity.ok(response);
    }


}
