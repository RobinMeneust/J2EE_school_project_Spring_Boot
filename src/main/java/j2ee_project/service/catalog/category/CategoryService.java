package j2ee_project.service.catalog.category;

import j2ee_project.dao.catalog.category.CategoryRepository;
import j2ee_project.dao.catalog.product.FeaturedProductRepository;
import j2ee_project.model.catalog.Category;
import j2ee_project.model.catalog.FeaturedProduct;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategory(int categoryId) {
        return categoryRepository.findCategoryBy(categoryId);
    }

    public void deleteCategory(int categoryId) {
        categoryRepository.deleteCategoryById(categoryId);
    }

    public void addCategory(Category category) {
        categoryRepository.save(category);
    }
}

