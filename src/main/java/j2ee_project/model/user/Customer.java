package j2ee_project.model.user;

import j2ee_project.dto.CustomerDTO;
import j2ee_project.dto.UserDTO;
import j2ee_project.model.Address;
import j2ee_project.model.order.Cart;
import j2ee_project.model.loyalty.LoyaltyAccount;
import j2ee_project.model.order.Orders;
import jakarta.persistence.*;

import java.util.Set;

/**
 * User who can buy products but without management permissions
 */
@Entity
@PrimaryKeyJoinColumn(name = "idUser")
public class Customer extends User{

    @OneToOne(mappedBy = "customer",cascade = CascadeType.ALL, orphanRemoval = true)
    private Cart cart;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idAddress", referencedColumnName = "id")
    private Address address;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idLoyaltyAccount", referencedColumnName = "id")
    private LoyaltyAccount loyaltyAccount;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Orders> orders;

    /**
     * Instantiates a new Customer.
     *
     * @param customerDTO the customer dto
     */
    public Customer(CustomerDTO customerDTO){
        super(customerDTO);
    }

    /**
     * Instantiates a new Customer.
     */
    public Customer() {
        super();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;

        if (this.getId() != customer.getId()) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return this.getId();
    }

    /**
     * Gets cart.
     *
     * @return the cart
     */
    public Cart getCart() {
        return cart;
    }

    /**
     * Sets cart.
     *
     * @param cart the cart
     */
    public void setCart(Cart cart) {
        this.cart = cart;
    }

    /**
     * Gets address.
     *
     * @return the address
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Sets address.
     *
     * @param address the address
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * Gets loyalty account.
     *
     * @return the loyalty account
     */
    public LoyaltyAccount getLoyaltyAccount() {
        return loyaltyAccount;
    }

    /**
     * Sets loyalty account.
     *
     * @param loyaltyAccount the loyalty account
     */
    public void setLoyaltyAccount(LoyaltyAccount loyaltyAccount) {
        this.loyaltyAccount = loyaltyAccount;
    }

    /**
     * Gets orders.
     *
     * @return the orders
     */
    public Set<Orders> getOrders() {
        return orders;
    }

    /**
     * Sets orders.
     *
     * @param orders the orders
     */
    public void setOrders(Set<Orders> orders) {
        this.orders = orders;
    }
}
