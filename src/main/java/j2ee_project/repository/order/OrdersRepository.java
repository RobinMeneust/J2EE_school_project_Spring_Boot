package j2ee_project.repository.order;

import j2ee_project.model.order.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface Orders repository.
 */
@Repository

public interface OrdersRepository extends JpaRepository<Orders, Integer> {

    /**
     * Search an order in the database according to its id
     *
     * @param id the id
     * @return the found order
     */
    Orders findOrdersById(int id);

    /**
     * Search orders in the database according to the customer
     *
     * @param customer_id the customer id
     * @return the list of found orders
     */
    List<Orders> searchOrdersByCustomerId(int customer_id);

    /**
     * Count orders in the database according to the address
     *
     * @param address_id the address id
     * @return the number of found orders
     */
    int countOrdersByAddress_Id(int address_id);

}
