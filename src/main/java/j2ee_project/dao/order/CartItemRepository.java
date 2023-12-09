package j2ee_project.dao.order;

import j2ee_project.model.order.Cart;
import j2ee_project.model.order.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


/**
 * The interface Cart repository to interacts with the Cart table in the DB
 */
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    CartItem getCartItemById(int id);
    void removeCartItemsByCart(Cart cart);
    void removeCartItemsById(int id);

}

