package j2ee_project.controller.user.moderator;

import j2ee_project.Application;
import j2ee_project.dto.ModeratorDTO;
import j2ee_project.model.user.Moderator;
import j2ee_project.model.user.Permission;
import j2ee_project.model.user.TypePermission;
import j2ee_project.service.AuthService;
import j2ee_project.service.DTOService;
import j2ee_project.service.HashService;
import j2ee_project.service.user.ModeratorService;
import j2ee_project.service.user.PermissionService;
import j2ee_project.service.user.UserService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static j2ee_project.staticServices.PermissionHelper.getPermission;

@WebServlet("/edit-moderator")
public class EditModeratorController extends HttpServlet {

    private static UserService userService;
    private static ModeratorService moderatorService;

    @Override
    public void init() {
        ApplicationContext context = Application.getContext();
        userService = context.getBean(UserService.class);
        moderatorService = context.getBean(ModeratorService.class);
    }

    /**
     * Get the page to edit a moderator
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
            if (obj instanceof Moderator user
                    && user.isAllowed(getPermission(TypePermission.CAN_MANAGE_MODERATOR))) {
                String moderatorIdStr = request.getParameter("id");
                int moderatorId = -1;

                if (moderatorIdStr != null && !moderatorIdStr.trim().isEmpty()) {
                    try {
                        moderatorId = Integer.parseInt(moderatorIdStr);
                    } catch (Exception ignore) {
                    }
                }

                if (moderatorId <= 0) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Moderator ID must be positive");
                }

                Moderator moderator = moderatorService.getModerator(moderatorId);
                request.setAttribute("moderator", moderator);

                List<String> permissionNameList = new ArrayList<>();
                for (Permission permission : moderator.getPermissions()) {
                    permissionNameList.add(permission.getPermission().getName());
                }
                request.setAttribute("permissionNameList", permissionNameList);

                RequestDispatcher view = request.getRequestDispatcher("WEB-INF/views/dashboard/edit/editModerator.jsp");
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
     * Edit a moderator to the DB
     * @param request Request object received by the servlet
     * @param response Response to be sent
     * @throws ServletException If the request for the GET could not be handled
     * @throws IOException If an input or output error is detected when the servlet handles the GET request
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String moderatorIdStr = request.getParameter("id");
        int moderatorId = -1;

        if (moderatorIdStr != null && !moderatorIdStr.trim().isEmpty()) {
            try {
                moderatorId = Integer.parseInt(moderatorIdStr);
            } catch (Exception ignore) {
            }
        }

        if (moderatorId <= 0) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Moderator ID must be positive");
        }

        Moderator moderator = moderatorService.getModerator(moderatorId);
        String oldPassword = request.getParameter("oldPassword");
        String password = (!request.getParameter("password").isEmpty() ? request.getParameter("password") : request.getParameter("oldPassword"));
        String confirmPassword = (!request.getParameter("confirmPassword").isEmpty() ? request.getParameter("confirmPassword") : request.getParameter("oldPassword"));
        try {
            if (HashService.validatePassword(oldPassword, moderator.getPassword())) {

                ModeratorDTO moderatorDTO = new ModeratorDTO(
                        request.getParameter("firstName"),
                        request.getParameter("lastName"),
                        request.getParameter("email"),
                        password,
                        confirmPassword,
                        (request.getParameter("phoneNumber").isEmpty()) ? null : request.getParameter("phoneNumber")
                );

                for (String permissionStr : request.getParameterValues("permissions")) {
                    TypePermission permission = TypePermission.values()[Integer.parseInt(permissionStr)];
                    moderatorDTO.addPermission(getPermission(permission));
                }

                Map<String, String> inputErrors = DTOService.userDataValidation(moderatorDTO);

                String errorDestination = "WEB-INF/views/dashboard/edit/editModerator.jsp";
                RequestDispatcher dispatcher = null;

                if (inputErrors.isEmpty()) {
                    if (!userService.emailOrPhoneNumberIsInDb(moderatorDTO.getEmail(), moderatorDTO.getPhoneNumber())) {
                        try {

                            if (moderatorDTO.getFirstName()!= null && !moderatorDTO.getFirstName().isEmpty()){
                                moderator.setFirstName(moderatorDTO.getFirstName());
                            }
                            if (moderatorDTO.getLastName()!= null && !moderatorDTO.getLastName().isEmpty()){
                                moderator.setLastName(moderatorDTO.getLastName());
                            }
                            if (moderatorDTO.getPassword()!= null && !moderatorDTO.getPassword().isEmpty()){
                                moderator.setPassword(HashService.generatePasswordHash(moderatorDTO.getPassword()));
                            }
                            if (moderatorDTO.getEmail()!= null && !moderatorDTO.getEmail().isEmpty()){
                                moderator.setEmail(moderatorDTO.getEmail());
                            }
                            if (moderatorDTO.getPhoneNumber()!= null && !moderatorDTO.getPhoneNumber().isEmpty()){
                                moderator.setPhoneNumber(moderatorDTO.getPhoneNumber());
                            }
                            if (moderatorDTO.getPermissions()!= null && !moderatorDTO.getPermissions().isEmpty()) {
                                moderator.setPermissions(moderatorDTO.getPermissions());
                            }

                            userService.updateUser(moderator);
                            response.sendRedirect("dashboard?tab=moderators");
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