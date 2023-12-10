package j2ee_project.repository.catalog.product;

import j2ee_project.model.catalog.Category;
import j2ee_project.model.catalog.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Repository to interacts with the Product table in the DB
 *
 * @author Robin Meneust
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * Find products in the database according to its specifications
     *
     * @param productSpec the specifications
     * @param pageable information to know how page the result
     * @return a list of found products
     */
    List<Product> findAll(Specification<Product> productSpec, Pageable pageable);

    /**
     * Count products in the database according to its specifications
     *
     * @param productSpec the specifications
     * @return the number of corresponding products
     */
    int count(Specification<Product> productSpec);

    /**
     * Find a product according to its id
     *
     * @param id the id
     * @return the found product
     */
    Product findProductById(int id);

    /**
     * Search all products in the database with the category id
     *
     * @param category_id the category id
     * @return the found categories list
     */
    List<Product> findProductsByCategoryId(int category_id);

    /**
     * Delete a product according to its id
     *
     * @param id the id
     */
    void deleteProductById(int id);

}
