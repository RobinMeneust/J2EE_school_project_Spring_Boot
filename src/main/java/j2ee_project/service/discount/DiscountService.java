package j2ee_project.service.discount;

import j2ee_project.dao.catalog.category.CategoryRepository;
import j2ee_project.dao.discount.DiscountRepository;
import j2ee_project.model.Discount;

import java.util.List;

public class DiscountService {
    private final DiscountRepository discountRepository;

    public DiscountService(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    public List<Discount> getDiscounts() {
        return discountRepository.findAll();
    }

    public Discount getDiscount(Long discountId) {
        return discountRepository.findDiscountById(discountId);
    }

    public void deleteDiscount(Long discountId) {
        discountRepository.deleteDiscountById(discountId);
    }

    public void addDiscount(Discount discount) {
        discountRepository.save(discount);
    }
}
