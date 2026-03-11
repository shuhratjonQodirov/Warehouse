package uz.qodirov.warehouse.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.qodirov.warehouse.dto.req.MessageReqDto;
import uz.qodirov.warehouse.dto.res.MessageResponseDto;
import uz.qodirov.warehouse.dto.res.SendMailDto;
import uz.qodirov.warehouse.enums.RoleName;
import uz.qodirov.warehouse.mapper.MessageMapper;
import uz.qodirov.warehouse.model.Message;
import uz.qodirov.warehouse.model.User;
import uz.qodirov.warehouse.repository.MessageRepository;
import uz.qodirov.warehouse.repository.UserRepository;
import uz.qodirov.warehouse.service.EmailService;
import uz.qodirov.warehouse.service.MessageService;
import uz.qodirov.warehouse.utils.ApiResponse;
import uz.qodirov.warehouse.utils.PaginationUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final PaginationUtil paginationUtil;
    private final EmailService emailService;
    private final MessageMapper messageMapper;


    @Override
    @Transactional
    public ApiResponse<?> sendMessage(String filter, MessageReqDto messageReqDto, User sender) {
        RoleName roleName;

        try {
            roleName = RoleName.valueOf(filter.trim().toUpperCase());
        } catch (Exception e) {
            return new ApiResponse<>("Invalid role", false);
        }

        List<User> userList = userRepository.findAllByRoleAndDeletedFalse(roleName);

        Message message = messageMapper.toEntity(messageReqDto, roleName);
        messageRepository.save(message);

        userList.forEach(user -> {
            Map<String, Object> model = new HashMap<>();
            model.put("fullName", user.getFullName());
            model.put("messageText", messageReqDto.getMessage());
            model.put("senderName", "admin");
            SendMailDto mailDto = SendMailDto
                    .builder()
                    .sendTo(user.getEmail())
                    .subjectName("Yangi habar-ombor tizimi !")
                    .templateName("send-message.ftl")
                    .model(model)
                    .build();
            emailService.sendMail(mailDto);
        });


        return new ApiResponse<>("Message sent successfully", true);
    }

    @Override
    public ApiResponse<?> getAllMessages(int page, int size) {
        Pageable pageable = paginationUtil.createPageable(page, size);
        Page<Message> all = messageRepository.findAllByDeletedFalse(pageable);
        Map<String, Object> meta = paginationUtil.createMeta(all.getTotalElements(), page, size, all.getTotalPages());

        List<MessageResponseDto> list = all.getContent().stream().map(messageMapper::toDto).toList();

        return new ApiResponse<>("List of messages", true, list, meta);
    }
}
