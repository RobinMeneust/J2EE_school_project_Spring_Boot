package j2ee_project.service.loyalty;

import j2ee_project.dao.loyalty.LoyaltyLevelRepository;
import j2ee_project.model.loyalty.LoyaltyLevel;

public class LoyaltyLevelService {
    private final LoyaltyLevelRepository loyaltyLevelRepository;

    public LoyaltyLevelService(LoyaltyLevelRepository loyaltyLevelRepository) {
        this.loyaltyLevelRepository = loyaltyLevelRepository;
    }

    public LoyaltyLevel getLoyaltyLevel(int id) {
        return loyaltyLevelRepository.findLoyaltyLevelById(id);
    }
}
