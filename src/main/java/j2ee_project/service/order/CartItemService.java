package j2ee_project.service.order;

import j2ee_project.dao.order.CartItemRepository;
import j2ee_project.dao.order.CartRepository;
import j2ee_project.model.order.Cart;
import j2ee_project.model.order.CartItem;
import j2ee_project.model.user.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CartItemService {

    private final CartItemRepository cartItemRepository;

    public CartItemService(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    /**
     * Edit an item quantity and check before if the cart of the given customer is associated to it
     *
     * @param customer   the customer to whom the cart to be edited belongs
     * @param cartItemId the cart item id whose quantity is changed
     * @param quantity   the new quantity
     */
    public void editItemQuantity(Customer customer, int cartItemId, int quantity) {
        CartItem cartItemDBObj = this.cartItemRepository.getCartItemById(cartItemId);

        if(cartItemDBObj == null || cartItemDBObj.getCart() == null || customer == null || !customer.equals(cartItemDBObj.getCart().getCustomer())) {
            return;
        }

        if(quantity<=0) {
            cartItemDBObj.getCart().getCartItems().remove(cartItemDBObj);
            this.cartItemRepository.removeCartItemsById(cartItemId);
        } else {
            cartItemDBObj.setQuantity(quantity);
            this.cartItemRepository.save(cartItemDBObj);
        }
    }


    /**
     * Remove all cart items
     *
     * @param cart the cart whose items are removed
     */
    public void removeCartItems(Cart cart) {
        this.cartItemRepository.removeCartItemsByCart(cart);
    }

    /**
     * Add an item to a cart and get its ID in the DB
     *
     * @param item the item added
     * @return the new item's ID
     */
    public int newItem(CartItem item) {
        this.cartItemRepository.save(item);
        return item.getId();
    }

}