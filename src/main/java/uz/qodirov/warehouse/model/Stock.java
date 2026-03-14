package uz.qodirov.warehouse.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.qodirov.warehouse.utils.AbsEntity;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
@EntityListeners(AuditingEntityListener.class)
@Table(name = "stock", uniqueConstraints = {@UniqueConstraint(columnNames = {"product_id", "warehouse_id"})})
public class Stock extends AbsEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    @Column(nullable = false, precision = 15, scale = 3)
    private BigDecimal physicalQuantity = BigDecimal.ZERO; // Omborda bor yuk

    @Column(nullable = false, precision = 15, scale = 3)
    private BigDecimal reservedQuantity = BigDecimal.ZERO; // Taqsimlangan, lekin yetib bormagan

    public BigDecimal getAvailableQuantity() {
        return physicalQuantity.subtract(reservedQuantity);
    }
}