package j2ee_project.controller.user.moderator;

import j2ee_project.dao.user.ModeratorDAO;
import j2ee_project.dao.user.PermissionDAO;
import j2ee_project.model.user.Moderator;
import j2ee_project.model.user.Permission;
import j2ee_project.model.user.TypePermission;
import j2ee_project.service.HashService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * This class is a servlet used to add a moderator. It's a controller in the MVC architecture of this project.
 */
@WebServlet("/add-moderator")
public class AddModeratorController extends HttpServlet {
    /**
     * Get the page to add a moderator
     * @param request Request object received by the servlet
     * @param response Response to be sent
     * @throws ServletException If the request for the GET could not be handled
     * @throws IOException If an input or output error is detected when the servlet handles the GET request
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            RequestDispatcher view = request.getRequestDispatcher("WEB-INF/views/dashboard/add/addModerator.jsp");
            view.forward(request,response);
        }catch (Exception err){
            System.err.println(err.getMessage());
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    /**
     * Add a moderator to the DB
     * @param request Request object received by the servlet
     * @param response Response to be sent
     * @throws ServletException If the request for the GET could not be handled
     * @throws IOException If an input or output error is detected when the servlet handles the GET request
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Moderator moderator = new Moderator();

        moderator.setLastName(request.getParameter("last-name"));
        moderator.setFirstName(request.getParameter("first-name"));
        String password = request.getParameter("password");
        try {
            moderator.setPassword(HashService.generatePasswordHash(password));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }

        for (String permissionStr : request.getParameterValues("permissions")){
            TypePermission permission = TypePermission.values()[Integer.parseInt(permissionStr)];
            moderator.addPermission(PermissionDAO.getPermission(permission));
        }

        moderator.setEmail(request.getParameter("email"));
        moderator.setPhoneNumber((request.getParameter("phone-number").isEmpty()) ? null : request.getParameter("phone-number"));

        ModeratorDAO.addModerator(moderator);

        try {
            response.sendRedirect("dashboard?tab=moderators");
        }catch (Exception err){
            System.err.println(err.getMessage());
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}