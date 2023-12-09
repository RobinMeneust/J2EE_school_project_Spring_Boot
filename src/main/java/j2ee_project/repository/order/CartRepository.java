package j2ee_project.repository.order;

import j2ee_project.model.order.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * The interface Cart repository to interacts with the Cart table in the DB
 */
@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

    Cart getCartByCustomerId(int customer_id);

    Cart getCartById(int id);

    void removeCartById(int id);

}

