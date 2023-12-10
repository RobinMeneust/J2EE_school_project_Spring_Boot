package j2ee_project.controller.user.moderator;

import j2ee_project.Application;
import j2ee_project.model.user.Moderator;
import j2ee_project.service.user.ModeratorService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.util.List;

/**
 * This class is a servlet used to get a list of the moderators. It's a controller in the MVC architecture of this project.
 */
@WebServlet("/get-moderators")
public class GetModeratorController extends HttpServlet {
    private static ModeratorService moderatorService;

    /**
     * Initialize the services used by the class
     */
    @Override
    public void init() {
        ApplicationContext context = Application.getContext();
        moderatorService = context.getBean(ModeratorService.class);
    }

    /**
     * Get the moderators list
     * @param request Request object received by the servlet
     * @param response Response to be sent
     * @throws ServletException If the request for the GET could not be handled
     * @throws IOException If an input or output error is detected when the servlet handles the GET request
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Moderator> moderators = moderatorService.getModerators();
            request.setAttribute("moderators", moderators);
        }catch (Exception err){
            System.err.println(err.getMessage());
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}