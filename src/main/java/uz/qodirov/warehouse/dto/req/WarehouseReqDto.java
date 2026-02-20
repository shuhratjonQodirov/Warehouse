package uz.qodirov.warehouse.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseReqDto {
    @NotBlank(message = "Warehouse name cannot be empty")
    private String name;
    @NotBlank(message = "Warehouse address cannot be empty")
    private String address;
    @NotBlank(message = "Warehouse description cannot be empty")
    private String description;
    @NotNull(message = "Warehouse latitude cannot ber empty")
    private BigDecimal latitude;
    @NotNull(message = "Warehouse longitude cannot ber empty")
    private BigDecimal longitude;
}
