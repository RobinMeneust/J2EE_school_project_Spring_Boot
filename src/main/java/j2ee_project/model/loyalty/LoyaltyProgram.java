package j2ee_project.model.loyalty;

import jakarta.persistence.*;

import java.util.Set;

/**
 * Loyalty program that contains a list of loyalty levels
 */
@Entity
public class LoyaltyProgram {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "durationNbDays", nullable = false)
    private int durationNbDays;
    @OneToMany(fetch = FetchType.EAGER, targetEntity = LoyaltyLevel.class, mappedBy = "loyaltyProgram")
    private Set<LoyaltyLevel> loyaltyLevels;

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
     * Gets programs duration (number of days)
     *
     * @return the duration nb days
     */
    public int getDurationNbDays() {
        return durationNbDays;
    }

    /**
     * Set programs duration (number of days)
     *
     * @param durationNbDays the duration nb days
     */
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

    /**
     * Gets all the loyalty levels.
     *
     * @return the loyalty levels
     */
    public Set<LoyaltyLevel> getLoyaltyLevels() {
        return loyaltyLevels;
    }

    /**
     * Sets all the loyalty levels.
     *
     * @param loyaltyLevels the loyalty levels
     */
    public void setLoyaltyLevels(Set<LoyaltyLevel> loyaltyLevels) {
        this.loyaltyLevels = loyaltyLevels;
    }
}
