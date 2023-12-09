package j2ee_project.dao.user;

import j2ee_project.model.user.Moderator;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository to interacts with the Moderator table in the DB
 *
 * @author Robin Meneust
 */
public interface ModeratorRepository extends JpaRepository<Moderator,Long> {
    Moderator findById(int id);
    void deleteById(int id);
}
