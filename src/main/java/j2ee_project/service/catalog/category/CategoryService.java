package j2ee_project.service.catalog.category;

import j2ee_project.repository.catalog.category.CategoryRepository;
import j2ee_project.model.catalog.Category;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategory(int categoryId) {
        return categoryRepository.findCategoryById(categoryId);
    }

    public void deleteCategory(int categoryId) {
        categoryRepository.deleteCategoryById(categoryId);
    }

    public void addCategory(Category category) {
        categoryRepository.save(category);
    }
}

