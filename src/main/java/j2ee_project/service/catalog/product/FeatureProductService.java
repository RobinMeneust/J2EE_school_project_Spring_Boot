package j2ee_project.service.catalog.product;

import j2ee_project.repository.catalog.product.FeaturedProductRepository;
import j2ee_project.model.catalog.FeaturedProduct;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * The type Feature product service.
 */
@Service
@Transactional
public class FeatureProductService {

    private final FeaturedProductRepository featuredProductRepository;

    /**
     * Instantiates a new Feature product service.
     *
     * @param featuredProductRepository the featured product repository
     */
    public FeatureProductService(FeaturedProductRepository featuredProductRepository) {
        this.featuredProductRepository = featuredProductRepository;
    }

    /**
     * Get the list of all the featured products
     *
     * @return List of featured products
     */
    public List<FeaturedProduct> getFeaturedProducts() {
        return featuredProductRepository.findAll();
    }
}
