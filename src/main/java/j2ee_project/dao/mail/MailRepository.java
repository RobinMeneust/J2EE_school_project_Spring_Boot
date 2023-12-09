package j2ee_project.dao.mail;

import j2ee_project.model.Mail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailRepository extends JpaRepository<Mail,Long> {
    void removeMailById(int id);
}
