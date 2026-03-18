package uz.qodirov.warehouse.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.qodirov.warehouse.model.Holiday;
import uz.qodirov.warehouse.repository.HolidayRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkingDayService {


    private final HolidayRepository holidayRepo;

    public int calculateWorkingDays(YearMonth yearMonth) {
        LocalDate start = yearMonth.atDay(1);
        LocalDate end = yearMonth.atEndOfMonth();

        int totalDays = end.getDayOfMonth();

        int weekendDays = 0;
        LocalDate current = start;
        while (!current.isAfter(end)) {
            DayOfWeek day = current.getDayOfWeek();
            if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) {
                weekendDays++;
            }
            current = current.plusDays(1);
        }

        List<Holiday> holidays = holidayRepo.findByYearMonthAndDeletedFalse(yearMonth.toString());

        int holidayOnWorkingDay = 0;
        for (Holiday h : holidays) {
            LocalDate date = h.getHolidayDate();
            DayOfWeek day = date.getDayOfWeek();
            if (day != DayOfWeek.SATURDAY && day != DayOfWeek.SUNDAY) {
                holidayOnWorkingDay++;
            }
        }

        return totalDays - weekendDays - holidayOnWorkingDay;
    }
}