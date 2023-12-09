package j2ee_project.dao.mail;

import j2ee_project.model.Mail;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository to interacts with the Mail table in the DB
 *
 * @author Robin Meneust
 */
public interface MailRepository extends JpaRepository<Mail,Long> {
    void removeMailById(int id);
}
