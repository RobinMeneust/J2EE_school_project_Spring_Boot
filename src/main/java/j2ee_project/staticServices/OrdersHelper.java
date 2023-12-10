package j2ee_project.staticServices;

import j2ee_project.Application;
import j2ee_project.model.order.Orders;
import j2ee_project.model.user.Customer;
import j2ee_project.model.user.Moderator;
import j2ee_project.model.user.User;
import j2ee_project.service.AuthService;
import j2ee_project.service.order.OrdersService;
import org.springframework.context.ApplicationContext;

public class OrdersHelper {

    private static final OrdersService ordersService;

    static {
        ApplicationContext context = Application.getContext();
        ordersService = context.getBean(OrdersService.class);
    }

    /**
     * Get an order according to its id
     *
     * @param idOrder the order id
     * @return the found order
     */
    public static Orders getOrder(int idOrder) {
        return ordersService.getOrder(idOrder);
    }
}
