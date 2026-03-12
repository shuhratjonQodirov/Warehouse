package uz.qodirov.warehouse.tgBot.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.qodirov.warehouse.tgBot.state.BotState;
import uz.qodirov.warehouse.utils.AbsEntity;

@Entity(name = "bot_users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EntityListeners(AuditingEntityListener.class)
public class BotUser extends AbsEntity {

    @Column(unique = true)
    private Long chatId;

    @Column(columnDefinition = "VARCHAR(50)")
    private String state = BotState.ENTER_USERNAME.name();

    private String tempUsername;
}