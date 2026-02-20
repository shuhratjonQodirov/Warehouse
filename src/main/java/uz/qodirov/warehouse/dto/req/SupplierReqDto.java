package uz.qodirov.warehouse.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SupplierReqDto {

    @NotBlank(message = "Supplier name cannot be empty")
    private String name;

    @NotBlank(message = "Supplier contactPerson cannot be empty")
    private String contactPerson;

    @NotBlank(message = "Supplier phoneNumber cannot be empty")
    private String phoneNumber;

    @NotBlank(message = "Supplier tin cannot be empty")
    @Pattern(regexp = "^[0-9]{6,12}$",
    message = "Supplier TIN must contain only digits and be 6 to 12 digits long")
    private String tin;

    @NotBlank(message = "Supplier address cannot be empty")
    private String address;

    private Boolean active;
}
