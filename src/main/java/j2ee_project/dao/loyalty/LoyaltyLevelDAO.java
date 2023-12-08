package j2ee_project.dao.loyalty;

import j2ee_project.dao.JPAUtil;
import j2ee_project.model.loyalty.LoyaltyLevel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

/**
 * Class that interacts with the database to edit the Product table in the database or get data from it
 *
 * @author Robin Meneust
 */
public class LoyaltyLevelDAO {

    /**
     * Get loyalty level by id
     *
     * @param id the id of the loyalty level fetched
     * @return the loyalty level fetched
     */
    public static LoyaltyLevel getLoyaltyLevel(int id) {
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        LoyaltyLevel loyaltyLevel = entityManager.find(LoyaltyLevel.class,id);

        transaction.commit();
        entityManager.close();
        return loyaltyLevel;
    }
}
