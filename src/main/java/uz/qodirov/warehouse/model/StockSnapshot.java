package uz.qodirov.warehouse.model;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.qodirov.warehouse.utils.AbsEntity;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Builder
@EntityListeners(AuditingEntityListener.class)
public class StockSnapshot extends AbsEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id", nullable = false)
    private Stock stock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receipt_id")
    private Receipt receipt; //

    @Column(nullable = false, precision = 15, scale = 3)
    private BigDecimal oldQuantity;

    @Column(nullable = false, precision = 15, scale = 3)
    private BigDecimal newQuantity;

    @Column(nullable = false, precision = 15, scale = 3)
    private BigDecimal reservedQuantity;

    @Column(nullable = false)
    private String action; // RECEIVE, SHIP, ADJUST

}
