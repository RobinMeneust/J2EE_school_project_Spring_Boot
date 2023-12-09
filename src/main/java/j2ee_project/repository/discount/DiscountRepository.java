package j2ee_project.repository.discount;

import j2ee_project.model.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository to interacts with the Discount table in the DB
 *
 * @author Robin Meneust
 */
@Repository
public interface DiscountRepository extends JpaRepository<Discount,Long> {
    public Discount findDiscountById(int discountId);
    public void deleteDiscountById(int discountId);
}
