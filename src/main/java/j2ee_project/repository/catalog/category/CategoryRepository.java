package j2ee_project.repository.catalog.category;

import j2ee_project.model.catalog.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Repository to interacts with the Category table in the DB
 *
 * @author Robin Meneust
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findCategoryById(int id);
    void deleteCategoryById(int id);
}
