package uz.qodirov.warehouse.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class HolidayReqDto {
    @NotNull(message = "holiday date cannot be null")
    private LocalDate holidayDate;
    @NotBlank(message = "holiday's name cannot be empty")
    private String name;
    @NotNull
    private boolean isNational;
    @NotBlank(message = "year of month cannot be empty")
    private String yearMonth;
}
