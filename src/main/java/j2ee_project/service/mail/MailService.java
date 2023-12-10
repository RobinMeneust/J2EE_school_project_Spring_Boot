package j2ee_project.service.mail;

import j2ee_project.repository.mail.MailRepository;
import j2ee_project.model.Mail;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The type Mail service.
 */
@Service
@Transactional
public class MailService {
    /**
     * The Mail repository.
     */
    MailRepository mailRepository;

    /**
     * Instantiates a new Mail service.
     *
     * @param mailRepository the mail repository
     */
    public MailService(MailRepository mailRepository) {
        this.mailRepository = mailRepository;
    }

    /**
     * Add a mail to the database
     *
     * @param mail Mail object containing the mail data (addresses, body...)
     */
    public void addMail(Mail mail) {
        mailRepository.save(mail);
    }

    /**
     * Remove a mail from the database by using its ID
     *
     * @param mailId ID of the mail to be deleted
     */
    public void removeMail(int mailId) {
        mailRepository.removeMailById(mailId);
    }
}
