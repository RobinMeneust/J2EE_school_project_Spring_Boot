package j2ee_project.repository.mail;

import j2ee_project.model.Mail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository to interacts with the Mail table in the DB
 *
 * @author Robin Meneust
 */
@Repository
public interface MailRepository extends JpaRepository<Mail,Long> {
    /**
     * Delete a mail in the database according to its id
     *
     * @param id the id
     */
    void removeMailById(int id);
}
