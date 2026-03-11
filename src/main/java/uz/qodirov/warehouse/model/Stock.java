package uz.qodirov.warehouse.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
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
public class Stock extends AbsEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;
    @ManyToOne(fetch = FetchType.LAZY)
    private Warehouse wareHouse;

    private BigDecimal physicalQuantity = BigDecimal.ZERO; // Omborda bor yuk

    private BigDecimal reservedQuantity = BigDecimal.ZERO; // Taqsimlangan, lekin yetib bormagan

}