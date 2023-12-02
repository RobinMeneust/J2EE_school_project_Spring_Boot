package j2ee_project.model.loyalty;

import j2ee_project.model.Discount;
import jakarta.persistence.*;

@Entity
@Table(name="`LoyaltyLevel`")
public class LoyaltyLevel implements Comparable<LoyaltyLevel> {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "`id`", nullable = false)
    private int id;
    @Basic
    @Column(name = "`requiredPoints`", nullable = false)
    private int requiredPoints;
    @ManyToOne
    @JoinColumn(name = "`idDiscount`", referencedColumnName = "`id`", nullable = false)
    private Discount discount;
    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "`idLoyaltyProgram`", referencedColumnName = "`id`", nullable = false)
    private LoyaltyProgram loyaltyProgram;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRequiredPoints() {
        return requiredPoints;
    }

    public void setRequiredPoints(int requiredPoints) {
        this.requiredPoints = requiredPoints;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LoyaltyLevel that = (LoyaltyLevel) o;

        if (id != that.id) return false;
        if (requiredPoints != that.requiredPoints) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + requiredPoints;
        return result;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "LoyaltyLevel{" +
                "id=" + id +
                ", requiredPoints=" + requiredPoints +
                ", discount=" + discount +
                ", loyaltyProgram=" + loyaltyProgram +
                '}';
    }

    @Override
    public int compareTo(LoyaltyLevel o) {
        return Integer.compare(getRequiredPoints(), o.getRequiredPoints());
    }
}
