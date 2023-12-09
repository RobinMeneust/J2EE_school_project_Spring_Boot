package j2ee_project.dao.loyalty;

import j2ee_project.model.loyalty.LoyaltyLevel;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository to interacts with the LoyaltyLevel table in the DB
 *
 * @author Robin Meneust
 */
public interface LoyaltyLevelRepository extends JpaRepository<LoyaltyLevel, Long> {
    LoyaltyLevel findLoyaltyLevelById(int id);
}
