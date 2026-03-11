package uz.qodirov.warehouse.service;

import uz.qodirov.warehouse.dto.req.MessageReqDto;
import uz.qodirov.warehouse.model.User;
import uz.qodirov.warehouse.utils.ApiResponse;

public interface MessageService {
    ApiResponse<?> sendMessage(String filter, MessageReqDto dto, User sender);

    ApiResponse<?> getAllMessages(int page, int size);
}
