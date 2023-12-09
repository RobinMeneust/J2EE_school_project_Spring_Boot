package j2ee_project.dao.order;

import j2ee_project.model.order.Cart;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * The interface Cart repository to interacts with the Cart table in the DB
 */
public interface CartRepository extends JpaRepository<Cart, Integer> {

    Cart getCartByCustomerId(int customer_id);

    Cart getCartById(int id);

    void removeCartById(int id);

}

