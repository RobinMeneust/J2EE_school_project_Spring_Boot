package j2ee_project.service;

import j2ee_project.model.order.OrderStatus;
import j2ee_project.model.order.Orders;
import j2ee_project.model.user.Customer;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Manages orders
 */
public class OrdersManager {
    /**
     * Check if an order is associated to the given customer who is not null, and if it has already been paid.
     * If one of those case is not met then an associated string is returned
     *
     * @param order    the order checked
     * @param customer the customer checked
     * @return the string message associated to the first condition that is not met (not logged in, not his order or already paid)
     */
    public static String checkOrder(Orders order, Customer customer) {
        if(customer == null) {
            return "You need to be logged in with a customer account";
        }

        if(!order.getCustomer().equals(customer)) {
            return "You can't pay the order of another customer";
        }

        if(order.getOrderStatus() != OrderStatus.WAITING_PAYMENT) {
            return "This order has already been paid";
        }

        return null;
    }
}
