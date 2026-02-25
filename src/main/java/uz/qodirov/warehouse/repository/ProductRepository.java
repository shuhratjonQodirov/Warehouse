package uz.qodirov.warehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.qodirov.warehouse.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
