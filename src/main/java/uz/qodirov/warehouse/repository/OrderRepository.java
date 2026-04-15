package uz.qodirov.warehouse.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.qodirov.warehouse.dto.req.OrderListResDto;
import uz.qodirov.warehouse.dto.res.OrderDetailResDto;
import uz.qodirov.warehouse.dto.res.OrderItemResDto;
import uz.qodirov.warehouse.model.Order;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(nativeQuery = true, value = "select o.id as id, o.order_date as orderDate , o.status as status, o.total_amount as totalAmount, k.name as kindergartenName,u.full_name as driverName from orders o inner join kindergarten as k on o.kindergarten_id = k.id left join users as u on u.id=o.driver_id")
    List<OrderListResDto> findByRes(Pageable pageable);

    @Query(nativeQuery = true, value = "select o.id as id, o.order_date as orderDate , o.status as status, o.total_amount as totalAmount, k.name as kindergartenName,u.full_name as driverName from orders o inner join kindergarten as k on o.kindergarten_id = k.id left join users as u on u.id=o.driver_id WHERE k.mudir_id = :mudiraId ORDER BY o.created_at DESC")
    List<OrderListResDto> findByResForMudira(@Param("mudiraId") Long mudiraId, Pageable pageable);

    @Query(nativeQuery = true, value = "select o.id as id, o.order_date as orderDate , o.status as status, o.total_amount as totalAmount, k.name as kindergartenName,u.full_name as driverName from orders o inner join kindergarten as k on o.kindergarten_id = k.id left join users as u on u.id=o.driver_id WHERE o.driver_id = :driverId ORDER BY o.created_at DESC")
    List<OrderListResDto> findByResForDriver(@Param("driverId") Long driverId, Pageable pageable);

    Optional<Order> findByIdAndDeletedFalse(Long id);

    @Query(nativeQuery = true, value = "SELECT o.id,k.name AS kindergartenName,u.full_name AS driverName,o.status,o.total_amount AS totalAmount,o.order_date AS orderDate FROM orders o INNER JOIN kindergarten k ON o.kindergarten_id = k.id  LEFT JOIN users u ON u.id = o.driver_id WHERE o.id = :orderId;")
    Optional<OrderListResDto> findByIdQu(@Param("orderId") Long orderId);

    Optional<Order> findFirstByKindergartenAndStatusOrderByCreatedAtDesc(uz.qodirov.warehouse.model.Kindergarten kindergarten, uz.qodirov.warehouse.enums.OrderStatus status);
}
