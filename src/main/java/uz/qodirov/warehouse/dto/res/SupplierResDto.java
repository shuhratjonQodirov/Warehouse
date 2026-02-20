package uz.qodirov.warehouse.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SupplierResDto {
    private Long id;
    private String name;
    private String contactPerson;
    private String phoneNumber;
    private String tin;
    private String address;
    private Boolean active;
}
