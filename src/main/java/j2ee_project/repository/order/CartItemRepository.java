package j2ee_project.repository.order;

import j2ee_project.model.order.Cart;
import j2ee_project.model.order.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * The interface Cart repository to interacts with the Cart table in the DB
 */
@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    CartItem getCartItemById(int id);
    void removeCartItemsByCart(Cart cart);
    void removeCartItemsById(int id);

}

