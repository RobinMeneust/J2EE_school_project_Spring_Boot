package j2ee_project.repository.loyalty;

import j2ee_project.model.loyalty.LoyaltyProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository to interacts with the LoyaltyProgram table in the DB
 *
 * @author Robin Meneust
 */
@Repository
public interface LoyaltyProgramRepository extends JpaRepository<LoyaltyProgram,Long> {
    LoyaltyProgram findLoyaltyProgramById(int id);
}
