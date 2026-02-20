package uz.qodirov.warehouse.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.qodirov.warehouse.model.Supplier;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    boolean existsByTin(String tin);

    boolean existsByName(String name);

    Optional<Supplier> findByIdAndDeletedFalse(Long id);

    Optional<Supplier> findByTinAndDeletedFalse(String tin);

    Page<Supplier> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Supplier> findByTinContainingIgnoreCase(String tin, Pageable pageable);

    Page<Supplier> findAllByDeletedFalse(Pageable pageable);

}
