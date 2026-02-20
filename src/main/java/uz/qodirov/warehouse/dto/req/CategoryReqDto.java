package uz.qodirov.warehouse.dto.req;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryReqDto {
    @NotBlank(message ="category name cannot be empty")
    private String name;
}
