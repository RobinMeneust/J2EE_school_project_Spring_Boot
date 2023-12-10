package j2ee_project.repository.user;

import j2ee_project.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository to interacts with the User table in the DB
 *
 * @author Robin Meneust
 */
@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    /**
     * Count user in the database according to its email and phone number
     *
     * @param email the email
     * @param phoneNumber the phone number
     * @return the number of found user
     */
    int countByEmailOrPhoneNumber(String email, String phoneNumber);

    /**
     * Search user in the database according to its email
     *
     * @param email the email
     * @return the found user
     */
    User findByEmail(String email);

    /**
     * Search user in the database according to its id
     *
     * @param id the id
     * @return the user
     */
    User findById(int id);
}
