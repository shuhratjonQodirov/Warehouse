package uz.qodirov.warehouse.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.qodirov.warehouse.dto.res.KindergartenListDto;
import uz.qodirov.warehouse.dto.res.KindergartenResDto;
import uz.qodirov.warehouse.model.Kindergarten;
import uz.qodirov.warehouse.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface KindergartenRepository extends JpaRepository<Kindergarten, Long> {
    @Query(nativeQuery = true, value = "SELECT k.id, k.name,r.id, r.name, u.id,u.full_name,k.latitude,k.longitude,k.total_children, k.total_children1to3,k.children1to3_3h, k.children1to3_9h,k.children1to3_12h,k.children1to3_24h, k.total_children3to7,k.children3to7_3h, k.children3to7_9h, k.children3to7_12h, k.children3to7_24h FROM kindergarten k INNER JOIN region r ON r.id = k.region_id LEFT JOIN users u ON u.id = k.mudir_id where k.id=:id and k.deleted=false;")
    Optional<KindergartenResDto> findByIdN(@Param("id") Long id);

    @Query(nativeQuery = true, value = "SELECT k.id,k.name,r.name,u.full_name,k.latitude,k.longitude,k.total_children FROM kindergarten k INNER JOIN region r ON r.id = k.region_id LEFT JOIN users u ON u.id = k.mudir_id ;")
    Page<KindergartenListDto> findAllList(Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT k.id, k.name, r.name, u.full_name, k.latitude, k.longitude, k.total_children FROM kindergarten k INNER JOIN region r ON r.id = k.region_id LEFT JOIN users u ON u.id = k.mudir_id where r.id=:regionId;")
    Page<KindergartenListDto> findAllListByRegionId(Pageable pageable, @Param("regionId") Long regionId);

    List<Kindergarten> findAllByMudir(User mudir);

    Optional<Kindergarten> findByMudir(User mudir);
}
