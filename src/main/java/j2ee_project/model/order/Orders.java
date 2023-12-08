package j2ee_project.model.order;

import j2ee_project.model.Address;
import j2ee_project.model.Discount;
import j2ee_project.model.user.Customer;
import jakarta.persistence.*;

import java.sql.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Order that a user can confirm (pay), it can also be cancelled, shipped... The cart is used before the payment and the order is what we use after to keep a track of what has been ordered
 */
@Entity
public class Orders implements Comparable<Orders> {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "date", nullable = false)
    private Date date;
    @Basic
    @Column(name = "orderStatus", nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private Set<OrderItem> orderItems;
    @ManyToOne
    @JoinColumn(name = "idCustomer", referencedColumnName = "idUser", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "idAddress", referencedColumnName = "id", nullable = false)
    private Address address;

    @Transient
    private static final float shippingFees = 5.0f;

    @ManyToOne
    @JoinColumn(name = "idDiscount", referencedColumnName = "id")
    private Discount discount;

    /**
     * Instantiates a new Order.
     */
    public Orders() {
    }

    /**
     * Instantiates a new Order.
     *
     * @param cart     the cart
     * @param discount the discount
     * @param date     the date
     * @param customer the customer
     * @param address  the address
     */
    public Orders(Cart cart, Discount discount, Date date, Customer customer, Address address) {
        this.discount = discount;
        this.date = date;
        this.orderItems = null;
        this.customer = customer;
        this.address = address;
        this.orderStatus = OrderStatus.WAITING_PAYMENT;
        loadItemsFromCart(cart);
    }

    /**
     * Gets discount.
     *
     * @return the discount
     */
    public Discount getDiscount() {
        return discount;
    }

    /**
     * Sets discount.
     *
     * @param discount the discount
     */
    public void setDiscount(Discount discount) {
        this.discount = discount;
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
     * Gets total.
     *
     * @return the total
     */
    public float getTotal() {
        float total = 0.0f;
        if (orderItems != null) {
            for (OrderItem item : orderItems) {
                total += item.getTotal();
            }
        }
        return total + shippingFees;
    }

    /**
     * Gets date.
     *
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets date.
     *
     * @param date the date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Gets order status.
     *
     * @return the order status
     */
    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    /**
     * Sets order status.
     *
     * @param orderStatus the order status
     */
    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Orders orders = (Orders) o;

        if (id != orders.id) return false;
        if (!orderItems.equals(orders.orderItems)) return false;
        if (date != null ? !date.equals(orders.date) : orders.date != null) return false;
        if (orderStatus != null ? !orderStatus.equals(orders.orderStatus) : orders.orderStatus != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + date.hashCode();
        result = 31 * result + orderStatus.ordinal();
        result = 31 * result + customer.getId();
        result = 31 * result + address.getId();
        return result;
    }

    /**
     * Gets order items.
     *
     * @return the order items
     */
    public Set<OrderItem> getOrderItems() {
        return orderItems;
    }

    /**
     * Sets order items.
     *
     * @param orderItems the order items
     */
    public void setOrderItems(Set<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    /**
     * Gets customer.
     *
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Sets customer.
     *
     * @param customer the customer
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Gets delivery address.
     *
     * @return the address
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Sets delivery address.
     *
     * @param address the address
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        String str = "ORDER n°" + id + "\n";
        for (OrderItem item : getOrderItems()) {
            str += "- " + item.getProduct().getName() + "   (" + item.getQuantity() + ")  price: " + item.getTotal() + "\n";
        }
        str += "\n+ Shipment fees: 5.0 €\n\nTotal: "+getTotal();
        return str;
    }

    private void loadItemsFromCart(Cart cart) {
        this.setOrderItems(new HashSet<>());
        if (cart == null || cart.getCartItems() == null) {
            return;
        }

        float cartPricePercentage = 1;
        if (cart.getDiscount() != null) {
            cartPricePercentage = (1 - ((float) cart.getDiscount().getDiscountPercentage() / 100));
        }

        for (CartItem item : cart.getCartItems()) {
            OrderItem newItem = new OrderItem();
            newItem.setProduct(item.getProduct());
            newItem.setQuantity(item.getQuantity());
            newItem.setOrder(this);

            float itemPrice = item.getQuantity() * item.getProduct().getUnitPrice();
            Discount categoryDiscount = item.getProduct().getCategory().getDiscount();
            if (categoryDiscount != null) {
                itemPrice *= (1 - ((float) categoryDiscount.getDiscountPercentage() / 100));
            }
            itemPrice *= cartPricePercentage;

            newItem.setTotal(itemPrice);
            this.getOrderItems().add(newItem);
        }
    }

    @Override
    public int compareTo(Orders o) {
        return Integer.compare(getId(), o.getId());
    }
}
