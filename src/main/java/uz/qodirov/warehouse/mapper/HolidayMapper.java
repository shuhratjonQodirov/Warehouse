package uz.qodirov.warehouse.mapper;

import org.springframework.stereotype.Component;
import uz.qodirov.warehouse.dto.req.HolidayReqDto;
import uz.qodirov.warehouse.dto.res.HolidayResDto;
import uz.qodirov.warehouse.model.Holiday;

@Component
public class HolidayMapper {

    public Holiday toEntity(HolidayReqDto dto) {
        return Holiday.builder()
                .name(dto.getName())
                .holidayDate(dto.getHolidayDate())
                .yearMonth(dto.getYearMonth())
                .isNational(dto.isNational())
                .build();
    }

    public HolidayResDto toDto(Holiday holiday) {
        return HolidayResDto.builder()
                .id(holiday.getId())
                .name(holiday.getName())
                .holidayDate(holiday.getHolidayDate())
                .isNational(holiday.isNational())
                .yearMonth(holiday.getYearMonth())
                .build();
    }

    public void toUpdate(HolidayReqDto dto, Holiday holiday) {
        holiday.setName(dto.getName());
        holiday.setHolidayDate(dto.getHolidayDate());
        holiday.setNational(dto.isNational());
        holiday.setYearMonth(dto.getYearMonth());
    }
}
