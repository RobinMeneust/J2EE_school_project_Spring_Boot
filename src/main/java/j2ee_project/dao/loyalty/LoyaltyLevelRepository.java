package j2ee_project.dao.loyalty;

import j2ee_project.model.loyalty.LoyaltyLevel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoyaltyLevelRepository extends JpaRepository<LoyaltyLevel, Long> {
    LoyaltyLevel findLoyaltyLevelById(int id);
}
