package uz.qodirov.warehouse.repository;

import kotlin.OptIn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.qodirov.warehouse.model.Kindergarten;
import uz.qodirov.warehouse.model.KindergartenMonthlySupply;
import uz.qodirov.warehouse.model.Product;

import java.util.List;
import java.util.Optional;

@Repository
public interface KindergartenMonthlySupplyRepository extends JpaRepository<KindergartenMonthlySupply, Long> {

    Optional<KindergartenMonthlySupply> findByKindergartenAndProductAndYearMonth(Kindergarten kindergarten, Product product, String yearMonth);

}
