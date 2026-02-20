package uz.qodirov.warehouse.model;

import jakarta.persistence.*;
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
public class Kindergarten extends AbsEntity {
    @Column(length = 100)
    private String name;

    private Integer totalChildren;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", nullable = false)
    private Region region;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mudir_id")
    private User mudir;

    private BigDecimal latitude;
    private BigDecimal longitude;

    @Column(nullable = false)
    private Integer children1to3_3h = 0;

    @Column(nullable = false)
    private Integer children1to3_9h = 0;

    @Column(nullable = false)
    private Integer children1to3_12h = 0;

    @Column(nullable = false)
    private Integer children1to3_24h = 0;

    @Column(nullable = false)
    private Integer children3to7_3h = 0;

    @Column(nullable = false)
    private Integer children3to7_9h = 0;

    @Column(nullable = false)
    private Integer children3to7_12h = 0;

    @Column(nullable = false)
    private Integer children3to7_24h = 0;
}
