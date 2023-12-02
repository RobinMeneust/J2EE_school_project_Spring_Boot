package j2ee_project.dao;

import java.util.Optional;
import j2ee_project.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> getUserById(Long id);
//    @Query("select role from Role role")
//    Stream<Role> getAllRolesStream();
}