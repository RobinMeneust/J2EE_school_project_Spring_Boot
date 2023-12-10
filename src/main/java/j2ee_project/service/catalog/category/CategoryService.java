package j2ee_project.service.catalog.category;

import j2ee_project.model.catalog.Product;
import j2ee_project.repository.catalog.category.CategoryRepository;
import j2ee_project.model.catalog.Category;
import j2ee_project.repository.catalog.product.ProductRepository;
import j2ee_project.service.catalog.product.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * The type Category service.
 */
@Service
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    /**
     * Instantiates a new Category service.
     *
     * @param categoryRepository the category repository
     * @param productRepository  the product repository
     */
    public CategoryService(CategoryRepository categoryRepository,  ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
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

        List<Product> productsWithDiscount = productRepository.findProductsByCategoryId(categoryId);
        for (Product product: productsWithDiscount) {
            product.setCategory(categoryRepository.findCategoryById(1));
            productRepository.save(product);
        }

        categoryRepository.deleteById((long) categoryId);
    }

    /**
     * Add category.
     *
     * @param category the category
     */
    public void addCategory(Category category) {
        categoryRepository.save(category);
    }

    /**
     * Update a category in the database.
     *
     * @param category the category
     */
    public void updateCategory(Category category) {
        categoryRepository.save(category);
    }

}

