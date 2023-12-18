package j2ee_project.service.order;

import j2ee_project.repository.order.CartItemRepository;
import j2ee_project.repository.order.CartRepository;
import j2ee_project.repository.user.CustomerRepository;
import j2ee_project.model.Discount;
import j2ee_project.model.order.Cart;
import j2ee_project.model.order.CartItem;
import j2ee_project.model.user.Customer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The type Cart service.
 */
@Service
@Transactional
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    private final CustomerRepository customerRepository;

    /**
     * Instantiates a new Cart service.
     *
     * @param cartRepository     the cart repository
     * @param cartItemRepository the cart item repository
     * @param customerRepository the customer repository
     */
    public CartService(CartRepository cartRepository, CartItemRepository cartItemRepository, CustomerRepository customerRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.customerRepository = customerRepository;
    }

    /**
     * Add an item to a cart
     *
     * @param cart the cart to which an item is added
     * @param item the item added
     */
    @Transactional
    public void addItem(Cart cart, CartItem item) {
        CartItem itemDBObj = this.cartItemRepository.save(item);
        itemDBObj.setCart(cart);
        cart.getCartItems().add(itemDBObj);
        this.cartRepository.save(cart);
    }

    /**
     * Add a cart
     *
     * @param cart the cart added
     */
    @Transactional
    public void addCart(Cart cart) {
        this.cartRepository.save(cart);
    }

    /**
     * Remove cart
     *
     * @param cart the cart removed
     */
    @Transactional
    public void removeCart(Cart cart) {
        if(cart == null) {
            return;
        }
        Cart cartDbObject = this.cartRepository.getCartById(cart.getId());
        cartDbObject.getCustomer().setCart(null);
        this.cartRepository.removeCartById(cartDbObject.getId());
    }

    /**
     * Update the cart only if the customer's cart is empty
     *
     * @param customer the customer
     * @param cart     the new cart
     */
    @Transactional
    public void updateCart(Customer customer, Cart cart) {
        Cart oldCart = customer.getCart();
        if(oldCart != null && oldCart.getCartItems() != null && !oldCart.getCartItems().isEmpty()) {
            // Cancelled : The cart should not be updated since the old one in still active
            return;
        }

        try {
            this.removeCart(oldCart);  // Remove the old cart to persist a new one

            Customer customerDbObject = this.customerRepository.findCustomerById(customer.getId());
            if (customerDbObject == null) {
                return;
            }
            cart.setCustomer(customerDbObject);
            cart.setId(0); // To persist a new cart

            this.cartRepository.save(cart);
            customerDbObject.setCart(cart);

            this.customerRepository.save(customerDbObject);

        } catch (Exception e) {
            System.err.println("Transactions failed "+e.getMessage());
        }
    }

    /**
     * Get a customer's cart the customer's id
     *
     * @param id the id of the customer
     * @return the cart of the customer
     */
    @Transactional
    public Cart getCartFromCustomerId(int id) {
        return this.cartRepository.getCartByCustomerId(id);
    }

    /**
     * Set a cart discount
     *
     * @param cartId   the cart id whose discount is set
     * @param discount the discount set
     */
    @Transactional
    public void setDiscount(int cartId, Discount discount) {
        Cart cart = this.cartRepository.getCartById(cartId);
        if(cart != null) {
            cart.setDiscount(discount);
        }
    }

    /**
     * Save the cart
     *
     * @param cart the cart to be saved
     */
    @Transactional
    public void save(Cart cart) {
        if(cart==null) return;
        cartRepository.save(cart);
    }
}
