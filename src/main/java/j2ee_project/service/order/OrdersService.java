package j2ee_project.service.order;

import j2ee_project.repository.order.OrdersRepository;
import j2ee_project.model.order.OrderStatus;
import j2ee_project.model.order.Orders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * The type Orders service.
 */
@Service
public class OrdersService {

    private final OrdersRepository ordersRepository;


    /**
     * Instantiates a new Orders service.
     *
     * @param ordersRepository the orders repository
     */
    public OrdersService(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    /**
     * Get orders list by customer id
     *
     * @param customerId the customer id whose orders are got
     * @return the list of orders
     */
    @Transactional
    public List<Orders> getOrders(int customerId){
        return this.ordersRepository.searchOrdersByCustomerId(customerId);
    }

    /**
     * Add an order
     *
     * @param newOrder the new order
     */
    @Transactional
    public void addOrder(Orders newOrder) {
        this.ordersRepository.save(newOrder);
    }

    /**
     * Get an order by id
     *
     * @param orderId the order's id
     * @return the order fetched
     */
    @Transactional
    public Orders getOrder(int orderId) {
        return this.ordersRepository.findOrdersById(orderId);
    }

    /**
     * Sets the status of the given order
     *
     * @param order       the order whose status is changed
     * @param orderStatus the new order status
     */
    @Transactional
    public void setStatus(Orders order, OrderStatus orderStatus) {
        Orders orderDBObj = this.getOrder(order.getId());
        if(orderDBObj != null) {
            orderDBObj.setOrderStatus(orderStatus);
        }
    }


}
