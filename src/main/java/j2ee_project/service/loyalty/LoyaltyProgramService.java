package j2ee_project.service.loyalty;

import j2ee_project.repository.loyalty.LoyaltyProgramRepository;
import j2ee_project.model.loyalty.LoyaltyProgram;
import org.springframework.stereotype.Service;

@Service
public class LoyaltyProgramService {
    private final LoyaltyProgramRepository loyaltyProgramRepository;

    public LoyaltyProgramService(LoyaltyProgramRepository loyaltyProgramRepository) {
        this.loyaltyProgramRepository = loyaltyProgramRepository;
    }

    public LoyaltyProgram getLoyaltyProgram() {
        return loyaltyProgramRepository.findLoyaltyProgramById(1);
    }

}
