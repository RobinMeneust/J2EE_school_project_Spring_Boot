package j2ee_project.service.discount;

import j2ee_project.model.catalog.Category;
import j2ee_project.repository.catalog.category.CategoryRepository;
import j2ee_project.repository.discount.DiscountRepository;
import j2ee_project.model.Discount;
import j2ee_project.service.catalog.category.CategoryService;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DiscountService {
    private final DiscountRepository discountRepository;
    private final CategoryRepository categoryRepository;

    public DiscountService(DiscountRepository discountRepository, CategoryRepository categoryRepository) {
        this.discountRepository = discountRepository;
        this.categoryRepository = categoryRepository;
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

        List<Category> categoriesWithDiscount = categoryRepository.findCategoriesByDiscountId(discountId);
        for (Category category: categoriesWithDiscount) {
            category.setDiscount(null);
            categoryRepository.save(category);
        }
        discountRepository.deleteById((long) discountId);
    }

    /**
     * Add discount.
     *
     * @param discount the discount added
     */
    public void addDiscount(Discount discount) {
        discountRepository.save(discount);
    }

    public void updateDiscount(Discount discount) {
        discountRepository.save(discount);
    }
}
