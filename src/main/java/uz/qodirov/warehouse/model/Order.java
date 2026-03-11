package uz.qodirov.warehouse.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.qodirov.warehouse.enums.OrderStatus;
import uz.qodirov.warehouse.utils.AbsEntity;


import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(callSuper = true)
public class Order extends AbsEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private Kindergarten kindergarten;

    @ManyToOne(fetch = FetchType.LAZY)
    private User driver;

    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.PREPARING;

    private BigDecimal totalAmount = BigDecimal.ZERO;
}