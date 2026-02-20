package uz.qodirov.warehouse.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.qodirov.warehouse.model.Warehouse;

import java.util.Optional;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
    Optional<Warehouse> findByIdAndDeletedFalse(Long id);

    Page<Warehouse> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Warehouse> findAllByDeletedFalse(Pageable pageable);


}
