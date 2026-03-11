package uz.qodirov.warehouse.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MessageResponseDto {
    private Long id;
    private String messageText;
    private String targetRole;
    private String senderName;
    private LocalDateTime sentAt;
}