package uz.qodirov.warehouse.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.qodirov.warehouse.model.ProductNorm;

@Repository
public interface ProductNormRepository extends JpaRepository<ProductNorm, Long> {

    Page<ProductNorm> findAllByDeletedFalse(Pageable pageable);
}
