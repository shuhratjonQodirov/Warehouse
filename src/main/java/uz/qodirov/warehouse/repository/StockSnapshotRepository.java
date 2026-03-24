package uz.qodirov.warehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.qodirov.warehouse.dto.res.StockSnapshotDto;
import uz.qodirov.warehouse.model.StockSnapshot;

import java.util.List;

@Repository
public interface StockSnapshotRepository extends JpaRepository<StockSnapshot, Long> {
    @Query(
            value = """
                SELECT
                    ss.id AS snapshotId,
                    ss.action AS action,
                    ss.old_quantity AS oldQuantity,
                    ss.new_quantity AS newQuantity,
                    ss.reserved_quantity AS reservedQuantity,
                    ss.created_at AS createdAt,
                    
                    u.id AS userId,
                    u.full_name AS userFullName,
                    u.username AS userUsername,
                    
                    s.id AS stockId,
                    s.physical_quantity AS stockPhysicalQuantity,
                    s.reserved_quantity AS stockReservedQuantity,
                    
                    r.id AS receiptId,
                    r.quantity AS receiptQuantity,
                    r.price AS receiptPrice,
                    r.total_sum AS receiptTotalSum,
                    r.document_number AS receiptDocumentNumber,
                    r.receive_date AS receiptDate,
                    
                    p.id AS productId,
                    p.name AS productName,
                    
                    w.id AS warehouseId,
                    w.name AS warehouseName,
                    
                    sup.id AS supplierId,
                    sup.name AS supplierName
                FROM stock_snapshot ss
                LEFT JOIN stock s ON s.id = ss.stock_id
                LEFT JOIN receipt r ON r.id = ss.receipt_id
                LEFT JOIN product p ON s.product_id = p.id
                LEFT JOIN warehouse w ON s.warehouse_id = w.id
                LEFT JOIN supplier sup ON r.supplier_id = sup.id
                LEFT JOIN users u ON u.id = ss.created_by
                ORDER BY ss.created_at DESC
                """,
            nativeQuery = true
    )
    List<StockSnapshotDto> findAllSnapshots();
}
