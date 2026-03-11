package uz.qodirov.warehouse.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.qodirov.warehouse.enums.RoleName;
import uz.qodirov.warehouse.utils.AbsEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Data
@EntityListeners(AuditingEntityListener.class)
public class Message extends AbsEntity {
    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleName roleName;
}
