package j2ee_project.model.order;

import j2ee_project.model.catalog.Product;
import jakarta.persistence.*;

import java.util.Objects;

/**
 * Item of an order
 */
@Entity
public class OrderItem {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "quantity", nullable = false)
    private int quantity;
    @ManyToOne
    @JoinColumn(name = "idProduct", referencedColumnName = "id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "idOrder", referencedColumnName = "id", nullable = true)
    private Orders order;
    @Basic
    @Column(name = "total", nullable = false)
    private float total;

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
     * Gets quantity.
     *
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets quantity.
     *
     * @param quantity the quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets order.
     *
     * @return the order
     */
    public Orders getOrder() {
        return order;
    }

    /**
     * Sets order.
     *
     * @param order the order
     */
    public void setOrder(Orders order) {
        this.order = order;
    }

    /**
     * Gets product.
     *
     * @return the product
     */
    public Product getProduct() {
        return product;
    }

    /**
     * Sets product.
     *
     * @param product the product
     */
    public void setProduct(Product product) {
        this.product = product;
    }

    /**
     * Gets total.
     *
     * @return the total
     */
    public float getTotal() {
        return total;
    }

    /**
     * Sets total.
     *
     * @param total the total
     */
    public void setTotal(float total) {
        this.total = total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return id == orderItem.id && quantity == orderItem.quantity && product.equals(orderItem.product) && total == orderItem.total && order.equals(orderItem.order);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + quantity;
        result = 31 * result + order.getId();
        result = 31 * result + Float.hashCode(total);
        result = 31 * result +product.getId();
        return result;
    }
}
