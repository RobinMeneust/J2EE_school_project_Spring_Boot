package j2ee_project.model.order;

import j2ee_project.model.catalog.Product;
import jakarta.persistence.*;

@Entity
@Table(name="`CartItem`")
public class CartItem {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "`id`", nullable = false)
    private int id;
    @Basic
    @Column(name = "`quantity`", nullable = false)
    private int quantity;
    @ManyToOne
    @JoinColumn(name = "`idProduct`", referencedColumnName = "`id`", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "`idCart`", referencedColumnName = "`id`", nullable = true)
    private Cart cart;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CartItem cartItem = (CartItem) o;

        if (id != cartItem.id) return false;
        if (!product.equals(cartItem.product)) return false;
        if(!(quantity == cartItem.getQuantity())) return false;
        if(!cart.equals(cartItem.getCart())) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + quantity;
        return result;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return getProduct().toString() + " Qty: " + getQuantity();
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
}
