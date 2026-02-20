package uz.qodirov.warehouse.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
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
public class Stock extends AbsEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;
    @ManyToOne(fetch = FetchType.LAZY)
    private Warehouse wareHouse;

    private BigDecimal physicalQuantity = BigDecimal.ZERO; // Omborda bor yuk

    private BigDecimal reservedQuantity = BigDecimal.ZERO; // Taqsimlangan, lekin yetib bormagan

}