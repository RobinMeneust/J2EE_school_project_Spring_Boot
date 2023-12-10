package j2ee_project.controller.user.customer;

import j2ee_project.Application;
import j2ee_project.dto.AddressDTO;
import j2ee_project.dto.CustomerDTO;
import j2ee_project.model.Address;
import j2ee_project.model.user.Moderator;
import j2ee_project.model.user.TypePermission;
import j2ee_project.service.AuthService;
import j2ee_project.service.DTOService;
import j2ee_project.service.user.UserService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.util.Map;

import static j2ee_project.staticServices.PermissionHelper.getPermission;

/**
 * This class is a servlet used to add a customer. It's a controller in the MVC architecture of this project.
 */
@WebServlet("/add-customer")
public class AddCustomerController extends HttpServlet {

    private static AuthService authService;
    private static UserService userService;

    /**
     * Initialize the services used by the class
     */
    @Override
    public void init() {
        ApplicationContext context = Application.getContext();
        authService = context.getBean(AuthService.class);
        userService = context.getBean(UserService.class);
    }

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
            HttpSession session = request.getSession();
            Object obj = session.getAttribute("user");
            if (obj instanceof Moderator moderator
                    && moderator.isAllowed(getPermission(TypePermission.CAN_MANAGE_CUSTOMER))) {
                RequestDispatcher view = request.getRequestDispatcher("WEB-INF/views/dashboard/add/addCustomer.jsp");
                view.forward(request, response);
            } else {
                response.sendRedirect("dashboard");
            }
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
        CustomerDTO customerDTO = new CustomerDTO(
                request.getParameter("firstName"),
                request.getParameter("lastName"),
                request.getParameter("email"),
                request.getParameter("password"),
                request.getParameter("confirmPassword"),
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

        String errorDestination = "WEB-INF/views/dashboard/add/addCustomer.jsp";
        RequestDispatcher dispatcher = null;

        if(inputErrors.isEmpty()){
            if (!userService.emailOrPhoneNumberIsInDb(null, customerDTO.getEmail(), customerDTO.getPhoneNumber())){
                try {
                    authService.registerCustomer(customerDTO);
                    response.sendRedirect("dashboard?tab=customers");
                } catch(Exception exception){
                    System.err.println(exception.getMessage());
                    request.setAttribute("RegisterProcessError","Error during register process");
                    dispatcher = request.getRequestDispatcher(errorDestination);
                    dispatcher.include(request, response);
                }
            }
            else{
                request.setAttribute("emailOrPhoneNumberInDbError","Email or phone number already used");
                dispatcher = request.getRequestDispatcher(errorDestination);
                dispatcher.include(request, response);
            }
        }
        else{
            request.setAttribute("InputError", inputErrors);
            dispatcher = request.getRequestDispatcher(errorDestination);
            dispatcher.include(request, response);
        }

        if (dispatcher != null) doGet(request, response);

    }
}