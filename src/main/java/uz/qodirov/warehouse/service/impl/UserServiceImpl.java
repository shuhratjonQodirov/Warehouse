package uz.qodirov.warehouse.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.qodirov.warehouse.dto.req.UserReqDto;
import uz.qodirov.warehouse.dto.res.UserResDto;
import uz.qodirov.warehouse.enums.RoleName;
import uz.qodirov.warehouse.error.ExistsNameException;
import uz.qodirov.warehouse.mapper.UserMapper;
import uz.qodirov.warehouse.model.User;
import uz.qodirov.warehouse.repository.UserRepository;
import uz.qodirov.warehouse.service.UserService;
import uz.qodirov.warehouse.utils.ApiResponse;
import uz.qodirov.warehouse.utils.PaginationUtil;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PaginationUtil pagination;
    private final UserMapper mapper;

    @Override
    public ApiResponse<?> getAllUsersByFiltered(String filter, int page, int size) {
        Pageable pageable = pagination.createPageable(page, size);
        Page<User> userPage;
        if (filter.toUpperCase().equals(RoleName.MUDIRA.name())) {
            userPage = userRepository.findAllByRoleAndDeletedFalse(RoleName.MUDIRA, pageable);
        } else {
            userPage = userRepository.findAllByRoleNotAndDeletedFalse(RoleName.ADMIN, pageable);
        }
        List<UserResDto> list = userPage.getContent().stream().map(mapper::toDto).toList();
        Map<String, Object> meta = pagination.createMeta(userPage.getTotalElements(), page, size, userPage.getTotalPages());

        return new ApiResponse<>("Filtered users", true, list, meta);
    }

    @Override
    public ApiResponse<?> checkUsername(String username) {
        if (userRepository.existsByUsernameIgnoreCase(username)) {
            return new ApiResponse<>("This username already exists", false);
        }
        return new ApiResponse<>("This username not exists", true);
    }

    @Override
    public ApiResponse<?> create(UserReqDto dto) {

        if (userRepository.existsByUsernameIgnoreCase(dto.getUsername())) {
            throw new ExistsNameException("This username already exists");
        }

        User user = mapper.toEntity(dto);
        userRepository.save(user);
        return new ApiResponse<>("New user created", true);
    }
}
