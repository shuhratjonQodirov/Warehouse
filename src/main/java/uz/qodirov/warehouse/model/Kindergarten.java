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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", nullable = false)
    private Region region;

    @Column(length = 100)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mudir_id")
    private User mudir;

    private BigDecimal latitude;
    private BigDecimal longitude;

    private Integer totalChildren;


    private Integer totalChildren1to3 = 0;

    @Column(nullable = false)
    private Integer children1to3_3h = 0;

    @Column(nullable = false)
    private Integer children1to3_9h = 0;

    @Column(nullable = false)
    private Integer children1to3_12h = 0;

    @Column(nullable = false)
    private Integer children1to3_24h = 0;


    private Integer totalChildren3to7 = 0;

    @Column(nullable = false)
    private Integer children3to7_3h = 0;

    @Column(nullable = false)
    private Integer children3to7_9h = 0;

    @Column(nullable = false)
    private Integer children3to7_12h = 0;

    @Column(nullable = false)
    private Integer children3to7_24h = 0;


    @PrePersist
    @PreUpdate
    private void updateTotalChildren1to3() {
        this.totalChildren1to3 =
                (this.children1to3_3h != null ? this.children1to3_3h : 0) +
                        (this.children1to3_9h != null ? this.children1to3_9h : 0) +
                        (this.children1to3_12h != null ? this.children1to3_12h : 0) +
                        (this.children1to3_24h != null ? this.children1to3_24h : 0);

        this.totalChildren3to7 =
                (this.children3to7_3h != null ? this.children3to7_3h : 0) +
                        (this.children3to7_9h != null ? this.children3to7_9h : 0) +
                        (this.children3to7_12h != null ? this.children3to7_12h : 0) +
                        (this.children3to7_24h != null ? this.children3to7_24h : 0);

        if (this.totalChildren == null || this.totalChildren == 0) {
            this.totalChildren = this.totalChildren1to3 + this.totalChildren3to7;
        }
    }


}
