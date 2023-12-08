package j2ee_project.dao.order;

import j2ee_project.dao.JPAUtil;
import j2ee_project.dao.user.CustomerDAO;
import j2ee_project.model.order.Cart;
import j2ee_project.model.order.CartItem;
import j2ee_project.model.order.Orders;
import j2ee_project.model.user.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.hibernate.Session;

import java.util.Set;

/**
 * Class that interact with the database to edit the CartItem table in the database
 *
 * @author Robin Meneust
 */
public class CartItemDAO {

    /**
     * Edit an item quantity and check before if the cart of the given customer is associated to it
     *
     * @param customer   the customer to whom the cart to be edited belongs
     * @param cartItemId the cart item id whose quantity is changed
     * @param quantity   the new quantity
     */
    public static void editItemQuantity(Customer customer, int cartItemId, int quantity) {
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        CartItem cartItemDBObj = entityManager.find(CartItem.class,cartItemId);

        if(cartItemDBObj == null || cartItemDBObj.getCart() == null || customer == null || !customer.equals(cartItemDBObj.getCart().getCustomer())) {
            return;
        }

        if(cartItemDBObj == null) {
            return;
        }

        if(quantity<=0) {
            cartItemDBObj.getCart().getCartItems().remove(cartItemDBObj);
            entityManager.remove(cartItemDBObj);
        } else {
            cartItemDBObj.setQuantity(quantity);
        }


        transaction.commit();
        entityManager.close();
    }


    /**
     * Remove all cart items
     *
     * @param cart the cart whose items are removed
     */
    public static void removeCartItems(Cart cart) {
        if(cart == null) {
            return;
        }
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        cart = entityManager.find(Cart.class,cart.getId());

        if(cart != null) {
            Set<CartItem> cartItems = cart.getCartItems();
            if (cartItems != null) {
                for (CartItem item : cartItems) {
                    entityManager.remove(item);
                }
            }
        }

        transaction.commit();
        entityManager.close();
    }

    /**
     * Add an item to a cart and get its ID in the DB
     *
     * @param item the item added
     * @return the new item's ID
     */
    public static int newItem(CartItem item) {
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.persist(item);
        entityManager.flush();
        int id = item.getId();

        transaction.commit();
        entityManager.close();

        return id;
	}
}
