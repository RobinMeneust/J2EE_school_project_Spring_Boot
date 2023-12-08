package j2ee_project.service;

import j2ee_project.dao.order.CartDAO;
import j2ee_project.model.order.Cart;
import j2ee_project.model.user.Customer;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/**
 * Provide helper functions to manage the cart (session and DB)
 */
public class CartManager {
	/**
	 * Gets session cart.
	 *
	 * @param session the session
	 * @return the session cart
	 */
	public static Cart getSessionCart(HttpSession session) {
		Object cartObj = session.getAttribute("sessionCart");
		Cart cart;

		if(!(cartObj instanceof Cart)) {
			cart = new Cart();
			session.setAttribute("sessionCart", cart);
		} else {
			cart = (Cart) cartObj;
		}

		return cart;
	}

	/**
	 * Gets cart (either from DB or session). The priority is given to the database cart
	 *
	 * @param session  the session in which we look for the cart
	 * @param customer the customer in the database from whom we look for the cart
	 * @return the cart
	 */
	public static Cart getCart(HttpSession session, Customer customer) {
		if(customer != null) {
			return customer.getCart();
		} else {
			return getSessionCart(session);
		}
	}

	/**
	 * Gets cart.
	 *
	 * @param sessionCart the session cart
	 * @param customer    the customer
	 * @return the cart
	 */
	public static Cart getCart(Cart sessionCart, Customer customer) {
		if(customer != null /* && AUTHENTICATED && IS IN DATABASE*/) {
			return customer.getCart();
		} else if(sessionCart != null && sessionCart.getId() <= 0) {
			// If it's not null and not associated to a customer
			return sessionCart;
		}
		return null;
	}

	/**
	 * Copy session cart to a database cart and associate it to a specific customer only if the customer cart is empty
	 *
	 * @param request  the request with the session
	 * @param customer the customer whose cart might  change
	 */
	public static void copySessionCartToCustomerEmptyCart(HttpServletRequest request, Customer customer) {
		HttpSession session = request.getSession();
		if(customer != null) {
			Cart cart = CartManager.getSessionCart(session);

			if(cart != null && cart.getCartItems() != null && !cart.getCartItems().isEmpty()) {
				// Copy the cart
				CartDAO.updateCart(customer, cart);
			}
			// The session cart and the user cart won't be sync, so it's better to clear the session cart and just use the user cart
			session.removeAttribute("sessionCart");
		}
	}
}
