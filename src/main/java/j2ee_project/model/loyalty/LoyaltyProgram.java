package j2ee_project.model.loyalty;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name="`LoyaltyProgram`")
public class LoyaltyProgram {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "`id`", nullable = false)
    private int id;
    @Basic
    @Column(name = "`durationNbDays`", nullable = false)
    private int durationNbDays;
    @OneToMany(fetch = FetchType.EAGER, targetEntity = LoyaltyLevel.class, mappedBy = "loyaltyProgram")
    private Set<LoyaltyLevel> loyaltyLevels;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDurationNbDays() {
        return durationNbDays;
    }

    public void setDurationNbDays(int durationNbDays) {
        this.durationNbDays = durationNbDays;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LoyaltyProgram that = (LoyaltyProgram) o;

        if (id != that.id) return false;
        if (durationNbDays != that.durationNbDays) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + durationNbDays;
        return result;
    }

    public Set<LoyaltyLevel> getLoyaltyLevels() {
        return loyaltyLevels;
    }

    public void setLoyaltyLevels(Set<LoyaltyLevel> loyaltyLevels) {
        this.loyaltyLevels = loyaltyLevels;
    }
}
