package uz.qodirov.warehouse.dto.res;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReceiptResDto {
    Long id;
    private String productName;
    private String warehouseName;
    private String supplierName;
    private BigDecimal quantity;
    private BigDecimal price;
    private Date receiveDate;
    private String documentNumber;
}
