package j2ee_project.controller.user.customer;

import j2ee_project.dao.user.CustomerDAO;
import j2ee_project.model.Address;
import j2ee_project.model.user.Customer;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

/**
 * This class is a servlet used to add a customer. It's a controller in the MVC architecture of this project.
 */
@WebServlet("/add-customer")
public class AddCustomerController extends HttpServlet {
    /**
     * Get the page to add a customer
     * @param request Request object received by the servlet
     * @param response Response to be sent
     * @throws ServletException If the request for the GET could not be handled
     * @throws IOException If an input or output error is detected when the servlet handles the GET request
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            RequestDispatcher view = request.getRequestDispatcher("WEB-INF/views/dashboard/add/addCustomer.jsp");
            view.forward(request,response);
        }catch (Exception err){
            System.err.println(err.getMessage());
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    /**
     * Add a customer to the DB
     * @param request Request object received by the servlet
     * @param response Response to be sent
     * @throws ServletException If the request for the GET could not be handled
     * @throws IOException If an input or output error is detected when the servlet handles the GET request
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Customer customer = new Customer();

        customer.setLastName(request.getParameter("last-name"));
        customer.setFirstName(request.getParameter("first-name"));
        customer.setPassword(request.getParameter("password"));

        Address address = new Address();
        address.setStreetAddress(request.getParameter("street"));
        address.setPostalCode(request.getParameter("postal-code"));
        address.setCity(request.getParameter("city"));
        address.setCountry(request.getParameter("country"));
        customer.setAddress(address);

        customer.setEmail(request.getParameter("email"));
        customer.setPhoneNumber((request.getParameter("phone-number").isEmpty()) ? null : request.getParameter("phone-number"));

        CustomerDAO.addCustomer(customer);

        try {
            response.sendRedirect("dashboard?tab=customers");
        }catch (Exception err){
            System.err.println(err.getMessage());
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}