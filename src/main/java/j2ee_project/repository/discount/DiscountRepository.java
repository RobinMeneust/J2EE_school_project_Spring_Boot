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

    /**
     * Find a discount in the database according to its id
     *
     * @param discountId the id
     * @return the found discount
     */
    Discount findDiscountById(int discountId);

    /**
     * Delete a discount in the database according to its id
     *
     * @param discountId the id
     */
    void deleteDiscountById(int discountId);
}
