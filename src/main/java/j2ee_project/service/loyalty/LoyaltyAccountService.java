package j2ee_project.service.loyalty;

import j2ee_project.dao.discount.DiscountRepository;
import j2ee_project.dao.loyalty.LoyaltyAccountRepository;
import j2ee_project.model.Discount;
import j2ee_project.model.loyalty.LoyaltyAccount;

import java.util.Set;

public class LoyaltyAccountService {
    private final LoyaltyAccountRepository loyaltyAccountRepository;
    private final DiscountRepository discountRepository;

    public LoyaltyAccountService(LoyaltyAccountRepository loyaltyAccountRepository, DiscountRepository discountRepository) {
        this.loyaltyAccountRepository = loyaltyAccountRepository;
        this.discountRepository = discountRepository;
    }

    public void removeDiscount(LoyaltyAccount loyaltyAccount, Discount discount) {
        LoyaltyAccount loyaltyAccountDBObj = loyaltyAccountRepository.findLoyaltyAccountById(loyaltyAccount.getId());
        if(loyaltyAccountDBObj != null) {
            Set<Discount> discounts = loyaltyAccountDBObj.getAvailableDiscounts();
            discounts.remove(discount);
            discountRepository.delete(discount);
        }
    }
}
