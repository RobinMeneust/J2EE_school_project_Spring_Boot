package j2ee_project.repository.loyalty;

import j2ee_project.model.loyalty.LoyaltyLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository to interacts with the LoyaltyLevel table in the DB
 *
 * @author Robin Meneust
 */
@Repository
public interface LoyaltyLevelRepository extends JpaRepository<LoyaltyLevel, Long> {

    /**
     * Find a loyalty level in the database according to its id
     *
     * @param id the id
     * @return the found loyalty level
     */
    LoyaltyLevel findLoyaltyLevelById(int id);
}
