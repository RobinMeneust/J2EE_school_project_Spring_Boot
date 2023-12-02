package j2ee_project.model;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="`Discount`")
public class Discount {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "`id`", nullable = false)
    private int id;
    @Basic
    @Column(name = "`name`", nullable = true, length = 30)
    private String name;
    @Basic
    @Column(name = "`startDate`", nullable = false)
    private Date startDate;
    @Basic
    @Column(name = "`endDate`", nullable = false)
    private Date endDate;
    @Basic
    @Column(name = "`discountPercentage`", nullable = false)
    private int discountPercentage;

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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getDiscountPercentage() {
        return discountPercentage;
    }

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

    public boolean hasExpired() {
        Date today = new java.sql.Date(new java.util.Date().getTime());
        return getEndDate().compareTo(today) <= 0;
    }
}
