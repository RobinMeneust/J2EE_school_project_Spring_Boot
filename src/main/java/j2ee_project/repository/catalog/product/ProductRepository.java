package j2ee_project.repository.catalog.product;

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
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findAll(Specification<Product> productSpec, Pageable pageable);
    int count(Specification<Product> productSpec);
    Product findProductById(int id);

}
