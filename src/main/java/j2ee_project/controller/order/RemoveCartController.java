package j2ee_project.controller.order;

import j2ee_project.dao.catalog.product.ProductDAO;
import j2ee_project.dao.order.CartDAO;
import j2ee_project.dao.user.CustomerDAO;
import j2ee_project.model.catalog.Product;
import j2ee_project.model.order.Cart;
import j2ee_project.model.order.CartItem;
import j2ee_project.model.user.Customer;
import j2ee_project.service.AuthService;
import j2ee_project.service.CartManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

/**
 * This class is a servlet used to remove the current user's cart. It's a controller in the MVC architecture of this project.
 *
 * @author Robin MENEUST
 */
@WebServlet("/remove-cart")
public class RemoveCartController extends HttpServlet {
    /**
     * Remove the cart of the user in the session
     * @param request Request object received by the servlet
     * @param response Response to be sent
     * @throws ServletException If the request for the GET could not be handled
     * @throws IOException If an input or output error is detected when the servlet handles the GET request
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        HttpSession session = request.getSession();
        Object obj = session.getAttribute("user");
        if(obj instanceof Customer) {
            // DB cart (logged in customer)
            Customer customer = (Customer) obj;
            CartDAO.removeCart(customer.getCart());
            session.setAttribute("user", CustomerDAO.getCustomer(customer.getId())); // update the session variable
        } else {
            // Session cart
            Cart cart = CartManager.getSessionCart(session);
            if(cart != null) {
                cart.setCartItems(new HashSet<>());
            }
            session.setAttribute("cart",cart); // update the session variable
        }
        response.sendRedirect("cart");
    }
}
