package j2ee_project.controller.order;

import j2ee_project.dao.catalog.product.ProductDAO;
import j2ee_project.dao.order.CartDAO;
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

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;
import org.json.JSONObject;

/**
 * This class is a servlet used to add items to the cart. It's a controller in the MVC architecture of this project.
 *
 * @author Robin MENEUST
 */
@WebServlet("/add-to-cart")
public class AddToCartController extends HttpServlet {
    /**
     * Add an item to the user cart
     * @param request Request object received by the servlet
     * @param response Response to be sent
     * @throws ServletException If the request for the GET could not be handled
     * @throws IOException If an input or output error is detected when the servlet handles the GET request
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        JSONObject responseObj = new JSONObject();

        String idStr = request.getParameter("id");
        int id = -1;
        if(idStr != null && !idStr.trim().isEmpty()) {
            try {
                id = Integer.parseInt(idStr);
            } catch(Exception ignore) {}
        }

        Product product = ProductDAO.getProduct(id);

        if(product == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "The provided product ID doesn't exist");
            return;
        }

        HttpSession session = request.getSession();

        Object obj = session.getAttribute("user");
        Customer customer = null;
        if(obj instanceof Customer) {
            customer = (Customer) obj;
        }
        Cart cart;

        cart = CartManager.getCart(session, customer);

        if(cart == null) {
            // We need to create a new cart
            cart = new Cart();
            cart.setCustomer(customer);

            CartDAO.addCart(cart);
        }

        Set<CartItem> cartItems = cart.getCartItems();

        if(cartItems == null) {
            cartItems = new HashSet<>();
            cart.setCartItems(cartItems);
        }

        CartItem newItem = new CartItem();
        newItem.setProduct(product);
        newItem.setQuantity(1);

        if(cartItems.contains(newItem)) {
            response.setStatus(HttpServletResponse.SC_OK);
            PrintWriter out = response.getWriter();
            responseObj = new JSONObject();
            responseObj.put("isAlreadyInCart", true);
            out.print(responseObj);
            out.flush();
            return;
        }

        // If the product is not already in the cart
        if(customer == null) {
            newItem.setCart(cart);
            cart.getCartItems().add(newItem); // Add to the cart object (not saved in the db)
            session.setAttribute("sessionCart", cart);
        } else {
            CartDAO.addItem(cart, newItem); // Add to the cart of the customer (saved in the db)
            // Refresh the user's cart
            customer.setCart(CartDAO.getCartFromCustomerId(customer.getId()));
            session.setAttribute("user", customer);
        }
        response.setStatus(HttpServletResponse.SC_OK);
        PrintWriter out = response.getWriter();
        out.print(responseObj); // empty JSON response
        out.flush();
    }
}
