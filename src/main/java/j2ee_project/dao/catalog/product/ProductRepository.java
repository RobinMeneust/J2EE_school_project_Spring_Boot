package j2ee_project.dao.catalog.product;

import j2ee_project.model.catalog.Category;
import j2ee_project.model.catalog.Product;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


/**
 * Repository to interacts with the Product table in the DB
 *
 * @author Robin Meneust
 */
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAll(Specification<Product> productSpec, Pageable pageable);
    int count(Specification<Product> productSpec);
    Product findProductById(int id);
    void deleteProductById(int id);

}
