package uz.qodirov.warehouse.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductReqDto {
    @NotBlank(message = "Product name cannot be empty")
    private String name;
    @NotBlank(message = "Product name cannot be empty")
    private String unit;
    @NotNull(message = "Product  currentPrice be null")
    private BigDecimal currentPrice;
    @NotNull(message = "category id cannot be null")
    private Long categoryId;
    @NotNull(message = "Product criticalLimit cannot be null")
    private BigDecimal criticalLimit;
    @NotNull(message = "Product description cannot be empty")
    private String description;
}
