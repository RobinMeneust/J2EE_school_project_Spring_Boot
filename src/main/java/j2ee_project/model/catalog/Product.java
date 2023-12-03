package j2ee_project.model.catalog;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name="`Product`")
@Inheritance(strategy = InheritanceType.JOINED)
public class Product {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "`id`", nullable = false)
    private int id;
    @Basic
    @Column(name = "`name`", nullable = true, length = 30)
    private String name;
    @Basic
    @Column(name = "`stockQuantity`", nullable = false)
    private Integer stockQuantity;
    @Basic
    @Column(name = "`unitPrice`", nullable = false, precision = 2)
    private float unitPrice;
    @Basic
    @Column(name = "`description`", nullable = true, length = 1000)
    private String description;
    @Basic
    @Column(name = "`imagePath`", nullable = true, length = 500)
    private String imagePath;
    @Basic
    @Column(name = "`weight`", nullable = true, precision = 0)
    private Float weight;
    @ManyToOne
    @JoinColumn(name = "`idCategory`", referencedColumnName = "`id`", nullable = false)
    private Category category;

    public Product() {
    }

    public Product(String name, int stockQuantity, float unitPrice, String description, String imagePath, Float weight, Category category) {
        this.name = name;
        this.stockQuantity = stockQuantity;
        this.unitPrice = unitPrice;
        this.description = description;
        this.imagePath = imagePath;
        this.weight = weight;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Category getCategory() {
        return category;
    }

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
