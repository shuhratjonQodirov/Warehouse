package uz.qodirov.warehouse.mapper;

import org.springframework.stereotype.Component;
import uz.qodirov.warehouse.dto.req.UserReqDto;
import uz.qodirov.warehouse.dto.res.UserResDto;
import uz.qodirov.warehouse.enums.RoleName;
import uz.qodirov.warehouse.model.User;

@Component
public class UserMapper {
    public UserResDto toDto(User user) {
        return UserResDto
                .builder()
                .id(user.getId())
                .username(user.getUsername())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .chatId(user.getChatId())
                .brithDate(user.getBrithDate())
                .role(user.getRole().name())
                .build();
    }

    public User toEntity(UserReqDto dto) {
        return User.builder()
                .fullName(dto.getFullName()).username(dto.getUsername()).email(dto.getEmail())
                .phoneNumber(dto.getPhoneNumber())
                .password(dto.getPassword())
                .chatId(dto.getChatId())
                .brithDate(dto.getBrithDate())
                .role(RoleName.valueOf(dto.getRole()))
                .build();
    }
}
