package j2ee_project.controller.order;

import j2ee_project.Application;
import j2ee_project.model.user.Customer;
import j2ee_project.service.loyalty.LoyaltyAccountService;
import j2ee_project.service.loyalty.LoyaltyLevelService;
import j2ee_project.service.user.CustomerService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.ApplicationContext;


import java.io.IOException;

/**
 * This class is a servlet used to get the cart page. It's a controller in the MVC architecture of this project.
 *
 * @author Robin MENEUST
 */
@WebServlet("/cart")
public class GetCartPageController extends HttpServlet
{
    private static CustomerService customerService;

    /**
     * Initialize the services used by the class
     */
    @Override
    public void init() {
        ApplicationContext context = Application.getContext();
        customerService = context.getBean(CustomerService.class);
    }

    /**
     * Get a page to edit the current customer cart (it can be either the session cart if the user is not logged in, or the authenticated customer cart if he is)
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
            // Refresh user's session variable
            Customer customer = (Customer) obj;
            session.setAttribute("user", customerService.getCustomer(customer.getId()));
        }

        try {
            RequestDispatcher view = request.getRequestDispatcher("WEB-INF/views/cart.jsp");
            view.forward(request, response);
        } catch(Exception err) {
            // The forward didn't work
            System.err.println(err.getMessage());
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doGet(request,response);
    }
}
