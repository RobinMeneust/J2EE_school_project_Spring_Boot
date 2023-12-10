package j2ee_project.controller.dashboard;

import j2ee_project.Application;
import j2ee_project.model.user.Moderator;
import j2ee_project.model.user.TypePermission;
import j2ee_project.service.user.PermissionService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.springframework.context.ApplicationContext;

import java.io.IOException;


/**
 * This class is a servlet used to get the dashboard page. It's a controller in the MVC architecture of this project.
 */
@WebServlet("/dashboard")
public class DashboardController extends HttpServlet {

    private static PermissionService permissionService;

    /**
     * Initialize the services used by the class
     */
    @Override
    public void init() {
        ApplicationContext context = Application.getContext();
        permissionService = context.getBean(PermissionService.class);
    }

    /**
     * Get the categories list
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
            if(obj instanceof Moderator moderator) {
                if (moderator.isAllowed(permissionService.getPermission(TypePermission.CAN_MANAGE_CUSTOMER))){
                    RequestDispatcher dispatcherCustomers = getServletContext().getRequestDispatcher("/get-customers");
                    dispatcherCustomers.include(request, response);
                }
                if (moderator.isAllowed(permissionService.getPermission(TypePermission.CAN_MANAGE_MODERATOR))) {
                    RequestDispatcher dispatcherModerators = getServletContext().getRequestDispatcher("/get-moderators");
                    dispatcherModerators.include(request, response);
                }
                if (moderator.isAllowed(permissionService.getPermission(TypePermission.CAN_MANAGE_PRODUCT))) {
                    RequestDispatcher dispatcherProducts = getServletContext().getRequestDispatcher("/get-products");
                    dispatcherProducts.include(request, response);
                }
                if (moderator.isAllowed(permissionService.getPermission(TypePermission.CAN_MANAGE_CATEGORY))) {
                    RequestDispatcher dispatcherCategories = getServletContext().getRequestDispatcher("/get-categories");
                    dispatcherCategories.include(request, response);
                }
                if (moderator.isAllowed(permissionService.getPermission(TypePermission.CAN_MANAGE_DISCOUNT))) {
                    RequestDispatcher dispatcherDiscounts = getServletContext().getRequestDispatcher("/get-discounts");
                    dispatcherDiscounts.include(request, response);
                }
                if (moderator.isAllowed(permissionService.getPermission(TypePermission.CAN_MANAGE_LOYALTY))) {

                }
                RequestDispatcher view = request.getRequestDispatcher("WEB-INF/views/dashboard/dashboard.jsp");
                view.forward(request,response);
            } else {
                response.sendRedirect("");
            }
        }catch (Exception err){
            System.err.println(err.getMessage());
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}