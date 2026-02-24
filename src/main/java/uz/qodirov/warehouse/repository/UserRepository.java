package uz.qodirov.warehouse.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.qodirov.warehouse.enums.RoleName;
import uz.qodirov.warehouse.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsernameAndDeletedFalse(String username);

    Optional<User> findByIdAndDeletedFalse(Long id);

    Page<User> findAllByRoleAndDeletedFalse(RoleName role, Pageable pageable);

    Page<User> findAllByRoleNotAndDeletedFalse(RoleName roleName, Pageable pageable);

    boolean existsByUsernameIgnoreCase(String username);

    boolean existsByEmailIgnoreCase(String username);

}
