package j2ee_project.service.catalog.product;

import j2ee_project.dao.catalog.product.FeaturedProductRepository;
import j2ee_project.dao.catalog.product.ProductRepository;
import j2ee_project.model.catalog.FeaturedProduct;
import j2ee_project.model.catalog.Product;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class FeatureProductService {

    private final FeaturedProductRepository featuredProductRepository;

    public FeatureProductService(FeaturedProductRepository featuredProductRepository) {
        this.featuredProductRepository = featuredProductRepository;
    }

    public List<FeaturedProduct> getFeaturedProducts() {
        return featuredProductRepository.findAll();
    }
}
