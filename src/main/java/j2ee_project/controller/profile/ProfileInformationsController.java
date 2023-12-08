package j2ee_project.controller.profile;

import j2ee_project.dao.user.CustomerDAO;
import j2ee_project.model.Address;
import j2ee_project.model.user.Customer;
import j2ee_project.service.HashService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * This class is a servlet used to get or edit the profile information page. It's a controller in the MVC architecture of this project.
 */
@WebServlet("/profile-informations")
public class ProfileInformationsController extends HttpServlet {
    /**
     * Get the profile information page
     * @param request Request object received by the servlet
     * @param response Response to be sent
     * @throws ServletException If the request for the GET could not be handled
     * @throws IOException If an input or output error is detected when the servlet handles the GET request
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String customerIdStr = request.getParameter("customerId");
        int customerId = 1;
        if(customerIdStr != null && !customerIdStr.trim().isEmpty()) {
            try {
                customerId = Integer.parseInt(customerIdStr);
            } catch(Exception ignore) {}
        }

        try {
            Customer customer = CustomerDAO.getCustomer(customerId);
            request.setAttribute("customer", customer);
            RequestDispatcher view = request.getRequestDispatcher("WEB-INF/views/profile.jsp?active-tab=1&has-loyalty-account=1");
            view.forward(request, response);
        } catch(Exception err) {
            // The forward didn't work
            System.err.println(err.getMessage());
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }

    /**
     * Edit the user information
     * @param request Request object received by the servlet
     * @param response Response to be sent
     * @throws ServletException If the request for the GET could not be handled
     * @throws IOException If an input or output error is detected when the servlet handles the GET request
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userIdStr = request.getParameter("id");
        String userAddressIdStr = request.getParameter("addressId");
        int userId=0;
        int userAdressId=0;

        if(userIdStr != null && !userIdStr.trim().isEmpty()) {
            try {
                userId = Integer.parseInt(userIdStr);
            } catch(Exception ignore) {}
        }
        if(userAddressIdStr != null && !userAddressIdStr.trim().isEmpty()) {
            try {
                userAdressId = Integer.parseInt(userAddressIdStr);
            } catch(Exception ignore) {}
        }

        Customer customer = new Customer();

        customer.setId(userId);
        if(request.getParameter("userPassword")!=null){
            String passwordNotHashed = request.getParameter("userPassword");
            String hashedPassword;
            try {
                hashedPassword = HashService.generatePasswordHash(passwordNotHashed);
            } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                throw new RuntimeException(e);
            }
            customer.setPassword(hashedPassword);
        }
        customer.setFirstName(request.getParameter("userFirstName"));
        customer.setLastName(request.getParameter("userLastName"));
        customer.setEmail(request.getParameter("userEmail"));
        customer.setPhoneNumber(request.getParameter("userPhoneNumber"));

        Address address = new Address();
        address.setStreetAddress(request.getParameter("userAddress"));
        address.setPostalCode(request.getParameter("userPostalCode"));
        address.setCity(request.getParameter("userCity"));
        address.setCountry(request.getParameter("userCountry"));
        address.setId(userAdressId);

        customer.setAddress(address);
        CustomerDAO.modifyCustomer(customer);

        try {
            Customer customert = CustomerDAO.getCustomer(userId);
            request.setAttribute("customer", customert);
            RequestDispatcher view = request.getRequestDispatcher("WEB-INF/views/profile.jsp?active-tab=1&has-loyalty-account=1");
            view.forward(request, response);
        } catch(Exception err) {
            // The forward didn't work
            System.err.println(err.getMessage());
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
