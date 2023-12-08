package j2ee_project.controller.order;

import j2ee_project.dao.catalog.product.ProductDAO;
import j2ee_project.dao.discount.DiscountDAO;
import j2ee_project.dao.loyalty.LoyaltyLevelDAO;
import j2ee_project.dao.order.CartDAO;
import j2ee_project.dao.user.CustomerDAO;
import j2ee_project.model.Discount;
import j2ee_project.model.catalog.Product;
import j2ee_project.model.loyalty.LoyaltyAccount;
import j2ee_project.model.loyalty.LoyaltyLevel;
import j2ee_project.model.loyalty.LoyaltyProgram;
import j2ee_project.model.order.Cart;
import j2ee_project.model.order.CartItem;
import j2ee_project.model.user.Customer;
import j2ee_project.service.CartManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

/**
 * This class is a servlet used to add a loyalty level discount to the cart. It's a controller in the MVC architecture of this project.
 *
 * @author Robin MENEUST
 */
@WebServlet("/cart/loyalty-level-discount")
public class AddLoyaltyLevelDiscountToCart extends HttpServlet {
    /**
     * Add a loyalty level discount to the cart
     * @param request Request object received by the servlet
     * @param response Response to be sent
     * @throws ServletException If the request for the GET could not be handled
     * @throws IOException If an input or output error is detected when the servlet handles the GET request
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        BufferedReader reader = request.getReader();
        JSONTokener tokener = new JSONTokener(reader);
        JSONObject paramsObject = new JSONObject(tokener);

        int id = -1;
        try {
            id = paramsObject.getInt("id");
        } catch (Exception ignore) {}

        Discount discount = DiscountDAO.getDiscount(id);

        HttpSession session = request.getSession();

        Object obj = session.getAttribute("user");
        Customer customer = null;
        if(obj instanceof Customer) {
            customer = (Customer) obj;
        }

        if(customer == null) {
            response.sendRedirect("login");
            return;
        }

        LoyaltyAccount loyaltyAccount = customer.getLoyaltyAccount();

        if(loyaltyAccount == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "You don't hava a loyalty account");
            System.err.println("You don't hava a loyalty account");
            return;
        }

        if(discount != null) {
            Set<Discount> discounts = loyaltyAccount.getAvailableDiscounts();

            if (discounts == null || discounts.isEmpty() || !discounts.contains(discount)) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "You don't have any claimed discount or the one you provided is unavailable");
                System.err.println("You don't have any claimed discount or the one you provided is unavailable");
                return;
            }

            if (discount.hasExpired()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "The discount has expired");
                System.err.println("The discount has expired");
                return;
            }
        }

        Cart cart = CartDAO.getCartFromCustomerId(customer.getId());

        if(cart == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "You don't have a cart");
            System.err.println("You don't have a cart");
            return;
        }

        CartDAO.setDiscount(cart.getId(), discount);

        // Refresh the user's cart
        customer.setCart(CartDAO.getCartFromCustomerId(customer.getId()));
        session.setAttribute("user", customer);

        response.setStatus(HttpServletResponse.SC_OK);
    }
}
