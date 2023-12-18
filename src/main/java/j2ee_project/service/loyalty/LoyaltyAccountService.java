package j2ee_project.service.loyalty;

import j2ee_project.dto.discount.DiscountDTO;
import j2ee_project.repository.discount.DiscountRepository;
import j2ee_project.repository.loyalty.LoyaltyAccountRepository;
import j2ee_project.repository.loyalty.LoyaltyLevelRepository;
import j2ee_project.model.Discount;
import j2ee_project.model.loyalty.LoyaltyAccount;
import j2ee_project.model.loyalty.LoyaltyLevel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.Calendar;
import java.util.Set;

/**
 * Service to manage loyalty accounts
 */
@Service
@Transactional
public class LoyaltyAccountService {
    private final LoyaltyAccountRepository loyaltyAccountRepository;
    private final DiscountRepository discountRepository;
    private final LoyaltyLevelRepository loyaltyLevelRepository;


    /**
     * Instantiates a new Loyalty account service.
     *
     * @param loyaltyAccountRepository the loyalty account repository
     * @param discountRepository       the discount repository
     * @param loyaltyLevelRepository   the loyalty level repository
     */
    public LoyaltyAccountService(LoyaltyAccountRepository loyaltyAccountRepository, DiscountRepository discountRepository, LoyaltyLevelRepository loyaltyLevelRepository) {
        this.loyaltyAccountRepository = loyaltyAccountRepository;
        this.discountRepository = discountRepository;
        this.loyaltyLevelRepository = loyaltyLevelRepository;
    }

    /**
     * Remove the given discount from the given loyalty account
     *
     * @param loyaltyAccount the loyalty account from which the discount is deleted
     * @param discount       the discount deleted
     */
    public void removeDiscount(LoyaltyAccount loyaltyAccount, Discount discount) {
        LoyaltyAccount loyaltyAccountDBObj = loyaltyAccountRepository.findLoyaltyAccountById(loyaltyAccount.getId());
        if(loyaltyAccountDBObj != null) {
            Set<Discount> discounts = loyaltyAccountDBObj.getAvailableDiscounts();
            discounts.remove(discount);
        }
    }

    /**
     * Get loyalty account from its id
     *
     * @param loyaltyAccountId the loyalty account id
     * @return the loyalty account
     */
    public LoyaltyAccount getLoyaltyAccount(int loyaltyAccountId) {
        return loyaltyAccountRepository.findLoyaltyAccountById(loyaltyAccountId);
    }

    /**
     * Define a loyally level used for a loyalty account
     *
     * @param idLoyaltyAccount the loyalty account id
     * @param idLoyaltyLevel   the loyalty level id
     */
    public void createLevelUsed(int idLoyaltyAccount, int idLoyaltyLevel) {
        LoyaltyAccount loyaltyAccount = loyaltyAccountRepository.findLoyaltyAccountById(idLoyaltyAccount);
        LoyaltyLevel loyaltyLevel = loyaltyLevelRepository.findLoyaltyLevelById(idLoyaltyLevel);
        loyaltyAccount.addLoyaltyLevelUsed(loyaltyLevel);

        Date startDate = new java.sql.Date(new java.util.Date().getTime());
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        c.add(Calendar.DATE, 30);
        Date endDate = new Date(c.getTimeInMillis());

        Discount newDiscount = new Discount(new DiscountDTO(loyaltyLevel.getDiscount().getName(),startDate,endDate,loyaltyLevel.getDiscount().getDiscountPercentage()));
        discountRepository.save(newDiscount);
        loyaltyAccount.getAvailableDiscounts().add(newDiscount);

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
