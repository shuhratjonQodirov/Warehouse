package uz.qodirov.warehouse.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.qodirov.warehouse.utils.AbsEntity;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@Builder
public class Holiday extends AbsEntity {

    @Column(nullable = false)
    private LocalDate holidayDate;  // 2026-03-08, 2026-03-21 va h.k.

    @Column(length = 200)
    private String name;  // "Xalqaro xotin-qizlar kuni", "Navro‘z"

    @Column(length = 7)
    private String yearMonth;  // "2026-03" (qidiruv uchun qulay)

    private boolean isNational = true;  // milliy bayram bo‘lsa true
}