package j2ee_project.dao.catalog.product;

import j2ee_project.model.catalog.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


/**
 * Repository to interacts with the Product table in the DB
 */
public interface ProductRepository extends JpaRepository<Category, Long> {
    Optional<Category> findCategoryBy(Long id);
    Optional<Category> deleteCategoryById(Long id);

}
