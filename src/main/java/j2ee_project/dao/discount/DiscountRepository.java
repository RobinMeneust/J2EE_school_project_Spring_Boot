package j2ee_project.dao.discount;

import j2ee_project.model.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountRepository extends JpaRepository<Discount,Long> {
    public Discount findDiscountById(Long discountId);
    public void deleteDiscountById(Long discountId);
}
