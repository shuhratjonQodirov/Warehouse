package uz.qodirov.warehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.qodirov.warehouse.model.Holiday;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface HolidayRepository extends JpaRepository<Holiday, Long> {
    // Asosiy metod: berilgan oy bo‘yicha barcha bayramlarni olish
    List<Holiday> findByYearMonth(String yearMonth);

    // Qo‘shimcha qidiruvlar (foydali bo‘ladi)
    Optional<Holiday> findByHolidayDate(LocalDate date);

    List<Holiday> findByHolidayDateBetween(LocalDate start, LocalDate end);
}
