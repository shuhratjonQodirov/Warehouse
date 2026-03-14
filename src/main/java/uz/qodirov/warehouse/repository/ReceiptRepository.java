package uz.qodirov.warehouse.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.qodirov.warehouse.dto.res.ReceiptResDto;
import uz.qodirov.warehouse.model.Receipt;

public interface ReceiptRepository extends JpaRepository<Receipt, Long> {

    @Query(nativeQuery = true,value = "select r.id, p.name as productName,   w.name as warehouseName, s.name as supplierName,  r.quantity,    r.price,  r.receive_date as receiveDate ,r.document_number as documentNumber from receipt as r  inner join product p on r.product_id = p.id inner join warehouse w on r.warehouse_id = w.id  inner join supplier s on r.supplier_id = s.id")
    Page<ReceiptResDto> findAllByRec(Pageable pageable);
}
