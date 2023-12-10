package j2ee_project.service.catalog.category;

import j2ee_project.repository.catalog.category.CategoryRepository;
import j2ee_project.model.catalog.Category;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    public Optional<Category> getCategory(int categoryId) {
        return categoryRepository.findById(categoryId);
    }

    public void deleteCategory(int categoryId) {
        categoryRepository.deleteById(categoryId);
    }

    public void addCategory(Category category) {
        categoryRepository.save(category);
    }

    public void updateCategory(Category category) {
        categoryRepository.save(category);
    }
}

