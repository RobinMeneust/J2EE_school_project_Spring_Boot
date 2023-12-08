package j2ee_project.controller.order;

import j2ee_project.dao.AddressDAO;
import j2ee_project.dao.discount.DiscountDAO;
import j2ee_project.dao.order.CartItemDAO;
import j2ee_project.dao.order.OrdersDAO;
import j2ee_project.dto.AddressDTO;
import j2ee_project.model.Address;
import j2ee_project.model.Discount;
import j2ee_project.model.order.Cart;
import j2ee_project.model.order.CartItem;
import j2ee_project.model.order.OrderItem;
import j2ee_project.model.order.Orders;
import j2ee_project.service.CartManager;
import j2ee_project.service.DTOService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import j2ee_project.model.user.Customer;

import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;
import java.util.Map;
import java.util.Set;

/**
 * This class is a servlet used to confirm the user's cart and redirect him to a payment page. It's a controller in the MVC architecture of this project.
 *
 * @author Robin MENEUST
 */
@WebServlet("/confirm-cart")
public class ConfirmCartController extends HttpServlet {
    /**
     * Confirm the current cart
     * @param request Request object received by the servlet
     * @param response Response to be sent
     * @throws ServletException If the request for the GET could not be handled
     * @throws IOException If an input or output error is detected when the servlet handles the GET request
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Object obj = session.getAttribute("user");
        Customer customer = null;
        if(obj instanceof Customer) {
            customer = (Customer) obj;
        } else {
            response.sendRedirect("login");
            return;
        }
        Cart cart = CartManager.getCart(session, customer);

        Set<CartItem> cartItems = cart.getCartItems();

        if(cartItems == null || cartItems.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "the cart is empty");
            return;
        }
        for(CartItem item : cartItems) {
            if(item.getQuantity() > item.getProduct().getStockQuantity()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "the stock quantities has been updated and are lesser than the quantity you ordered");
                return;
            }
        }

        String discountIdStr = request.getParameter("discount-id");

        if(discountIdStr != null && !discountIdStr.trim().isEmpty()) {
            try {
                Discount discount = DiscountDAO.getDiscount(Integer.parseInt(discountIdStr));
                cart.setDiscount(discount);
            } catch (Exception ignore) {}
        }

        String streetAddress = request.getParameter("street-address");
        String city = request.getParameter("city");
        String postalCode = request.getParameter("postal-code");
        String country = request.getParameter("country");

        AddressDTO deliveryAddressDTO = new AddressDTO(streetAddress, postalCode, city, country);
        Map<String, String> inputErrors = DTOService.addressDataValidation(deliveryAddressDTO);
        if(!inputErrors.isEmpty()){
            RequestDispatcher dispatcher = request.getRequestDispatcher("cart?tab=confirmation");
            request.setAttribute("InputError", inputErrors);
            dispatcher.forward(request,response);
            return;
        }

        Address deliveryAddress = new Address(deliveryAddressDTO);
        deliveryAddress = AddressDAO.addAddressIfNotExists(deliveryAddress);

        Orders newOrder = new Orders(cart, cart.getDiscount(), new Date(Calendar.getInstance().getTimeInMillis()), customer, deliveryAddress);
        OrdersDAO.addOrder(newOrder);

        response.sendRedirect("pay?order-id="+newOrder.getId());
    }
}
