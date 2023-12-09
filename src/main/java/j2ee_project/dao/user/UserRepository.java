package j2ee_project.dao.user;

import j2ee_project.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository to interacts with the User table in the DB
 *
 * @author Robin Meneust
 */
public interface UserRepository extends JpaRepository<User,Long> {
    int countByEmailOrPhoneNumber(String email, String phoneNumber);
    User findByEmail(String email);

    User findById(int id);
}
