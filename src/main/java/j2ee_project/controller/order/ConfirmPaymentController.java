package j2ee_project.controller.order;

import j2ee_project.dao.MailDAO;
import j2ee_project.dao.loyalty.LoyaltyAccountDAO;
import j2ee_project.dao.order.OrdersDAO;
import j2ee_project.model.Discount;
import j2ee_project.model.Mail;
import j2ee_project.model.order.OrderStatus;
import j2ee_project.model.order.Orders;
import j2ee_project.model.user.Customer;
import j2ee_project.service.MailManager;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;

/**
 * This class is a servlet used to confirm a payment. It's a controller in the MVC architecture of this project.
 *
 * @author Robin MENEUST
 */
@WebServlet("/confirm-payment")
public class ConfirmPaymentController extends HttpServlet
{
    /**
     * Confirm the payment and send a receipt via email if it's successful. Either redirect to the cart (discount expired) or to the order receipt. We also update the Order status.
     * @param request Request object received by the servlet
     * @param response Response to be sent
     * @throws ServletException If the request for the GET could not be handled
     * @throws IOException If an input or output error is detected when the servlet handles the GET request
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        HttpSession session = request.getSession();

        Object obj = session.getAttribute("user");
        if(!(obj instanceof Customer)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"You are not logged in");
            return;
        }

        Customer customer = (Customer) obj;

        String orderId = request.getParameter("order-id");
        Orders order = OrdersDAO.getOrder(orderId);

        if(order == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No order is associated to this ID");
            return;
        }

        if(order.getOrderStatus() == OrderStatus.WAITING_PAYMENT) {

            // Check if there is a discount associated to the whole order
            Discount discount = order.getDiscount();
            if (discount != null) {
                if(discount.hasExpired()) {
                    System.err.println("Error: The discount has expired. This order is invalid");
                    OrdersDAO.setStatus(order, OrderStatus.CANCELLED); //The customer will be refunded.
                    order.setOrderStatus(OrderStatus.CANCELLED);
                    request.setAttribute("order",order);
                    dispatch("cart?tab=confirmation",request, response);
                    return;
                }
                if (customer.getLoyaltyAccount() != null && customer.getLoyaltyAccount().getAvailableDiscounts() != null && customer.getLoyaltyAccount().getAvailableDiscounts().contains(discount)) {
                    // Use the discount (remove it from the user discounts list)
                    LoyaltyAccountDAO.removeDiscount(customer.getLoyaltyAccount(), discount);
                }
            }
            OrdersDAO.setStatus(order, OrderStatus.PREPARING);
            order.setOrderStatus(OrderStatus.PREPARING);
            request.setAttribute("order",order);

            sendReceiptMail(customer, order);
        }

        dispatch("order", request, response);
    }

    /**
     * Dispatch the given request to the route
     * @param route String representing the route where the request will be dispatched
     * @param request Request dispatched
     * @param response Response associated to the request
     * @throws IOException If sendError failed
     * @throws ServletException If The forward failed
     */
    private void dispatch(String route, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            RequestDispatcher view = request.getRequestDispatcher(route);
            view.forward(request, response);
        } catch(Exception err) {
            // The forward didn't work
            System.err.println(err.getMessage());
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    /**
     * Send a receipt via email
     * @param customer Customer to whom the receipt is sent
     * @param order Order whose receipt is sent
     */
    private void sendReceiptMail(Customer customer, Orders order) {
        Mail mail = new Mail();
        MailManager mailManager = MailManager.getInstance();

        try
        {
            mail.setFromAddress("jeewebproject@gmail.com");
            mail.setToAddress(customer.getEmail());
            mail.setSubject("Receipt of the order n°"+order.getId());
            mail.setBody("Hello,\nYou will find your receipt below:\n\n"+order);
            mail.setDate(new Date(Calendar.getInstance().getTimeInMillis()));
            MailDAO.addMail(mail);
            mailManager.send(mail);
        }
        catch(Exception ignore) {}
    }
}
