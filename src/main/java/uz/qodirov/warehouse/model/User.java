package uz.qodirov.warehouse.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.qodirov.warehouse.enums.RoleName;
import uz.qodirov.warehouse.tgBot.state.BotState;
import uz.qodirov.warehouse.utils.AbsEntity;

import java.time.LocalDate;

@Entity(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EntityListeners(AuditingEntityListener.class)
public class User extends AbsEntity {
    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private LocalDate brithDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleName role;

    @Column(nullable = false, unique = true)
    private Long chatId;

    @Column(columnDefinition = "VARCHAR(50)")
    private String state = BotState.REGISTRATION.name();
}
