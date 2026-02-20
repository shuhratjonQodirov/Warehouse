package uz.qodirov.warehouse.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.qodirov.warehouse.enums.AgeGroup;
import uz.qodirov.warehouse.enums.ConsumptionType;
import uz.qodirov.warehouse.enums.StayDuration;
import uz.qodirov.warehouse.utils.AbsEntity;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(callSuper = true)
public class ProductNorm extends AbsEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AgeGroup ageGroup;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private StayDuration stayDuration;

    @Column(nullable = false, precision = 10, scale = 3)
    private BigDecimal dailyPerChild;  // 1 bolaga kunlik norma (gramm, ml, dona va h.k.)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ConsumptionType consumptionType = ConsumptionType.DAILY;

    private Integer repeatDays;  // masalan dekada bo'yicha (10 kun)

    @Column(nullable = false, length = 10)
    private String unit;  // "g", "ml", "dona", "litr", "kg" va h.k.

    @Column(length = 100)
    private String sourceDocument;  // "SanQvaN 0016-21 1-ilova" yoki "2-ilova"

    @Column(nullable = false)
    private Boolean active = Boolean.TRUE;
}