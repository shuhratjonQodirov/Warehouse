package uz.qodirov.warehouse.dto.req;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegionReqDto {
    @NotBlank(message = "Region name cannot be empty")
    private String name;
}
