package j2ee_project.service.loyalty;

import j2ee_project.repository.loyalty.LoyaltyLevelRepository;
import j2ee_project.model.loyalty.LoyaltyLevel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class LoyaltyLevelService {
    private final LoyaltyLevelRepository loyaltyLevelRepository;

    public LoyaltyLevelService(LoyaltyLevelRepository loyaltyLevelRepository) {
        this.loyaltyLevelRepository = loyaltyLevelRepository;
    }

    public LoyaltyLevel getLoyaltyLevel(int id) {
        return loyaltyLevelRepository.findLoyaltyLevelById(id);
    }

    public List<LoyaltyLevel> getLoyaltyLevels() {
        return loyaltyLevelRepository.findAll();
    }
}
