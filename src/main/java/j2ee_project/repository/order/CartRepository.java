package j2ee_project.repository.order;

import j2ee_project.model.order.Cart;
import j2ee_project.model.order.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;


/**
 * The interface Cart repository to interacts with the Cart table in the DB
 */
@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

    /**
     * Search a cart in the database according to the customer
     *
     * @param customer_id the customer id
     * @return the found cart
     */
    Cart getCartByCustomerId(int customer_id);

    /**
     * Search a cart in the database according to its id
     *
     * @param id the id
     * @return the found cart
     */
    Cart getCartById(int id);

    /**
     * Remove a cart in the database according to its id
     *
     * @param id the id
     */
    void removeCartById(int id);
}

