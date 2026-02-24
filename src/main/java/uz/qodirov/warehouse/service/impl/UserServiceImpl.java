package uz.qodirov.warehouse.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.qodirov.warehouse.dto.req.UserReqDto;
import uz.qodirov.warehouse.dto.res.SendMailDto;
import uz.qodirov.warehouse.dto.res.UserResDto;
import uz.qodirov.warehouse.enums.RoleName;
import uz.qodirov.warehouse.error.ByIdException;
import uz.qodirov.warehouse.error.ExistsNameException;
import uz.qodirov.warehouse.mapper.UserMapper;
import uz.qodirov.warehouse.model.Kindergarten;
import uz.qodirov.warehouse.model.User;
import uz.qodirov.warehouse.repository.KindergartenRepository;
import uz.qodirov.warehouse.repository.UserRepository;
import uz.qodirov.warehouse.service.EmailService;
import uz.qodirov.warehouse.service.UserService;
import uz.qodirov.warehouse.utils.ApiResponse;
import uz.qodirov.warehouse.utils.PaginationUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PaginationUtil pagination;
    private final UserMapper mapper;
    private final EmailService serviceMail;
    private final KindergartenRepository kindergartenRepository;


    @Override
    public ApiResponse<?> getAllUsersByFiltered(String filter, int page, int size) {
        Pageable pageable = pagination.createPageable(page, size);
        Page<User> userPage;

        String upperFilter = filter.trim().toUpperCase();

        if (upperFilter.equals(RoleName.MUDIRA.name())) {
            userPage = userRepository.findAllByRoleAndDeletedFalse(RoleName.MUDIRA, pageable);
        } else if (upperFilter.equals(RoleName.DRIVER.name())) {
            userPage = userRepository.findAllByRoleAndDeletedFalse(RoleName.DRIVER, pageable);
        } else if (upperFilter.equals(RoleName.MANAGER.name())) {
            userPage = userRepository.findAllByRoleAndDeletedFalse(RoleName.MANAGER, pageable);
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
        if (userRepository.existsByEmailIgnoreCase(dto.getEmail())) {
            throw new ExistsNameException("This email already exists");
        }

        User user = mapper.toEntity(dto);
        userRepository.save(user);
        // Email yuborish
        Map<String, Object> model = new HashMap<>();
        model.put("fullName", dto.getFullName());
        model.put("username", dto.getUsername());
        model.put("password", dto.getPassword());

        SendMailDto mailDto = SendMailDto.builder()
                .sendTo(dto.getEmail())
                .subjectName("Tizimga xush kelibsiz!")
                .templateName("mail-template.ftl")
                .model(model)
                .build();

        serviceMail.sendMail(mailDto);
        return new ApiResponse<>("New user created", true);
    }

    @Override
    @Transactional
    public ApiResponse<?> update(Long id, UserReqDto dto) {
        User user = userRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new ByIdException("User not found"));

        if (dto.getUsername() != null) {
            String newUsername = dto.getUsername().trim();
            if (!newUsername.equalsIgnoreCase(user.getUsername()) &&
                    userRepository.existsByUsernameIgnoreCase(newUsername)) {
                throw new ExistsNameException("Bu username allaqachon mavjud");
            }
        }

        if (dto.getEmail() != null) {
            String newEmail = dto.getEmail().trim();
            if (!newEmail.equalsIgnoreCase(user.getEmail()) &&
                    userRepository.existsByEmailIgnoreCase(newEmail)) {
                throw new ExistsNameException("Bu email allaqachon mavjud");
            }
        }

        mapper.toUpdate(user, dto);

        userRepository.save(user);
        return new ApiResponse<>("User updated", true);
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse<?> getById(Long id) {
        User user = userRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new ByIdException("User not found"));
        UserResDto dto = mapper.toDto(user);
        return new ApiResponse<>("User get by id", true, dto);
    }

    @Override
    @Transactional
    public ApiResponse<?> delete(Long id) {
        User user = userRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new ByIdException("User not found"));
        user.setDeleted(true);
        userRepository.save(user);
        List<Kindergarten> kindergartens = kindergartenRepository.findAllByMudir(user);
        kindergartens.forEach(kg -> kg.setMudir(null));
        kindergartenRepository.saveAll(kindergartens);
        return new ApiResponse<>("User deleted", true);
    }
}
