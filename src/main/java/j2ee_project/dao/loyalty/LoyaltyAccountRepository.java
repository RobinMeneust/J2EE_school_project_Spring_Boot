package j2ee_project.dao.loyalty;

import j2ee_project.model.loyalty.LoyaltyAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoyaltyAccountRepository extends JpaRepository<LoyaltyAccount, Long> {
    LoyaltyAccount findLoyaltyAccountById(int id);
}
