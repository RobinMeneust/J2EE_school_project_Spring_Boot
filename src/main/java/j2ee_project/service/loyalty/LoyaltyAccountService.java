package j2ee_project.service.loyalty;

import j2ee_project.repository.discount.DiscountRepository;
import j2ee_project.repository.loyalty.LoyaltyAccountRepository;
import j2ee_project.repository.loyalty.LoyaltyLevelRepository;
import j2ee_project.model.Discount;
import j2ee_project.model.loyalty.LoyaltyAccount;
import j2ee_project.model.loyalty.LoyaltyLevel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional
public class LoyaltyAccountService {
    private final LoyaltyAccountRepository loyaltyAccountRepository;
    private final DiscountRepository discountRepository;
    private final LoyaltyLevelRepository loyaltyLevelRepository;

    public LoyaltyAccountService(LoyaltyAccountRepository loyaltyAccountRepository, DiscountRepository discountRepository, LoyaltyLevelRepository loyaltyLevelRepository) {
        this.loyaltyAccountRepository = loyaltyAccountRepository;
        this.discountRepository = discountRepository;
        this.loyaltyLevelRepository = loyaltyLevelRepository;
    }

    public void removeDiscount(LoyaltyAccount loyaltyAccount, Discount discount) {
        LoyaltyAccount loyaltyAccountDBObj = loyaltyAccountRepository.findLoyaltyAccountById(loyaltyAccount.getId());
        if(loyaltyAccountDBObj != null) {
            Set<Discount> discounts = loyaltyAccountDBObj.getAvailableDiscounts();
            discounts.remove(discount);
            discountRepository.delete(discount);
        }
    }

    public LoyaltyAccount getLoyaltyAccount(int loyaltyAccountId) {
        return loyaltyAccountRepository.findLoyaltyAccountById(loyaltyAccountId);
    }

    public void createLevelUsed(int idLoyaltyAccount, int idLoyaltyLevel) {
        LoyaltyAccount loyaltyAccount = loyaltyAccountRepository.findLoyaltyAccountById(idLoyaltyAccount);
        LoyaltyLevel loyaltyLevel = loyaltyLevelRepository.findLoyaltyLevelById(idLoyaltyLevel);
        loyaltyAccount.addLoyaltyLevelUsed(loyaltyLevel);
        loyaltyAccountRepository.save(loyaltyAccount);
    }

    /**
     * Add points to the given loyalty account
     *
     * @param loyaltyAccount the loyalty account
     * @param nbPointsAdded  the nb points added
     */
    public void addPoints(LoyaltyAccount loyaltyAccount, int nbPointsAdded) {
        LoyaltyAccount loyaltyAccountDBObj = loyaltyAccountRepository.findLoyaltyAccountById(loyaltyAccount.getId());
        if(loyaltyAccountDBObj != null && nbPointsAdded>0) {
            loyaltyAccountDBObj.addLoyaltyPoints(nbPointsAdded);
            loyaltyAccountRepository.save(loyaltyAccountDBObj);
        }
    }
}
