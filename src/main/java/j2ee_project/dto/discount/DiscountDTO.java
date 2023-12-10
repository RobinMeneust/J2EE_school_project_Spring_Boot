package j2ee_project.dto.discount;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.sql.Date;

/**
 * Discount class DTO (Data Transfer Object) that encapsulates the data and check if it's valid
 */
public class DiscountDTO {
    @NotBlank(message = "Name can not be blank.")
    @Size(max = 30, message = "Name can not exceed 30 characters.")
    @Pattern(regexp = "^[a-zA-ZÀ-ÖØ-öø-ÿ\\-' ]*$", message = "Name is not valid : only letters and -' are authorized.")
    private String name;
    @NotBlank(message = "Start date can not be blank.")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Start date is not valid.")
    private Date startDate;
    @NotBlank(message = "End date can not be blank.")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "End date is not valid.")
    private Date endDate;
    @NotBlank(message = "Discount percentage can not be blank.")
    @Pattern(regexp = "^\\d+(\\.[05])?$", message = "Discount percentage is not valid.")
    private int discountPercentage;

    /**
     * Instantiates a new Discount dto.
     *
     * @param name               the name
     * @param startDate          the start date
     * @param endDate            the end date
     * @param discountPercentage the discount percentage
     */
    public DiscountDTO(String name, Date startDate, Date endDate, int discountPercentage) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.discountPercentage = discountPercentage;
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
     * Gets start date.
     *
     * @return the start date
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Gets end date.
     *
     * @return the end date
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Gets discount percentage.
     *
     * @return the discount percentage
     */
    public int getDiscountPercentage() {
        return discountPercentage;
    }

    @Override
    public String toString() {
        return "DiscountDTO{" +
                "name=" + name +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", discountPercentage=" + discountPercentage +
                '}';
    }
}
