package j2ee_project.service.catalog.product;

import j2ee_project.repository.catalog.product.ProductRepository;
import j2ee_project.model.catalog.Product;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProducts() {
        return this.productRepository.findAll();
    }

    public List<Product> getProducts(String name, String category, Float minPrice, Float maxPrice, int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return productRepository.findAll(buildSpecification(name, category, minPrice, maxPrice), pageable);
    }

    public int getSize(String name, String category, Float minPrice, Float maxPrice) {
        return productRepository.count( buildSpecification(name, category, minPrice, maxPrice));
    }

    public void deleteProduct(int productId){
        productRepository.deleteProductById(productId);
    }

    public void setProductImagePath(int productId, String path) {
        if(path==null || path.trim().isEmpty()) {
            return;
        }
        Product product = this.productRepository.findProductById(productId);
        product.setImagePath(path);
        productRepository.save(product);
    }


    public void addProduct(Product product){
        productRepository.save(product);
    }

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
                category != null ? criteriaBuilder.like(criteriaBuilder.lower(root.get("category")), "%" + category.toLowerCase() + "%") : null;
    }

    private Specification<Product> betweenPrice(Float minPrice, Float maxPrice) {
        return (root, query, criteriaBuilder) ->
                (minPrice != null && maxPrice != null) ?
                        criteriaBuilder.between(root.get("price"), minPrice, maxPrice) :
                        minPrice != null ? criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice)  :
                                maxPrice != null ? criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice) : null;
    }
}
