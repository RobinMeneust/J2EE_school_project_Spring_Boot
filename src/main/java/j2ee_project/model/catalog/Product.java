package j2ee_project.model.catalog;

import jakarta.persistence.*;

import java.util.Objects;

/**
 * Product
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Product {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "name", nullable = true, length = 30)
    private String name;
    @Basic
    @Column(name = "stockQuantity", nullable = false)
    private Integer stockQuantity;
    @Basic
    @Column(name = "unitPrice", nullable = false, precision = 2)
    private float unitPrice;
    @Basic
    @Column(name = "description", nullable = true, length = 300)
    private String description;
    @Basic
    @Column(name = "imagePath", nullable = true, length = 500)
    private String imagePath;
    @Basic
    @Column(name = "weight", nullable = true, precision = 0)
    private Float weight;
    @ManyToOne
    @JoinColumn(name = "idCategory", referencedColumnName = "id", nullable = false)
    private Category category;

    /**
     * Instantiates a new Product.
     */
    public Product() {
    }

    /**
     * Instantiates a new Product.
     *
     * @param name          the name
     * @param stockQuantity the stock quantity
     * @param unitPrice     the unit price
     * @param description   the description
     * @param imagePath     the image path
     * @param weight        the weight
     * @param category      the category
     */
    public Product(String name, int stockQuantity, float unitPrice, String description, String imagePath, Float weight, Category category) {
        this.name = name;
        this.stockQuantity = stockQuantity;
        this.unitPrice = unitPrice;
        this.description = description;
        this.imagePath = imagePath;
        this.weight = weight;
        this.category = category;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets stock quantity.
     *
     * @return the stock quantity
     */
    public Integer getStockQuantity() {
        return stockQuantity;
    }

    /**
     * Sets stock quantity.
     *
     * @param stockQuantity the stock quantity
     */
    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    /**
     * Gets unit price.
     *
     * @return the unit price
     */
    public float getUnitPrice() {
        return unitPrice;
    }

    /**
     * Sets unit price.
     *
     * @param unitPrice the unit price
     */
    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets image path.
     *
     * @return the image path
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     * Sets image path.
     *
     * @param imagePath the image path
     */
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    /**
     * Gets weight.
     *
     * @return the weight
     */
    public Float getWeight() {
        return weight;
    }

    /**
     * Sets weight.
     *
     * @param weight the weight
     */
    public void setWeight(Float weight) {
        this.weight = weight;
    }

    /**
     * Gets category.
     *
     * @return the category
     */
    public Category getCategory() {
        return category;
    }

    /**
     * Sets category.
     *
     * @param category the category
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id && Objects.equals(name, product.name) && Objects.equals(stockQuantity, product.stockQuantity) && Objects.equals(unitPrice, product.unitPrice) && Objects.equals(description, product.description) && Objects.equals(imagePath, product.imagePath) && Objects.equals(weight, product.weight) && Objects.equals(category, product.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, stockQuantity, unitPrice, description, imagePath, weight, category);
    }

    @Override
    public String toString() {
        return getName();
    }
}
