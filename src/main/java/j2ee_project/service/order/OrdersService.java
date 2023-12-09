package j2ee_project.service.order;

import j2ee_project.dao.order.OrdersRepository;
import j2ee_project.model.order.OrderStatus;
import j2ee_project.model.order.Orders;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public class OrdersService {

    private final OrdersRepository ordersRepository;

    public OrdersService(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    /**
     * Get orders list by customer id
     *
     * @param customerId the customer id whose orders are got
     * @return the list of orders
     */
    public List<Orders> getOrders(int customerId){
        return this.ordersRepository.searchOrdersByCustomerId(customerId);
    }

    /**
     * Add an order
     *
     * @param newOrder the new order
     */
    public void addOrder(Orders newOrder) {
        this.ordersRepository.save(newOrder);
    }

    /**
     * Get an order by id
     *
     * @param orderId the order's id
     * @return the order fetched
     */
    public Orders getOrder(int orderId) {
        return this.ordersRepository.findOrdersById(orderId);
    }

    /**
     * Sets the status of the given order
     *
     * @param order       the order whose status is changed
     * @param orderStatus the new order status
     */
    public void setStatus(Orders order, OrderStatus orderStatus) {
        Orders orderDBObj = this.getOrder(order.getId());
        if(orderDBObj != null) {
            orderDBObj.setOrderStatus(orderStatus);
        }
    }


}