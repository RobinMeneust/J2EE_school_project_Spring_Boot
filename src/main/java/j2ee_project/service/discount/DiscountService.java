package j2ee_project.service.discount;

import j2ee_project.repository.discount.DiscountRepository;
import j2ee_project.model.Discount;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscountService {
    private final DiscountRepository discountRepository;

    public DiscountService(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    public List<Discount> getDiscounts() {
        return discountRepository.findAll();
    }

    public Discount getDiscount(int discountId) {
        return discountRepository.findDiscountById(discountId);
    }

    public void deleteDiscount(int discountId) {
        discountRepository.deleteDiscountById(discountId);
    }

    public void addDiscount(Discount discount) {
        discountRepository.save(discount);
    }
}
