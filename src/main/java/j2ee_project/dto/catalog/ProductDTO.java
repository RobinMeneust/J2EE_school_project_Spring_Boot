package j2ee_project.dto.catalog;

import j2ee_project.model.catalog.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Product class DTO (Data Transfer Object) that encapsulates the data and check if it's valid
 */
public class ProductDTO {
    @NotBlank(message = "Name can not be blank.")
    @Size(max = 30, message = "Name can not exceed 30 characters.")
    @Pattern(regexp = "^[a-zA-ZÀ-ÖØ-öø-ÿ\\-']*$", message = "Name is not valid : only letters and -' are authorized.")
    private String name;
    @NotBlank(message = "Stock quantity can not be blank.")
    @Pattern(regexp = "^[0-9]*$", message = "Stock quantity is not valid.")
    private Integer stockQuantity;
    @NotBlank(message = "Unit price can not be blank.")
    @Pattern(regexp = "^\\d+(\\.\\d{1,2})?$", message = "Unit price is not valid.")
    private float unitPrice;
    @Size(max = 300, message = "Description can not exceed 300 characters.")
    private String description;
    @Size(max = 500, message = "Image path can not exceed 500 characters.")
    private String imagePath;
    @Pattern(regexp = "^\\d+(\\.\\d*)?$", message = "Weight is not valid.")
    private Float weight;
    @NotBlank(message = "Category can not be blank.")
    private Category category;


    /**
     * Instantiates a new Product dto.
     *
     * @param name          the name
     * @param stockQuantity the stock quantity
     * @param unitPrice     the unit price
     * @param description   the description
     * @param imagePath     the image path
     * @param weight        the weight
     * @param category      the category
     */
    public ProductDTO(String name, Integer stockQuantity, float unitPrice, String description, String imagePath, Float weight, Category category) {
        this.name = name;
        this.stockQuantity = stockQuantity;
        this.unitPrice = unitPrice;
        this.description = description;
        this.imagePath = imagePath;
        this.weight = weight;
        this.category = category;
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
     * Gets stock quantity.
     *
     * @return the stock quantity
     */
    public Integer getStockQuantity() {
        return stockQuantity;
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
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
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
     * Gets weight.
     *
     * @return the weight
     */
    public Float getWeight() {
        return weight;
    }

    /**
     * Gets category.
     *
     * @return the category
     */
    public Category getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
                "name=" + name +
                ", stockQuantity=" + stockQuantity +
                ", unitPrice=" + unitPrice +
                ", description=" + description +
                ", imagePath=" + imagePath +
                ", weight=" + weight +
                ", category=" + category +
                '}';
    }
}
