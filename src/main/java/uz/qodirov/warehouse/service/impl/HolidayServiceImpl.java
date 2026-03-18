package uz.qodirov.warehouse.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.qodirov.warehouse.dto.req.HolidayReqDto;
import uz.qodirov.warehouse.dto.res.HolidayResDto;
import uz.qodirov.warehouse.error.ByIdException;
import uz.qodirov.warehouse.error.ExistsNameException;
import uz.qodirov.warehouse.mapper.HolidayMapper;
import uz.qodirov.warehouse.model.Holiday;
import uz.qodirov.warehouse.repository.HolidayRepository;
import uz.qodirov.warehouse.service.HolidayService;
import uz.qodirov.warehouse.utils.ApiResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HolidayServiceImpl implements HolidayService {
    private final HolidayRepository holidayRepository;
    private final HolidayMapper holidayMapper;

    @Override
    public ApiResponse<?> create(HolidayReqDto dto) {

        if (holidayRepository.existsByHolidayDateAndDeletedFalse(dto.getHolidayDate())) {
            throw new ExistsNameException("This date already exists");
        }
        Holiday holiday = holidayMapper.toEntity(dto);
        holidayRepository.save(holiday);
        return new ApiResponse<>("new holiday date created", true);
    }

    @Override
    public ApiResponse<?> getOneById(Long id) {
        Holiday holiday = holidayRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new ExistsNameException("holidays not found"));
        HolidayResDto dto = holidayMapper.toDto(holiday);
        return new ApiResponse<>("By id holidays", true, dto);
    }

    @Override
    public ApiResponse<?> getAllHolidaysBy(int year) {
        List<Holiday> holidayList = holidayRepository.findByYear(year);

        List<HolidayResDto> list = holidayList.stream().map(holidayMapper::toDto).toList();
        return new ApiResponse<>("list of holidays", true, list);
    }

    @Override
    @Transactional
    public ApiResponse<?> delete(Long id) {
        Holiday holiday = holidayRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new ByIdException("Holidays not found"));
        holiday.setDeleted(Boolean.TRUE);
        return new ApiResponse<>("Holidays deleted successfully", true);
    }

    @Override
    @Transactional
    public ApiResponse<?> update(Long id, HolidayReqDto dto) {
        Holiday holiday = holidayRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new ByIdException("Holidays not found"));
        holidayMapper.toUpdate(dto, holiday);
        return new ApiResponse<>("Holidays updated", true);
    }
}
