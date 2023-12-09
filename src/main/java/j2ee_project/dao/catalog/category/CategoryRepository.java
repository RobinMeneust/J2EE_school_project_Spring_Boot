package j2ee_project.dao.catalog.category;

import j2ee_project.model.catalog.Category;
import j2ee_project.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


/**
 * The interface Category repository to interacts with the Category table in the DB
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findCategoryBy(Long id);
    void deleteCategoryById(Long id);
}
