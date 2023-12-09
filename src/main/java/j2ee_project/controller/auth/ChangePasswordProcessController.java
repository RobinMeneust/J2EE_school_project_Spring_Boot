package j2ee_project.controller.auth;

import j2ee_project.Application;
import j2ee_project.model.user.ForgottenPassword;
import j2ee_project.model.user.User;
import j2ee_project.service.HashService;
import j2ee_project.service.user.ForgottenPasswordService;
import j2ee_project.service.user.UserService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is a servlet used to process a changing password. It's a controller in the MVC architecture of this project.
 *
 * @author Lucas VELAY
 */
@WebServlet(name = "ChangePasswordController", value = "/change-password-controller")
public class ChangePasswordProcessController extends HttpServlet {
    private static ForgottenPasswordService forgottenPasswordService;
    private static UserService userService;

    @Override
    public void init() {
        ApplicationContext context = Application.getContext();
        forgottenPasswordService = context.getBean(ForgottenPasswordService.class);
        userService = context.getBean(UserService.class);
    }
    /**
     * Redirect to the sender of this request and set an error message since GET queries aren't accepted by this servlet
     * @param request Request object received by the servlet
     * @param response Response to be sent
     * @throws ServletException If the servlet encountered difficulty at some point
     * @throws IOException If an I/O operation has failed or is interrupted
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            RequestDispatcher view = request.getRequestDispatcher("WEB-INF/views/changePassword.jsp");
            view.forward(request,response);
        }catch (Exception err){
            System.out.println(err.getMessage());
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    /**
     * Start the changing password process for a user with the parameters given in the request object. Different errors can be sent to the sender in the request object if a problem occur
     * @param request Request object received by the servlet
     * @param response Response to be sent
     * @throws ServletException If the servlet encountered difficulty at some point
     * @throws IOException If an I/O operation has failed or is interrupted
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String token = request.getParameter("forgottenPasswordToken");
        ForgottenPassword forgottenPassword = forgottenPasswordService.getForgottenPasswordFromToken(token);
        String errorDestinationFP = "WEB-INF/views/forgottenPassword.jsp";
        String errorDestination = "WEB-INF/views/changePassword.jsp";
        String noErrorDestination = "/index.jsp";
        RequestDispatcher dispatcher = null;

        System.out.println(forgottenPassword);

        if(forgottenPassword != null){
            if(password.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,24}$") && password.equals(confirmPassword)){
                System.out.println(forgottenPassword);
                User user = forgottenPasswordService.getUser(forgottenPassword);
                System.out.println(user);
                if(user != null){
                    try {
                        user.setPassword(HashService.generatePasswordHash(password));
                        userService.updateUser(user);
                        forgottenPasswordService.removeForgottenPassword(forgottenPassword);
                        response.sendRedirect(request.getContextPath() + noErrorDestination);
                    }catch (Exception e) {
                        System.out.println(e);
                        request.setAttribute("forgottenPasswordToken", forgottenPassword.getToken());
                        request.setAttribute("errorMessage", "An error occur");
                        dispatcher = request.getRequestDispatcher(errorDestination);
                    }
                }
                else{
                    request.setAttribute("forgottenPasswordToken", forgottenPassword.getToken());
                    request.setAttribute("errorMessage", "An error occur");
                    dispatcher = request.getRequestDispatcher(errorDestination);
                }
            }
            else {
                request.setAttribute("forgottenPasswordToken", forgottenPassword.getToken());
                request.setAttribute("errorMessage", "Password is not valid : it needs letters, numbers, special characters @$!%*#?& and length between 8 and 24.\nPassword and confirm password much match.");
                dispatcher = request.getRequestDispatcher(errorDestination);
            }
        }else{
            request.setAttribute("errorMessage", "An error occur");
            dispatcher = request.getRequestDispatcher(errorDestinationFP);
        }



        if (dispatcher != null) dispatcher.forward(request, response);
    }
}
