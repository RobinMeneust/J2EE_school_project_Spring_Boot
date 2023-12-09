package j2ee_project.staticServices;

import j2ee_project.Application;
import j2ee_project.model.catalog.FeaturedProduct;
import j2ee_project.service.catalog.product.FeatureProductService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class FeatureProductHelper {

    private static final FeatureProductService featureProductService;

    static {
        ApplicationContext context = Application.getContext();
        featureProductService = context.getBean(FeatureProductService.class);
    }

    public static List<FeaturedProduct> getFeaturedProducts() {
        return featureProductService.getFeaturedProducts();
    }
}