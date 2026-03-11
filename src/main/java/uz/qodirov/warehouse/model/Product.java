package uz.qodirov.warehouse.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.qodirov.warehouse.utils.AbsEntity;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(callSuper = true)
public class Product extends AbsEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String unit;

    private BigDecimal currentPrice = BigDecimal.ZERO;

    private BigDecimal criticalLimit = BigDecimal.ZERO;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    private String description;
}
