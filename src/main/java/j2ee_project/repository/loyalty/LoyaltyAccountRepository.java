package j2ee_project.repository.loyalty;

import j2ee_project.model.loyalty.LoyaltyAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository to interacts with the LoyaltyAccount table in the DB
 *
 * @author Robin Meneust
 */
@Repository
public interface LoyaltyAccountRepository extends JpaRepository<LoyaltyAccount, Long> {
    /**
     * Find a loyalty account in the database according to its id
     *
     * @param id the id
     * @return the found loyalty account
     */
    LoyaltyAccount findLoyaltyAccountById(int id);
}
