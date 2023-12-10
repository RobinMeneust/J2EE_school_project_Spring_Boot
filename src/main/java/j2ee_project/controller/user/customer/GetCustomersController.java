package j2ee_project.controller.user.customer;

import j2ee_project.Application;
import j2ee_project.service.user.CustomerService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.springframework.context.ApplicationContext;

import java.io.IOException;

/**
 * This class is a servlet used to get a list of the customers. It's a controller in the MVC architecture of this project.
 */
@WebServlet("/get-customers")
public class GetCustomersController extends HttpServlet {

    private static CustomerService customerService;

    @Override
    public void init() {
        ApplicationContext context = Application.getContext();
        customerService = context.getBean(CustomerService.class);
    }

    /**
     * Get the customers list
     * @param request Request object received by the servlet
     * @param response Response to be sent
     * @throws ServletException If the request for the GET could not be handled
     * @throws IOException If an input or output error is detected when the servlet handles the GET request
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setAttribute("customers", customerService.getCustomers());
        }catch (Exception err){
            System.err.println(err.getMessage());
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}