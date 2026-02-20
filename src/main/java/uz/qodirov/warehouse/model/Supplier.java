package uz.qodirov.warehouse.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.qodirov.warehouse.utils.AbsEntity;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@EntityListeners(AuditingEntityListener.class)
public class Supplier extends AbsEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String contactPerson;

    private String phoneNumber;

    @Column(nullable = false, unique = true)
    private String tin;

    private String address;

    private Boolean active = Boolean.TRUE;
}