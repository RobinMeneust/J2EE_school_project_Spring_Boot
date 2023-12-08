package j2ee_project.dao.user;

import j2ee_project.dao.JPAUtil;
import j2ee_project.model.user.ForgottenPassword;
import j2ee_project.model.user.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

/**
 * Class that interact with the database to edit the ForgottenPassword table in the database (add or remove mails)
 *
 * @author Lucas Velay
 */
public class ForgottenPasswordDAO {

    /**
     * Add a forgotten password request
     *
     * @param forgottenPassword the forgotten password request
     */
    public static void addForgottenPassword(ForgottenPassword forgottenPassword) {
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.persist(forgottenPassword);

        transaction.commit();
        entityManager.close();
    }

    /**
     * Remove a forgotten password request
     *
     * @param forgottenPassword the forgotten password request
     */
    public static void removeForgottenPassword(ForgottenPassword forgottenPassword) {
        if(forgottenPassword != null) {
            EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();

            entityManager.remove(entityManager.merge(forgottenPassword));

            transaction.commit();
            entityManager.close();
        }
    }

    /**
     * Get forgotten password query from token
     *
     * @param token the token
     * @return the forgotten password
     */
    public static ForgottenPassword getForgottenPasswordFromToken(String token){
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        ForgottenPassword forgottenPassword;
        try{
            forgottenPassword = entityManager.createQuery("FROM ForgottenPassword WHERE token=:token", ForgottenPassword.class)
                    .setParameter("token", token)
                    .getSingleResult();
        }catch (Exception exception){
            forgottenPassword = null;
        }
        transaction.commit();
        entityManager.close();
        return  forgottenPassword;
    }

    /**
     * Get the user associated to the given forgotten password query
     *
     * @param forgottenPassword the forgotten password query
     * @return the user
     */
    public static User getUser(ForgottenPassword forgottenPassword){
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        ForgottenPassword forgottenPasswordManaged = entityManager.merge(forgottenPassword);
        System.out.println(forgottenPasswordManaged);
        User user = forgottenPasswordManaged.getUser();
        System.out.println(user);
        transaction.commit();
        entityManager.close();
        return user;
    }

}
