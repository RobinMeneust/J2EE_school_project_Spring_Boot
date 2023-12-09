package j2ee_project.dao.loyalty;

import j2ee_project.model.loyalty.LoyaltyProgram;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoyaltyProgramRepository extends JpaRepository<LoyaltyProgram,Long> {
    LoyaltyProgram findLoyaltyProgramById(int id);
}
