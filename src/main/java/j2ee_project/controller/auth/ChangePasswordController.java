package j2ee_project.controller.auth;

import j2ee_project.dao.user.ForgottenPasswordDAO;
import j2ee_project.model.user.ForgottenPassword;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is a servlet used to change his password. It's a controller in the MVC architecture of this project.
 *
 * @author Lucas VELAY
 */
@WebServlet(value = "/change-password")
public class ChangePasswordController extends HttpServlet {

    /**
     * Get page to change password on the website
     * @param request Request object received by the servlet
     * @param response Response to be sent
     * @throws ServletException If the request for the GET could not be handled
     * @throws IOException If an input or output error is detected when the servlet handles the GET request
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String token = request.getParameter("token");
        ForgottenPassword forgottenPassword = ForgottenPasswordDAO.getForgottenPasswordFromToken(token);
        RequestDispatcher dispatcher;
        String noErrorDestination = "WEB-INF/views/changePassword.jsp";
        String errorDestination = "WEB-INF/views/forgottenPassword.jsp";
        Map<String, String> errorMessages = new HashMap<>();

        if(forgottenPassword != null){
            request.setAttribute("forgottenPasswordToken", forgottenPassword.getToken());
            dispatcher = request.getRequestDispatcher(noErrorDestination);
        }
        else{
            errorMessages.put("noForgottenPassword", "There is no forgotten password linked to this link. You can do a demand here.");
            request.setAttribute("errorMessages", errorMessages);
            dispatcher = request.getRequestDispatcher(errorDestination);
        }
        if (dispatcher != null) dispatcher.forward(request, response);

    }
}
