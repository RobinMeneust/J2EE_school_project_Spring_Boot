package j2ee_project.model.order;

import j2ee_project.model.Discount;
import j2ee_project.model.catalog.Product;
import j2ee_project.model.user.Customer;
import jakarta.persistence.*;

import java.util.Set;

/**
 * Cart that a customer can buy or edit
 */
@Entity
public class Cart {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @ManyToOne
    @JoinColumn(name = "idDiscount", referencedColumnName = "id")
    private Discount discount;
    @OneToOne
    @JoinColumn(name = "idCustomer", referencedColumnName = "idUser", nullable = false)
    private Customer customer;
    @OneToMany(mappedBy = "cart", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST,CascadeType.MERGE, CascadeType.REMOVE})
    private Set<CartItem> cartItems;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cart cart = (Cart) o;

        if (id != cart.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }

    /**
     * Gets the active discount.
     *
     * @return the discount
     */
    public Discount getDiscount() {
        return discount;
    }

    /**
     * Sets active discount.
     *
     * @param discount the discount
     */
    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    /**
     * Gets associated customer.
     *
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Sets associated customer.
     *
     * @param customer the customer
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Gets cart items.
     *
     * @return the cart items
     */
    public Set<CartItem> getCartItems() {
        return cartItems;
    }

    /**
     * Sets cart items.
     *
     * @param cartItems the cart items
     */
    public void setCartItems(Set<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    /**
     * Gets total amount to pay.
     *
     * @return the total
     */
    public float getTotal() {
        float total = 0.0f;
        for(CartItem item : getCartItems()) {
            float itemPrice = item.getQuantity()*item.getProduct().getUnitPrice();
            Discount categoryDiscount = item.getProduct().getCategory().getDiscount();
            if(categoryDiscount != null) {
                itemPrice *= (1-((float)categoryDiscount.getDiscountPercentage()/100));
            }
            total += itemPrice;
        }

        if(getDiscount() != null) {
            total *= (1-((float)getDiscount().getDiscountPercentage()/100));
        }

        return total;
    }

    /**
     * Check if the cart contains a product
     *
     * @param productId the product id
     * @return True if it does contain it and false otherwise
     */
    public boolean containsProduct(int productId) {
        Set<CartItem> items = getCartItems();
        if(productId<0 || items == null) return false;
        for(CartItem item : items) {
            if(item.getProduct() != null && item.getProduct().getId() == productId) {
                return true;
            }
        }
        return false;
    }
}
