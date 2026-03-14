package uz.qodirov.warehouse.dto.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReceiptReqDto {
    private Long warehouseId;
    private Long supplierId;
    private String documentNumber;
    private LocalDate receiveDate;
   List<ReceiptReqItem> items;
}

