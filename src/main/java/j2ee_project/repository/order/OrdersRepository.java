package j2ee_project.repository.order;

import j2ee_project.model.order.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface OrdersRepository extends JpaRepository<Orders, Integer> {

    Orders findOrdersById(int id);
    List<Orders> searchOrdersByCustomerId(int customer_id);

    int countOrdersByAddress_Id(int address_id);



}
