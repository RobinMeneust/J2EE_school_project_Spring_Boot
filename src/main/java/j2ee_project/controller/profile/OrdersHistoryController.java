package j2ee_project.controller.profile;

import j2ee_project.dao.user.CustomerDAO;
import j2ee_project.model.user.Customer;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * This class is a servlet used to get the orders' history. It's a controller in the MVC architecture of this project.
 */
@WebServlet("/order-history")
public class OrdersHistoryController extends HttpServlet {

    /**
     * Get the orders' history page
     * @param request Request object received by the servlet
     * @param response Response to be sent
     * @throws ServletException If the request for the GET could not be handled
     * @throws IOException If an input or output error is detected when the servlet handles the GET request
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String customerIdStr = request.getParameter("id");
        int customerId = 0;

        if(customerIdStr != null && !customerIdStr.trim().isEmpty()) {
            try {
                customerId = Integer.parseInt(customerIdStr);
            } catch(Exception ignore) {}
        }

        try{
            Customer customer = CustomerDAO.getCustomer(customerId);
            request.setAttribute("customer", customer);
            RequestDispatcher view = request.getRequestDispatcher("WEB-INF/views/profile.jsp?active-tab=3&has-loyalty-account=1");
            view.forward(request,response);
        }catch (Exception err){
            System.err.println(err.getMessage());
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
