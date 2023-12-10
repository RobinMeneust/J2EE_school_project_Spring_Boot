package j2ee_project.controller.user.customer;

import j2ee_project.Application;
import j2ee_project.dto.AddressDTO;
import j2ee_project.dto.CustomerDTO;
import j2ee_project.model.Address;
import j2ee_project.model.user.Customer;
import j2ee_project.model.user.Moderator;
import j2ee_project.model.user.TypePermission;
import j2ee_project.service.AuthService;
import j2ee_project.service.DTOService;
import j2ee_project.service.HashService;
import j2ee_project.service.user.CustomerService;
import j2ee_project.service.user.UserService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.util.Map;

import static j2ee_project.staticServices.PermissionHelper.getPermission;

@WebServlet("/edit-customer")
public class EditCustomerController extends HttpServlet {

    private static CustomerService customerService;
    private static UserService userService;

    @Override
    public void init() {
        ApplicationContext context = Application.getContext();
        customerService = context.getBean(CustomerService.class);
        userService = context.getBean(UserService.class);
    }

    /**
     * Get the page to edit a customer
     * @param request Request object received by the servlet
     * @param response Response to be sent
     * @throws ServletException If the request for the GET could not be handled
     * @throws IOException If an input or output error is detected when the servlet handles the GET request.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            Object obj = session.getAttribute("user");
            if (obj instanceof Moderator user
                    && user.isAllowed(getPermission(TypePermission.CAN_MANAGE_CUSTOMER))) {
                String customerIdStr = request.getParameter("id");
                int customerId = -1;

                if (customerIdStr != null && !customerIdStr.trim().isEmpty()) {
                    try {
                        customerId = Integer.parseInt(customerIdStr);
                    } catch (Exception ignore) {
                    }
                }

                if (customerId <= 0) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Customer ID must be positive");
                }

                Customer customer = customerService.getCustomer(customerId);
                request.setAttribute("customerToEdit", customer);

                RequestDispatcher view = request.getRequestDispatcher("WEB-INF/views/dashboard/edit/editCustomer.jsp");
                view.forward(request, response);
            } else {
                response.sendRedirect("dashboard");
            }
        } catch(Exception err) {
            System.err.println(err.getMessage());
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    /**
     * Edit a customer to the DB
     * @param request Request object received by the servlet
     * @param response Response to be sent
     * @throws ServletException If the request for the GET could not be handled
     * @throws IOException If an input or output error is detected when the servlet handles the GET request.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String customerIdStr = request.getParameter("id");
        int customerId = -1;

        if (customerIdStr != null && !customerIdStr.trim().isEmpty()) {
            try {
                customerId = Integer.parseInt(customerIdStr);
            } catch (Exception ignore) {
            }
        }

        if (customerId <= 0) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Customer ID must be positive");
        }

        Customer customer = customerService.getCustomer(customerId);
        String oldPassword = request.getParameter("oldPassword");
        String password = (!request.getParameter("password").isEmpty() ? request.getParameter("password") : request.getParameter("oldPassword"));
        String confirmPassword = (!request.getParameter("confirmPassword").isEmpty() ? request.getParameter("confirmPassword") : request.getParameter("oldPassword"));
        try {
            if (HashService.validatePassword(oldPassword, customer.getPassword())) {

                CustomerDTO customerDTO = new CustomerDTO(
                        request.getParameter("firstName"),
                        request.getParameter("lastName"),
                        request.getParameter("email"),
                        password,
                        confirmPassword,
                        (request.getParameter("phoneNumber").isEmpty()) ? null : request.getParameter("phoneNumber")
                );

                AddressDTO addressDTO = new AddressDTO(
                        request.getParameter("street"),
                        request.getParameter("postalCode"),
                        request.getParameter("city"),
                        request.getParameter("country")
                );
                customerDTO.setAddress(new Address(addressDTO));

                Map<String, String> inputErrors = DTOService.userDataValidation(customerDTO);

                String errorDestination = "WEB-INF/views/dashboard/edit/editCustomer.jsp";
                RequestDispatcher dispatcher = null;

                if (inputErrors.isEmpty()) {
                    if (!userService.emailOrPhoneNumberIsInDb(customerId, customerDTO.getEmail(), customerDTO.getPhoneNumber())) {
                        try {

                            if (customerDTO.getFirstName()!= null && !customerDTO.getFirstName().isEmpty()){
                                customer.setFirstName(customerDTO.getFirstName());
                            }
                            if (customerDTO.getLastName()!= null && !customerDTO.getLastName().isEmpty()){
                                customer.setLastName(customerDTO.getLastName());
                            }
                            if (customerDTO.getPassword()!= null && !customerDTO.getPassword().isEmpty()){
                                customer.setPassword(HashService.generatePasswordHash(customerDTO.getPassword()));
                            }
                            if (customerDTO.getAddress()!= null){
                                customer.setAddress(customerDTO.getAddress());
                            }
                            if (customerDTO.getEmail()!= null && !customerDTO.getEmail().isEmpty()){
                                customer.setEmail(customerDTO.getEmail());
                            }
                            if (customerDTO.getPhoneNumber()!= null && !customerDTO.getPhoneNumber().isEmpty()){
                                customer.setPhoneNumber(customerDTO.getPhoneNumber());
                            }

                            customerService.modifyCustomer(customer);
                            response.sendRedirect("dashboard?tab=customers");
                        } catch (Exception exception) {
                            System.err.println(exception.getMessage());
                            request.setAttribute("ModificationProcessError", "Error during modification process");
                            dispatcher = request.getRequestDispatcher(errorDestination);
                            dispatcher.include(request, response);
                        }
                    } else {
                        request.setAttribute("emailOrPhoneNumberInDbError", "Email or phone number already used");
                        dispatcher = request.getRequestDispatcher(errorDestination);
                        dispatcher.include(request, response);
                    }
                } else {
                    request.setAttribute("InputError", inputErrors);
                    dispatcher = request.getRequestDispatcher(errorDestination);
                    dispatcher.include(request, response);
                }

                if (dispatcher != null) doGet(request, response);
            }
        }catch (Exception e) {
            System.err.println(e.getMessage());
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}