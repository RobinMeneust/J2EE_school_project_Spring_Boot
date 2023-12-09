package j2ee_project.service.loyalty;

import j2ee_project.dao.loyalty.LoyaltyProgramRepository;
import j2ee_project.model.loyalty.LoyaltyProgram;

public class LoyaltyProgramService {
    private final LoyaltyProgramRepository loyaltyProgramRepository;

    public LoyaltyProgramService(LoyaltyProgramRepository loyaltyProgramRepository) {
        this.loyaltyProgramRepository = loyaltyProgramRepository;
    }

    public LoyaltyProgram getLoyaltyProgram() {
        return loyaltyProgramRepository.findLoyaltyProgramById(1);
    }

}
