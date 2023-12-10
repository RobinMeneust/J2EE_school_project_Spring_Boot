package j2ee_project.service.loyalty;

import j2ee_project.repository.loyalty.LoyaltyLevelRepository;
import j2ee_project.model.loyalty.LoyaltyLevel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * The type Loyalty level service.
 */
@Service
@Transactional
public class LoyaltyLevelService {
    private final LoyaltyLevelRepository loyaltyLevelRepository;

    /**
     * Instantiates a new Loyalty level service.
     *
     * @param loyaltyLevelRepository the loyalty level repository
     */
    public LoyaltyLevelService(LoyaltyLevelRepository loyaltyLevelRepository) {
        this.loyaltyLevelRepository = loyaltyLevelRepository;
    }

    /**
     * Get loyalty level by id
     *
     * @param id the id of the loyalty level fetched
     * @return the loyalty level fetched
     */
    public LoyaltyLevel getLoyaltyLevel(int id) {
        return loyaltyLevelRepository.findLoyaltyLevelById(id);
    }

    /**
     * Get all loyalty levels
     *
     * @return the list of loyalty level
     */
    public List<LoyaltyLevel> getLoyaltyLevels() {
        return loyaltyLevelRepository.findAll();
    }
}
