package j2ee_project.service.loyalty;

import j2ee_project.repository.loyalty.LoyaltyProgramRepository;
import j2ee_project.model.loyalty.LoyaltyProgram;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The type Loyalty program service.
 */
@Service
@Transactional
public class LoyaltyProgramService {
    private final LoyaltyProgramRepository loyaltyProgramRepository;

    /**
     * Instantiates a new Loyalty program service.
     *
     * @param loyaltyProgramRepository the loyalty program repository
     */
    public LoyaltyProgramService(LoyaltyProgramRepository loyaltyProgramRepository) {
        this.loyaltyProgramRepository = loyaltyProgramRepository;
    }

    /**
     * Get the loyalty program
     *
     * @return the loyalty program
     */
    public LoyaltyProgram getLoyaltyProgram() {
        return loyaltyProgramRepository.findLoyaltyProgramById(1);
    }

}
