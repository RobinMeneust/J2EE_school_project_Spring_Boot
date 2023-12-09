package j2ee_project.dao.discount;

import j2ee_project.model.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository to interacts with the Discount table in the DB
 *
 * @author Robin Meneust
 */
public interface DiscountRepository extends JpaRepository<Discount,Long> {
    public Discount findDiscountById(int discountId);
    public void deleteDiscountById(int discountId);
}
