package uz.qodirov.warehouse.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.qodirov.warehouse.model.Product;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findAllByDeletedFalseOrderByCreatedAtAsc(Pageable pageable);

    boolean existsByNameIgnoreCaseAndDeletedFalse(String name);

    Optional<Product> findByIdAndDeletedFalse(Long id);

}
