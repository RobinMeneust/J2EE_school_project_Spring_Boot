package j2ee_project.model.loyalty;

import j2ee_project.model.Discount;
import jakarta.persistence.*;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Loyalty account associated to a loyalty program with different loyalty levels with rewards
 */
@Entity
public class LoyaltyAccount {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "loyaltyPoints", nullable = false)
    private int loyaltyPoints;
    @Basic
    @Column(name = "endDate", nullable = false)
    private Date endDate;
    @ManyToOne
    @JoinColumn(name = "idLoyaltyProgram", referencedColumnName = "id", nullable = false)
    private LoyaltyProgram loyaltyProgram;

    @ManyToMany (fetch = FetchType.EAGER)
    @JoinTable(name = "LoyaltyAccountLevelUsed",
            joinColumns = @JoinColumn(name = "idLoyaltyAccount"),
            inverseJoinColumns = @JoinColumn(name = "idLoyaltyLevel")
    )
    private Set<LoyaltyLevel> loyaltyLevelsUsed = new HashSet<>();

    @ManyToMany (fetch = FetchType.EAGER)
    @JoinTable(name = "LoyaltyAccountDiscounts",
            joinColumns = @JoinColumn(name = "idLoyaltyAccount"),
            inverseJoinColumns = @JoinColumn(name = "idDiscount")
    )
    private Set<Discount> availableDiscounts = new HashSet<>();

    /**
     * Gets available discounts.
     *
     * @return the available discounts
     */
    public Set<Discount> getAvailableDiscounts() {
        return availableDiscounts;
    }

    /**
     * Sets available discounts.
     *
     * @param availableDiscounts the available discounts
     */
    public void setAvailableDiscounts(Set<Discount> availableDiscounts) {
        this.availableDiscounts = availableDiscounts;
    }

    /**
     * Gets loyalty levels used.
     *
     * @return the loyalty levels used
     */
    public Set<LoyaltyLevel> getLoyaltyLevelsUsed() {
        return loyaltyLevelsUsed;
    }

    /**
     * Check if a loyalty level is already used
     *
     * @param level the level checked
     * @return True if it's used and false otherwise
     */
    public boolean isLevelUsed(LoyaltyLevel level){
        return this.loyaltyLevelsUsed.contains(level);
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
     * Gets loyalty points.
     *
     * @return the loyalty points
     */
    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    /**
     * Sets loyalty points.
     *
     * @param loyaltyPoints the loyalty points
     */
    public void setLoyaltyPoints(int loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    /**
     * Gets start date.
     *
     * @return the start date
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Sets start date.
     *
     * @param endDate the start date
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LoyaltyAccount that = (LoyaltyAccount) o;

        if (id != that.id) return false;
        if (loyaltyPoints != that.loyaltyPoints) return false;
        if (endDate != null ? !endDate.equals(that.endDate) : that.endDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + loyaltyPoints;
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        return result;
    }

    /**
     * Gets loyalty program.
     *
     * @return the loyalty program
     */
    public LoyaltyProgram getLoyaltyProgram() {
        return loyaltyProgram;
    }

    /**
     * Sets loyalty program.
     *
     * @param loyaltyProgram the loyalty program
     */
    public void setLoyaltyProgram(LoyaltyProgram loyaltyProgram) {
        this.loyaltyProgram = loyaltyProgram;
    }

    /**
     * Add a level to the list of level used
     *
     * @param level the level added
     */
    public void addLoyaltyLevelUsed(LoyaltyLevel level){
        this.loyaltyLevelsUsed.add(level);
        this.getAvailableDiscounts().add(level.getDiscount());
    }

    /**
     * Reset the list of loyalty levels used
     */
    public void resetLoyaltyLevelUsed(){
        this.loyaltyLevelsUsed = new HashSet<>();
    }
}
