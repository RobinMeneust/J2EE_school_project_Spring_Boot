package j2ee_project.service.catalog.product;

import j2ee_project.repository.catalog.product.ProductRepository;
import j2ee_project.model.catalog.Product;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Get products list
     *
     * @return the list of products
     */
    public List<Product> getProducts() {
        return this.productRepository.findAll();
    }

    /**
     * Get a list of products that match the given filters
     *
     * @param name     The products' name must match with this name (it's case-insensitive, and it can be just a part of a searched word: e.g. 'Ch' will return 'chess' products)
     * @param category The products' category must match with it (exactly like the name filter)
     * @param minPrice The products' price must be greater or equal to this value
     * @param maxPrice The products' price must be lesser or equal to this value
     * @param pageNum    Index of the first element (e.g. begin=4 will ignore the 3 first products)
     * @param pageSize     Max number of elements returned
     * @return List of products that match with all of those filters
     */
    public List<Product> getProducts(String name, String category, Float minPrice, Float maxPrice, int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return productRepository.findAll(buildSpecification(name, category, minPrice, maxPrice), pageable);
    }

    /**
     * Get the total number of products
     *
     * @param name     The products' name must match with this name (it's case-insensitive, and it can be just a part of a searched word: e.g. 'Ch' will return 'chess' products)
     * @param category The products' category must match with it (exactly like the name filter)
     * @param minPrice The products' price must be greater or equal to this value
     * @param maxPrice The products' price must be lesser or equal to this value
     * @return Number of products
     */
    public int getSize(String name, String category, Float minPrice, Float maxPrice) {
        return productRepository.count( buildSpecification(name, category, minPrice, maxPrice));
    }

    /**
     * Delete product whose ID is given
     *
     * @param productId the product id
     */
    public void deleteProduct(int productId){
        productRepository.deleteProductById(productId);
    }

    /**
     * Set the image path of the product whose id is given
     *
     * @param productId the product id
     * @param path      the path
     */
    public void setProductImagePath(int productId, String path) {
        if(path==null || path.trim().isEmpty()) {
            return;
        }
        Product product = this.productRepository.findProductById(productId);
        product.setImagePath(path);
        productRepository.save(product);
    }

    /**
     * Add product
     *
     * @param product the product added
     */
    public void addProduct(Product product){
        productRepository.save(product);
    }

    /**
     * Get a product from its ID
     *
     * @param productId ID of the searched product
     * @return Product whose ID matched with the one provided
     */
    public Product getProduct(int productId) {
        return productRepository.findProductById(productId);
    }



    private Specification<Product> buildSpecification(String name, String category, Float minPrice, Float maxPrice) {
        return Specification.where(likeName(name)).and(likeCategory(category)).and(betweenPrice(minPrice,maxPrice));
    }

    private Specification<Product> likeName(String name) {
        return (root, query, criteriaBuilder) ->
                name != null ? criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%") : null;
    }

    private Specification<Product> likeCategory(String category) {
        return (root, query, criteriaBuilder) ->
                category != null ? criteriaBuilder.like(criteriaBuilder.lower(root.get("category").get("name")), "%" + category.toLowerCase() + "%") : null;
    }

    private Specification<Product> betweenPrice(Float minPrice, Float maxPrice) {
        return (root, query, criteriaBuilder) ->
                (minPrice != null && maxPrice != null) ?
                        criteriaBuilder.between(root.get("unitPrice"), minPrice, maxPrice) :
                        minPrice != null ? criteriaBuilder.greaterThanOrEqualTo(root.get("unitPrice"), minPrice)  :
                                maxPrice != null ? criteriaBuilder.lessThanOrEqualTo(root.get("unitPrice"), maxPrice) : null;
    }
}
