package j2ee_project.repository.catalog.product;

import j2ee_project.model.catalog.FeaturedProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository to interacts with the FeaturedProduct table in the DB
 *
 * @author Robin Meneust
 */
@Repository
public interface FeaturedProductRepository extends JpaRepository<FeaturedProduct, Long> {

}
