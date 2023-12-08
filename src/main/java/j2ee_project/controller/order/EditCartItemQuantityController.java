package j2ee_project.controller.order;

import j2ee_project.dao.order.CartDAO;
import j2ee_project.dao.order.CartItemDAO;
import j2ee_project.dao.user.CustomerDAO;
import j2ee_project.dao.user.UserDAO;
import j2ee_project.model.order.Cart;
import j2ee_project.model.order.CartItem;
import j2ee_project.model.user.Customer;
import j2ee_project.model.user.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import j2ee_project.service.CartManager;

/**
 * This class is a servlet used to change the quantity of an item in a cart. It's a controller in the MVC architecture of this project.
 *
 * @author Robin MENEUST
 */
@WebServlet("/edit-cart-item-quantity")
public class EditCartItemQuantityController extends HttpServlet {
    /**
     * Edit the quantity of the cart item whose ID and new quantity are given
     * @param request Request object received by the servlet
     * @param response Response to be sent
     * @throws ServletException If the request for the GET could not be handled
     * @throws IOException If an input or output error is detected when the servlet handles the GET request
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        // Get params
        String idStr = request.getParameter("id");
		String quantityStr = request.getParameter("quantity");

        int id = -1;
        if(idStr != null && !idStr.trim().isEmpty()) {
            try {
                id = Integer.parseInt(idStr);
            } catch(Exception ignore) {}
        }


		int quantity = -1;
		if(quantityStr != null && !quantityStr.trim().isEmpty()) {
			try {
				quantity = Integer.parseInt(quantityStr);
			} catch(Exception ignore) {}
		}

		if(id<0 || quantity<0) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid item ID or quantity");
		}

        HttpSession session = request.getSession();

        // Get customer

        Object obj = session.getAttribute("user");
        Customer customer = null;
        if(obj instanceof Customer) {
            customer = (Customer) obj;
        }

        // Get cart

        Cart cart;

        if(customer == null) {
            cart = CartManager.getSessionCart(session);
            Set<CartItem> cartItems = cart.getCartItems();

            if(cartItems == null) {
                cartItems = new HashSet<>();
                cart.setCartItems(cartItems);
            }


            Iterator<CartItem> it = cartItems.iterator();

            while(it.hasNext()) {
                CartItem item = it.next();
                if(item.getId() == id) {
                    if(quantity<=0) {
                        it.remove();
                    } else {
                        item.setQuantity(quantity);
                    }
                    break;
                }
            }
        } else {
            CartItemDAO.editItemQuantity(customer, id, quantity);
            // Refresh the user's cart
            customer.setCart(CartDAO.getCartFromCustomerId(customer.getId()));
            session.setAttribute("user", customer);
        }

        response.sendRedirect("cart");
    }
}
