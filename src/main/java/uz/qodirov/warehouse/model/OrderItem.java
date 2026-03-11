package uz.qodirov.warehouse.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.qodirov.warehouse.utils.AbsEntity;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OrderItem extends AbsEntity {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private BigDecimal quantity;             // haqiqiy yuborilgan miqdor

    private BigDecimal priceAtOrder;
    private BigDecimal totalPrice;

    @Column(nullable = false)
    private Integer workingDays;             // har mahsulot uchun alohida ish kunlari soni

    private BigDecimal recommendedQuantity;

    @Column(length = 7, nullable = false)
    private String yearMonth;                // "2026-03"

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "monthly_supply_id", nullable = false)
    private KindergartenMonthlySupply monthlySupply;  // ← har bir mahsulot uchun alohida oy holati
}