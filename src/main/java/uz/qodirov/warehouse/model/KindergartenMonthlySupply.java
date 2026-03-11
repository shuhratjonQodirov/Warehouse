package uz.qodirov.warehouse.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.qodirov.warehouse.utils.AbsEntity;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@EntityListeners(AuditingEntityListener.class)
public class KindergartenMonthlySupply extends AbsEntity {

    @Column(nullable = false, length = 7)
    private String yearMonth;  // "2026-03"

    @Column(nullable = false)
    private Integer totalWorkingDays;      // oyda jami ish kunlari (masalan 22)

    @Column(nullable = false)
    private Integer suppliedWorkingDays = 0;  // shu oygacha berilgan ish kunlari

    @Column
    private LocalDate lastSupplyDate;

    @Column(length = 500)
    private String note;

    @Transient
    public Integer getRemainingWorkingDays() {
        return totalWorkingDays - suppliedWorkingDays;
    }
}