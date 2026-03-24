package uz.qodirov.warehouse.dto.res;

import java.math.BigDecimal;
import java.util.Date;
public interface StockSnapshotDto {
    Long getSnapshotId();
    String getAction();
    BigDecimal getOldQuantity();
    BigDecimal getNewQuantity();
    BigDecimal getReservedQuantity();
    Date getCreatedAt();

    Long getUserId();
    String getUserFullName();
    String getUserUsername();

    Long getStockId();
    BigDecimal getStockPhysicalQuantity();
    BigDecimal getStockReservedQuantity();

    Long getReceiptId();
    BigDecimal getReceiptQuantity();
    BigDecimal getReceiptPrice();
    BigDecimal getReceiptTotalSum();
    String getReceiptDocumentNumber();
    Date getReceiptDate();

    Long getProductId();
    String getProductName();

    Long getWarehouseId();
    String getWarehouseName();

    Long getSupplierId();
    String getSupplierName();
}