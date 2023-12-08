package j2ee_project.controller.auth;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

/**
 * This class is a servlet used to log out a user. It's a controller in the MVC architecture of this project.
 *
 * @author Lucas VELAY
 */
@WebServlet(name = "LogOutController", value = "/logout-controller")
public class LogOutController extends HttpServlet {

    /**
     * Log out the current user.
     *
     * @param request Request object received by the servlet
     * @param response Response to be sent
     * @throws ServletException If the servlet encountered difficulty at some point
     * @throws IOException If an I/O operation has failed or is interrupted
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        response.sendRedirect(request.getContextPath() + "/index.jsp");
    }
}