package j2ee_project.service.discount;

import j2ee_project.repository.discount.DiscountRepository;
import j2ee_project.model.Discount;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DiscountService {
    private final DiscountRepository discountRepository;

    public DiscountService(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    /**
     * Get discounts list.
     *
     * @return the list
     */
    public List<Discount> getDiscounts() {
        return discountRepository.findAll();
    }

    /**
     * Get discount by id.
     *
     * @param discountId the discount id
     * @return the discount
     */
    public Discount getDiscount(int discountId) {
        return discountRepository.findDiscountById(discountId);
    }

    /**
     * Delete discount by id.
     *
     * @param discountId the discount id
     */
    public void deleteDiscount(int discountId) {
        discountRepository.deleteDiscountById(discountId);
    }

    /**
     * Add discount.
     *
     * @param discount the discount added
     */
    public void addDiscount(Discount discount) {
        discountRepository.save(discount);
    }
}
