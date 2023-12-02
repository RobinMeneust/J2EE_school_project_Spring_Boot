package j2ee_project.model.order;

import j2ee_project.model.Address;
import j2ee_project.model.Discount;
import j2ee_project.model.user.Customer;
import jakarta.persistence.*;

import java.sql.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="`Orders`")
public class Orders {
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
    @OneToMany(mappedBy = "order", fetch=FetchType.EAGER, cascade = {CascadeType.PERSIST,CascadeType.MERGE, CascadeType.REMOVE})
    private Set<OrderItem> orderItems;
    @ManyToOne
    @JoinColumn(name = "idCustomer", referencedColumnName = "idUser", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "idAddress", referencedColumnName = "id", nullable = false)
    private Address address;

    @Transient
    private static final float shippingFees = 5.0f;

    public Orders() { }

    public Orders(Cart cart, Date date, Customer customer, Address address) {
        this.date = date;
        this.orderItems = null;
        this.customer = customer;
        this.address = address;
        this.orderStatus = OrderStatus.WAITING_PAYMENT;
        loadItemsFromCart(cart);
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getTotal() {
        float total = 0.0f;
        if(orderItems != null) {
            for(OrderItem item : orderItems) {
                total += item.getTotal();
            }
        }
        return total + shippingFees;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

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

    public Set<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        String str = "ORDER n°"+id+"\n";
        for(OrderItem item : getOrderItems()) {
            str += "- "+item.getProduct().getName()+"   ("+item.getQuantity()+")  price: "+item.getTotal()+"\n";
        }
        return str;
    }

    private void loadItemsFromCart(Cart cart) {
        this.setOrderItems(new HashSet<>());
        if(cart == null || cart.getCartItems() == null) {
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
}
