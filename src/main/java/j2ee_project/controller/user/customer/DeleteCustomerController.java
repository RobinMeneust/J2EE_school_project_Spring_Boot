package j2ee_project.controller.user.customer;

import j2ee_project.Application;
import j2ee_project.service.user.CustomerService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.springframework.context.ApplicationContext;

import java.io.IOException;

/**
 * This class is a servlet used to delete a customer. It's a controller in the MVC architecture of this project.
 */
@WebServlet("/delete-customer")
public class DeleteCustomerController extends HttpServlet {

    private static CustomerService customerService;

    @Override
    public void init() {
        ApplicationContext context = Application.getContext();
        customerService = context.getBean(CustomerService.class);
    }

    /**
     * Remove the customer whose id is given in the param "id"
     * @param request Request object received by the servlet
     * @param response Response to be sent
     * @throws ServletException If the request for the GET could not be handled
     * @throws IOException If an input or output error is detected when the servlet handles the GET request
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String customerIdStr = request.getParameter("id");
        int customerId = -1;

        if(customerIdStr != null && !customerIdStr.trim().isEmpty()) {
            try {
                customerId = Integer.parseInt(customerIdStr);
            } catch(Exception ignore) {}
        }

        if(customerId<=0) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Customer ID must be positive");
        }
        customerService.deleteCustomer(customerId);
        try {
            response.sendRedirect("dashboard?tab=customers");
        }catch (Exception err){
            System.err.println(err.getMessage());
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

}