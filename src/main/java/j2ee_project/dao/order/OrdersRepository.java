package j2ee_project.dao.order;

import j2ee_project.model.order.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface OrdersRepository extends JpaRepository<Orders, Integer> {

    Orders findOrdersById(int id);
    List<Orders> searchOrdersByCustomerId(int customer_id);

    int countOrdersByAddress_Id(int address_id);



}
