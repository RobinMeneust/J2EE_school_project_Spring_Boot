package j2ee_project.dto.catalog;

import j2ee_project.model.Discount;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Category class DTO (Data Transfer Object) that encapsulates the data and check if it's valid
 */
public class CategoryDTO {
    @NotBlank(message = "Name can not be blank.")
    @Size(max = 30, message = "Name can not exceed 30 characters.")
    @Pattern(regexp = "^[a-zA-ZÀ-ÖØ-öø-ÿ\\-']*$", message = "Name is not valid : only letters and -' are authorized.")
    private String name;
    @NotBlank(message = "Description can not be blank.")
    @Size(max = 300, message = "Description can not exceed 300 characters.")
    private String description;
    private Discount discount;

    /**
     * Instantiates a new Category dto.
     *
     * @param name        the name of the category
     * @param description the description of the category
     * @param discount    the discount of the category
     */
    public CategoryDTO(String name, String description, Discount discount) {
        this.name = name;
        this.description = description;
        this.discount = discount;
    }

    /**
     * Get name
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Get description
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get discount
     *
     * @return the discount
     */
    public Discount getDiscount() {
        return discount;
    }

    @Override
    public String toString() {
        return "CategoryDTO{" +
                "name=" + name +
                ", description=" + description +
                ", discount=" + discount +
                '}';
    }
}
