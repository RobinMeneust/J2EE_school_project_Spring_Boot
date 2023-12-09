package j2ee_project.controller.auth;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

/**
 * This class is a servlet used to get the login page. It's a controller in the MVC architecture of this project.
 *
 * @author Lucas VELAY
 */
@WebServlet(value = "/login")
public class GetLoginPageController extends HttpServlet {

    /**
     * Get a page to log in on the website
     * @param request Request object received by the servlet
     * @param response Response to be sent
     * @throws ServletException If the request for the GET could not be handled
     * @throws IOException If an input or output error is detected when the servlet handles the GET request
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try {
            RequestDispatcher view = request.getRequestDispatcher("WEB-INF/views/login.jsp");
            view.forward(request, response);
        } catch(Exception err) {
            // The forward didn't work
            System.err.println(err.getMessage());
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}