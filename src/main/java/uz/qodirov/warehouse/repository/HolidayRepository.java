package uz.qodirov.warehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.qodirov.warehouse.model.Holiday;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface HolidayRepository extends JpaRepository<Holiday, Long> {


    List<Holiday> findByYearMonthAndDeletedFalse(String yearMonth);

    @Query(value = "select * from holiday where extract(year from holiday_date) = :year and deleted = false", nativeQuery = true)
    List<Holiday> findByYear(@Param("year") int year);

    Optional<Holiday> findByHolidayDateAndDeletedFalse(LocalDate date);

    boolean existsByHolidayDateAndDeletedFalse(LocalDate holidayDate);

    List<Holiday> findByHolidayDateBetween(LocalDate start, LocalDate end);

    Optional<Holiday> findByIdAndDeletedFalse(Long id);
}
