package j2ee_project.service.catalog.category;

import j2ee_project.repository.catalog.category.CategoryRepository;
import j2ee_project.model.catalog.Category;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * Get the list of all the categories
     *
     * @return List of categories
     */
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    /**
     * Get category from its id
     *
     * @param categoryId the category id
     * @return the category
     */
    public Category getCategory(int categoryId) {
        return categoryRepository.findCategoryById(categoryId);
    }

    /**
     * Delete category.
     *
     * @param categoryId the category id
     */
    public void deleteCategory(int categoryId) {
        categoryRepository.deleteById(categoryId);
    }

    /**
     * Add category.
     *
     * @param category the category
     */
    public void addCategory(Category category) {
        categoryRepository.save(category);
    }

    public void updateCategory(Category category) {
        categoryRepository.save(category);
    }
}

