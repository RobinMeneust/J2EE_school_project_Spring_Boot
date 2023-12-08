package j2ee_project.dao.profile;

import j2ee_project.dao.JPAUtil;
import j2ee_project.model.loyalty.LoyaltyAccount;
import j2ee_project.model.loyalty.LoyaltyLevel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

/**
 * Class that interact with the database to edit the Loyalty table in the database
 *
 * @author Robin Meneust
 */
public class LoyaltyDAO {

    /**
     * Get a loyalty account by id
     *
     * @param loyaltyAccountId id of the loyalty account we want
     * @return a loyalty account
     */
    public static LoyaltyAccount getLoyaltyAccount(int loyaltyAccountId){
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        LoyaltyAccount loyaltyAccount = null;
        try {
            loyaltyAccount = entityManager.createQuery("FROM LoyaltyAccount WHERE id=:loyaltyAccountId", LoyaltyAccount.class).setParameter("loyaltyAccountId", loyaltyAccountId).getSingleResult();
        } catch (Exception ignore) {}

        transaction.commit();
        entityManager.close();
        return loyaltyAccount;
    }

    /**
     * Get loyalty levels list
     *
     * @return The list of all loyalty levels
     */
    public static List<LoyaltyLevel> getLoyaltyLevels(){
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        List<LoyaltyLevel> loyaltyLevels = entityManager.createQuery("FROM LoyaltyLevel ORDER BY requiredPoints", LoyaltyLevel.class).getResultList();

        transaction.commit();
        entityManager.close();
        return loyaltyLevels;
    }


    /**
     * Get a loyalty level by id
     *
     * @param idLoyaltyLevel the id of the searched loyalty level
     * @return the loyalty level
     */
    public static LoyaltyLevel getLoyaltyLevel(int idLoyaltyLevel) {
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        LoyaltyLevel loyaltyLevel = null;
        try {
            loyaltyLevel = entityManager.createQuery("FROM LoyaltyLevel WHERE id=:idLoyaltyLevel", LoyaltyLevel.class).setParameter("idLoyaltyLevel",idLoyaltyLevel).getSingleResult();
        } catch (Exception ignore) {}

        transaction.commit();
        entityManager.close();
        return loyaltyLevel;
    }

    /**
     * Mark in a loyalty account a loyalty level has "used"
     *
     * @param idLoyaltyAccount the id of the loyalty account where a level is marked as "used"
     * @param idLoyaltyLevel   the id of loyalty level marked as "used"
     */
    public static void createLevelUsed(int idLoyaltyAccount, int idLoyaltyLevel){
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        LoyaltyLevel loyaltyLevelUsed = entityManager.find(LoyaltyLevel.class, idLoyaltyLevel);
        LoyaltyAccount loyaltyAccount = entityManager.find(LoyaltyAccount.class, idLoyaltyAccount);
        loyaltyAccount.addLoyaltyLevelUsed(loyaltyLevelUsed);

        transaction.commit();
        entityManager.close();
    }

}
