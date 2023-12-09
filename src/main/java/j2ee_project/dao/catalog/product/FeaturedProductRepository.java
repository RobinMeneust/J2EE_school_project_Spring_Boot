package j2ee_project.dao.catalog.product;

import j2ee_project.model.catalog.FeaturedProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeaturedProductRepository extends JpaRepository<FeaturedProduct, Long> {

}
