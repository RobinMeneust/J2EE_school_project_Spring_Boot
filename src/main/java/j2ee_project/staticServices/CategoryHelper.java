package j2ee_project.staticServices;

import j2ee_project.Application;
import j2ee_project.model.catalog.Category;
import j2ee_project.model.catalog.FeaturedProduct;
import j2ee_project.service.catalog.category.CategoryService;
import j2ee_project.service.catalog.product.FeatureProductService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class CategoryHelper {

    private static final CategoryService categoryService;

    static {
        ApplicationContext context = new Application().getContext();
        categoryService = context.getBean(CategoryService.class);
    }

    public static List<Category> getCategories() {
        return categoryService.getCategories();
    }
}