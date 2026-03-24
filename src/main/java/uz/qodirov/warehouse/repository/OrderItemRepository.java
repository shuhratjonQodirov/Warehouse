package uz.qodirov.warehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.qodirov.warehouse.dto.res.OrderItemResDto;
import uz.qodirov.warehouse.model.OrderItem;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    @Query(nativeQuery = true, value = "SELECT p.name AS productName,p.id as productId, p.unit, oi.quantity,oi.working_days as days,kms.year_month AS yearMonth, w.name AS warehouseName ,w.id as warehouseId FROM order_item oi JOIN product p ON oi.product_id = p.id join kindergarten_monthly_supply kms on kms.id=oi.monthly_supply_id JOIN warehouse w ON oi.warehouse_id = w.id WHERE oi.order_id = :orderId")
    List<OrderItemResDto> findByOrder(@Param("orderId") Long orderId);

    List<OrderItem> findAllByOrderId(Long orderId);
}
