package uz.qodirov.warehouse.mapper;

import org.springframework.stereotype.Component;
import uz.qodirov.warehouse.dto.req.MessageReqDto;
import uz.qodirov.warehouse.dto.res.MessageResponseDto;
import uz.qodirov.warehouse.enums.RoleName;
import uz.qodirov.warehouse.model.Message;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
public class MessageMapper {
    public Message toEntity(MessageReqDto messageReqDto, RoleName roleName) {
        return Message.builder()
                .text(messageReqDto.getMessage())
                .roleName(roleName)
                .build();
    }

    public MessageResponseDto toDto(Message message) {
        return MessageResponseDto.builder()
                .id(message.getId()).messageText(message.getText())
                .targetRole(message.getRoleName().name())
                .senderName("Admin")
                .sentAt(LocalDateTime.ofInstant(message.getCreatedAt(), ZoneId.systemDefault()))
                .build();
    }
}
