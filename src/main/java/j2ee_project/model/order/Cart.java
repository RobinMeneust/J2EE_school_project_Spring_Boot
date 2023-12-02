package j2ee_project.model.order;

import j2ee_project.model.Discount;
import j2ee_project.model.catalog.Product;
import j2ee_project.model.user.Customer;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name="`Cart`")
public class Cart {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "`id`", nullable = false)
    private int id;
    @ManyToOne
    @JoinColumn(name = "`idDiscount`", referencedColumnName = "`id`")
    private Discount discount;
    @OneToOne
    @JoinColumn(name = "`idCustomer`", referencedColumnName = "`idUser`", nullable = false)
    private Customer customer;
    @OneToMany(mappedBy = "`cart`", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST,CascadeType.MERGE, CascadeType.REMOVE})
    private Set<CartItem> cartItems;

    public int getId() {
        return id;
    }

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

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Set<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(Set<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

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
