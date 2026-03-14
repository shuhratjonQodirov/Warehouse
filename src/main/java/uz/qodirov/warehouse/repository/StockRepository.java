package uz.qodirov.warehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.qodirov.warehouse.dto.res.StockProjection;
import uz.qodirov.warehouse.model.Product;
import uz.qodirov.warehouse.model.Stock;
import uz.qodirov.warehouse.model.Warehouse;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long> {

    Optional<Stock> findByProductAndWarehouse(Product product, Warehouse warehouse);


    List<Stock> findAllByWarehouseAndProductIdIn(Warehouse warehouse, List<Long> product_id);

    @Query(nativeQuery = true,value = "SELECT s.id, p.id as product_id, p.name as product_name, c.name as category_name, p.unit,w.id as warehouse_id, w.name as warehouse_name,s.physical_quantity, s.reserved_quantity FROM stock s INNER JOIN product p ON s.product_id = p.id INNER JOIN warehouse w ON s.warehouse_id = w.id LEFT JOIN category c ON c.id = p.category_id WHERE s.physical_quantity > 0 AND s.deleted = false")
    List<StockProjection> findAllByActive();
}
