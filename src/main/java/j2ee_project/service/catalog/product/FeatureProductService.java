package j2ee_project.service.catalog.product;

import j2ee_project.repository.catalog.product.FeaturedProductRepository;
import j2ee_project.model.catalog.FeaturedProduct;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class FeatureProductService {

    private final FeaturedProductRepository featuredProductRepository;

    public FeatureProductService(FeaturedProductRepository featuredProductRepository) {
        this.featuredProductRepository = featuredProductRepository;
    }

    public List<FeaturedProduct> getFeaturedProducts() {
        return featuredProductRepository.findAll();
    }
}
