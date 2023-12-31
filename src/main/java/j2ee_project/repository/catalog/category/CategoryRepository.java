package j2ee_project.repository.catalog.category;

import j2ee_project.model.catalog.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Repository to interacts with the Category table in the DB
 *
 * @author Robin Meneust
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    /**
     * Search a category in the database according to its id
     *
     * @param id the id
     * @return the found category
     */
    Category findCategoryById(int id);


    /**
     * Search all categories in the database with the discount id
     *
     * @param discount_id the discount id
     * @return the found categories list
     */
    List<Category> findCategoriesByDiscountId(int discount_id);

    /**
     * Delete a category in the database according to its id
     *
     * @param id the id
     */
    void deleteCategoryById(int id);
}
