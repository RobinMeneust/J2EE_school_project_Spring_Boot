package j2ee_project.dao.loyalty;

import j2ee_project.model.loyalty.LoyaltyAccount;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository to interacts with the LoyaltyAccount table in the DB
 *
 * @author Robin Meneust
 */
public interface LoyaltyAccountRepository extends JpaRepository<LoyaltyAccount, Long> {
    LoyaltyAccount findLoyaltyAccountById(int id);
}
