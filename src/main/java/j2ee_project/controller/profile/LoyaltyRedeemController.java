package j2ee_project.controller.profile;

import j2ee_project.Application;
import j2ee_project.model.loyalty.LoyaltyAccount;
import j2ee_project.model.loyalty.LoyaltyLevel;
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
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.util.List;

/**
 * This class is a servlet used to redeem a loyalty level. It's a controller in the MVC architecture of this project.
 */
@WebServlet("/loyalty-redeem")
public class LoyaltyRedeemController extends HttpServlet {

    private static LoyaltyLevelService loyaltyLevelService;
    private static CustomerService customerService;
    private static LoyaltyAccountService loyaltyAccountService;

    /**
     * Initialize the services used by the class
     */
    @Override
    public void init() {
        ApplicationContext context = Application.getContext();
        loyaltyLevelService = context.getBean(LoyaltyLevelService.class);
        customerService = context.getBean(CustomerService.class);
        loyaltyAccountService = context.getBean(LoyaltyAccountService.class);
    }

    /**
     * get the loyalty account of the customer as well as loyalty levels
     * @param request Request object received by the servlet
     * @param response Response to be sent
     * @throws ServletException If the request for the GET could not be handled
     * @throws IOException If an input or output error is detected when the servlet handles the GET request
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String customerIdStr = request.getParameter("customerId");
        int customerId = -1;

        if (customerIdStr != null && !customerIdStr.trim().isEmpty()) {
            try {
                customerId = Integer.parseInt(customerIdStr);
            } catch (Exception ignore) {
            }
        }

        Customer customer = customerService.getCustomer(customerId);
        List<LoyaltyLevel> loyaltyLevels = null;
        LoyaltyAccount loyaltyAccount = customer.getLoyaltyAccount();

        if (loyaltyAccount != null){
            loyaltyLevels = loyaltyLevelService.getLoyaltyLevels();
        }
        try {
                loyaltyLevels = loyaltyLevelService.getLoyaltyLevels();

                request.setAttribute("customer", customer);
                request.setAttribute("loyaltyAccount", loyaltyAccount);
                request.setAttribute("loyaltyLevels", loyaltyLevels);
                RequestDispatcher view = request.getRequestDispatcher("WEB-INF/views/profile.jsp?active-tab=2");
                view.forward(request, response);
            } catch (Exception err) {
                // The forward didn't work
                System.err.println(err.getMessage());
                response.sendError(HttpServletResponse.SC_NOT_FOUND);

        }
    }

    /**
     * Claim a loyalty level
     * @param request Request object received by the servlet
     * @param response Response to be sent
     * @throws ServletException If the request for the POST could not be handled
     * @throws IOException If an input or output error is detected when the servlet handles the POST request
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String loyaltyAccountIdStr = request.getParameter("loyaltyAccountId");
        String loyaltyLevelIdStr = request.getParameter("loyaltyLevelId");

        int loyaltyAccountId = -1;
        int loyaltyLevelId = -1;

        if(loyaltyAccountIdStr != null && !loyaltyAccountIdStr.trim().isEmpty()) {
            try {
                loyaltyAccountId = Integer.parseInt(loyaltyAccountIdStr);
            } catch(Exception ignore) {}
        }

        if(loyaltyLevelIdStr != null && !loyaltyLevelIdStr.trim().isEmpty()) {
            try {
                loyaltyLevelId = Integer.parseInt(loyaltyLevelIdStr);
            } catch(Exception ignore) {}
        }

        try{
            loyaltyAccountService.createLevelUsed(loyaltyAccountId,loyaltyLevelId);
            doGet(request,response);
        }catch (Exception err) {
            // The forward didn't work
            System.err.println(err.getMessage());
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
