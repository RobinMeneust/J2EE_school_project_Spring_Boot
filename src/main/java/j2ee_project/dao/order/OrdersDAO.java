package j2ee_project.dao.order;

import j2ee_project.dao.JPAUtil;
import j2ee_project.model.order.OrderStatus;
import j2ee_project.model.order.Orders;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.hibernate.Session;

import java.util.*;

/**
 * Class that interact with the database to edit the Orders table in the database
 *
 * @author Robin Meneust
 */
public class OrdersDAO {

    /**
     * Get orders list by customer id
     *
     * @param customerId the customer id whose orders are got
     * @return the list of orders
     */
    public static List<Orders> getOrders(int customerId){
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        List<Orders> orders = entityManager.createQuery("FROM Orders WHERE customer=:customerId", Orders.class).setParameter("customerId", customerId).getResultList();

        transaction.commit();
        entityManager.close();
        return orders;
    }

    /**
     * Add an order
     *
     * @param newOrder the new order
     */
    public static void addOrder(Orders newOrder) {
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.persist(newOrder);

        transaction.commit();
        entityManager.close();
    }

    /**
     * Get an order by id
     *
     * @param orderId the order's id
     * @return the order fetched
     */
    public static Orders getOrder(String orderId) {
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Orders order = entityManager.find(Orders.class,orderId);

        transaction.commit();
        entityManager.close();
        return order;
    }

    /**
     * Sets the status of the given order
     *
     * @param order       the order whose status is changed
     * @param orderStatus the new order status
     */
    public static void setStatus(Orders order, OrderStatus orderStatus) {
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Orders orderDBObj = entityManager.find(Orders.class,order.getId());
        if(orderDBObj != null) {
            orderDBObj.setOrderStatus(orderStatus);
            transaction.commit();
        }

        entityManager.close();
    }
}
