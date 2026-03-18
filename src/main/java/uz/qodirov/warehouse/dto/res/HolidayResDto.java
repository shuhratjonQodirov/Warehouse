package uz.qodirov.warehouse.dto.res;

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
public class HolidayResDto {
    private Long id;
    private LocalDate holidayDate;
    private String name;
    private boolean isNational;
    private String yearMonth;
}
