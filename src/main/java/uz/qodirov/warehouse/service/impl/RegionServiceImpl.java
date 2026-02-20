package uz.qodirov.warehouse.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.qodirov.warehouse.dto.req.RegionReqDto;
import uz.qodirov.warehouse.dto.res.RegionResDto;
import uz.qodirov.warehouse.error.ByIdException;
import uz.qodirov.warehouse.error.ExistsNameException;
import uz.qodirov.warehouse.mapper.RegionMapper;
import uz.qodirov.warehouse.model.Region;
import uz.qodirov.warehouse.repository.RegionRepository;
import uz.qodirov.warehouse.service.RegionService;
import uz.qodirov.warehouse.utils.ApiResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RegionServiceImpl implements RegionService {
    private final RegionRepository regionRepository;
    private final RegionMapper mapper;


    @Override
    public ApiResponse<?> create(RegionReqDto dto) {
        String name = validName(dto.getName());
        if (regionRepository.existsByName(name)) {
            throw new ExistsNameException("This region is already exists");
        }
        dto.setName(name);
        Region region = mapper.toEntity(dto);
        regionRepository.save(region);
        return new ApiResponse<>("saved new region", true);
    }

    @Override
    public ApiResponse<?> update(RegionReqDto dto, Long id) {
        Region region = regionRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new ByIdException("Region not found"));
        String name = validName(dto.getName());

        if (!region.getName().equals(name) && regionRepository.existsByName(name)) {
            throw new ExistsNameException("This region already exists");
        }
        region.setName(name);
        regionRepository.save(region);
        RegionResDto res = mapper.toDto(region);

        return new ApiResponse<>("Region update", true, res);
    }

    @Override
    public ApiResponse<?> getAll() {

        List<RegionResDto> list = regionRepository.findAllByDeletedFalse().stream()
                .map(mapper::toDto).toList();

        return new ApiResponse<>("List of Region", true, list);
    }

    @Override
    public ApiResponse<?> getById(Long id) {
        Region region = regionRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new ByIdException("Region not found"));
        RegionResDto dto = mapper.toDto(region);
        return new ApiResponse<>("Region by id", true, dto);
    }

    @Override
    public ApiResponse<?> delete(Long id) {
        Region region = regionRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new ByIdException("Region not found"));
        regionRepository.delete(region);
        return new ApiResponse<>("Region successfully deleted",true);
    }

    private String validName(String name) {
        String s = name
                .trim()
                .replaceAll("[^A-Za-z'‘’ʻ]", "");
        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }
}
