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

    /**
     * Find a loyalty program in the database according to its id
     *
     * @param id the id
     * @return the found loyalty program
     */
    LoyaltyProgram findLoyaltyProgramById(int id);
}
