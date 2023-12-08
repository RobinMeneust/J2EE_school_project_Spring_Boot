package j2ee_project.dao;

import j2ee_project.model.Mail;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.hibernate.Session;

/**
 * Class that interact with the database to edit the Mail table in the database (add or remove mails)
 *
 * @author Robin Meneust
 */
public class MailDAO {
    /**
     * Add a mail to the database
     * @param mail Mail object containing the mail data (addresses, body...)
     */
    public static void addMail(Mail mail) {
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.persist(mail);

        transaction.commit();
        entityManager.close();
    }

    /**
     * Remove a mail from the database by using its ID
     * @param mailId ID of the mail to be deleted
     */
    public static void removeMail(int mailId) {
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Mail mail = null;

        try {
            mail = entityManager.createQuery("FROM Mail WHERE id = :mailId", j2ee_project.model.Mail.class).getSingleResult();
        } catch (Exception ignore) {}

        if(mail != null) {
            entityManager.remove(mail);
            entityManager.getTransaction().commit();
        }

        entityManager.close();
    }
}