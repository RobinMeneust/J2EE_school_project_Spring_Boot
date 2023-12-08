package j2ee_project.model;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Discount (reduce the price of one or many products at once)
 */
@Entity
public class Discount {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "name", nullable = true, length = 30)
    private String name;
    @Basic
    @Column(name = "startDate", nullable = false)
    private Date startDate;
    @Basic
    @Column(name = "endDate", nullable = false)
    private Date endDate;
    @Basic
    @Column(name = "discountPercentage", nullable = false)
    private int discountPercentage;

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
     * Gets start date.
     *
     * @return the start date
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Sets start date.
     *
     * @param startDate the start date
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
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
     * Sets end date.
     *
     * @param endDate the end date
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * Gets discount percentage.
     *
     * @return the discount percentage
     */
    public int getDiscountPercentage() {
        return discountPercentage;
    }

    /**
     * Sets discount percentage.
     *
     * @param discountPercentage the discount percentage
     */
    public void setDiscountPercentage(int discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Discount discount = (Discount) o;

        if (id != discount.id) return false;
        if (discountPercentage != discount.discountPercentage) return false;
        if (name != null ? !name.equals(discount.name) : discount.name != null) return false;
        if (startDate != null ? !startDate.equals(discount.startDate) : discount.startDate != null) return false;
        if (endDate != null ? !endDate.equals(discount.endDate) : discount.endDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + discountPercentage;
        return result;
    }

    /**
     * Has expired boolean.
     *
     * @return the boolean
     */
    public boolean hasExpired() {
        Date today = new java.sql.Date(new java.util.Date().getTime());
        return getEndDate().compareTo(today) <= 0;
    }
}
