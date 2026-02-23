package uz.qodirov.warehouse.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.qodirov.warehouse.dto.req.KindergartenReqDto;
import uz.qodirov.warehouse.dto.res.KindergartenListDto;
import uz.qodirov.warehouse.dto.res.KindergartenResDto;
import uz.qodirov.warehouse.error.ByIdException;
import uz.qodirov.warehouse.mapper.KindergartenMapper;
import uz.qodirov.warehouse.model.Kindergarten;
import uz.qodirov.warehouse.model.Region;
import uz.qodirov.warehouse.model.User;
import uz.qodirov.warehouse.repository.KindergartenRepository;
import uz.qodirov.warehouse.repository.RegionRepository;
import uz.qodirov.warehouse.repository.UserRepository;
import uz.qodirov.warehouse.service.KindergartenService;
import uz.qodirov.warehouse.utils.ApiResponse;
import uz.qodirov.warehouse.utils.PaginationUtil;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class KindergartenServiceImpl implements KindergartenService {
    private final KindergartenRepository kindergartenRepository;
    private final KindergartenMapper mapper;
    private final RegionRepository regionRepository;
    private final UserRepository userRepository;
    private final PaginationUtil pagination;

    @Override
    public ApiResponse<?> create(KindergartenReqDto dto) {
        User user = userRepository.findByIdAndDeletedFalse(dto.getMudirId()).orElseThrow(() -> new ByIdException("Mudir not found"));
        Region region = regionRepository.findByIdAndDeletedFalse(dto.getRegionId()).orElseThrow(() -> new ByIdException("Region not found"));
        Kindergarten kindergarten = mapper.toEntity(user, region, dto);
        kindergartenRepository.save(kindergarten);
        return new ApiResponse<>("New Kindergarten saved", true);
    }

    @Override
    public ApiResponse<?> getById(Long id) {
        KindergartenResDto kindergarten = kindergartenRepository.findByIdN(id).orElseThrow(() -> new ByIdException("Kinderganten not found"));
        return new ApiResponse<>("Kindergarten by id", true, kindergarten);
    }

    @Override
    public ApiResponse<?> getAll(int page, int size) {
        Pageable pageable = pagination.createPageable(page, size);
        Page<KindergartenListDto> list =
                kindergartenRepository
                        .findAllList(pageable);
        Map<String, Object> meta = pagination
                .createMeta(list.getTotalElements(),
                        page,
                        size,
                        list.getTotalPages());
        List<KindergartenListDto> content = list.getContent();
        return new ApiResponse<>("List of kindergarten", true, content, meta);
    }

    @Override
    public ApiResponse<?> filteredByRegionId(int page, int size, Long regionId) {
        Pageable pageable = pagination.createPageable(page, size);
        Page<KindergartenListDto> list = kindergartenRepository.findAllListByRegionId(pageable, regionId);
        Map<String, Object> meta = pagination
                .createMeta(list.getTotalElements(),
                        page,
                        size,
                        list.getTotalPages());
        List<KindergartenListDto> content = list.getContent();
        return new ApiResponse<>("List of kindergarten", true, content, meta);
    }

    @Override
    public ApiResponse<?> delete(Long kindergartenId) {


        return null;
    }
}
