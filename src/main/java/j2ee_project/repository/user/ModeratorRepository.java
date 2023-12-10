package j2ee_project.repository.user;

import j2ee_project.model.user.Moderator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository to interacts with the Moderator table in the DB
 *
 * @author Robin Meneust
 */
@Repository
public interface ModeratorRepository extends JpaRepository<Moderator,Long> {

    /**
     * Search moderator in the database according to its id
     *
     * @param id the id
     * @return the found moderator
     */
    Moderator findById(int id);

    /**
     * Delete customer in the database according to its id
     *
     * @param id the id
     */
    void deleteById(int id);
}
