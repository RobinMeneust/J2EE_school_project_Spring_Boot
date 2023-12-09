package j2ee_project.service.loyalty;

import j2ee_project.dao.loyalty.LoyaltyLevelRepository;
import j2ee_project.model.loyalty.LoyaltyLevel;

import java.util.List;

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
