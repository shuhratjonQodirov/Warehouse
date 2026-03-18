package uz.qodirov.warehouse.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.qodirov.warehouse.utils.AbsEntity;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Warehouse extends AbsEntity {
    private String name;
    private String address;
    private String description;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Boolean active = true;
}
