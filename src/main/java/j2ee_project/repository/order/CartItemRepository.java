package j2ee_project.repository.order;

import j2ee_project.model.order.Cart;
import j2ee_project.model.order.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.security.Signature;
import java.util.List;


/**
 * The interface Cart repository to interacts with the Cart table in the DB
 */
@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    /**
     * Search a cart item in the database according to its id
     *
     * @param id the id
     * @return the found cart item
     */
    CartItem getCartItemById(int id);

    /**
     * Remove a cart item in the database
     *
     * @param cart the cart to remove
     */
    void removeCartItemsByCart(Cart cart);

    /**
     * Remove a cart item in the database according to its id
     *
     * @param id the id
     */
    void removeCartItemsById(int id);

    /**
     * Search all cart items in the database with the product id
     *
     * @param product_Id the product id
     * @return the found categories list
     */
    List<CartItem> findCartItemsByProductId(int product_Id);
}

