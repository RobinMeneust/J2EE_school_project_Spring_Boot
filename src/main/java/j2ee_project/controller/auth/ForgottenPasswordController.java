package j2ee_project.controller.auth;

import j2ee_project.dao.MailDAO;
import j2ee_project.dao.user.ForgottenPasswordDAO;
import j2ee_project.dao.user.UserDAO;
import j2ee_project.model.Mail;
import j2ee_project.model.user.ForgottenPassword;
import j2ee_project.model.user.User;
import j2ee_project.service.HashService;
import j2ee_project.service.MailManager;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is a servlet used to start the changing password process if a user forgot his password. It's a controller in the MVC architecture of this project.
 *
 * @author Lucas VELAY
 */
@WebServlet(name = "ForgottenPasswordController", value = "/forgotten-password-controller")
public class ForgottenPasswordController extends HttpServlet {

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
            RequestDispatcher view = request.getRequestDispatcher("WEB-INF/views/forgottenPassword.jsp");
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

        String email = request.getParameter("email");
        String errorDestination = "WEB-INF/views/forgottenPassword.jsp";
        String noErrorDestination = "/index.jsp";
        RequestDispatcher dispatcher = null;

        Map<String, String> errorMessages = new HashMap<>();

        if(email.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")){
            User user = UserDAO.getUserFromEmail(email);
            if(user != null && user.getForgottenPassword() == null){
                String token;
                do{
                    token = HashService.generateToken(20);
                }while(ForgottenPasswordDAO.getForgottenPasswordFromToken(token) != null);

                String link = "http://localhost:8082" + request.getContextPath() + "/change-password?token=" + token;
                sendForgottenPasswordEmail(email, link);
                ForgottenPassword forgottenPassword = new ForgottenPassword(user, token);
                ForgottenPasswordDAO.addForgottenPassword(forgottenPassword);
                response.sendRedirect(request.getContextPath() + noErrorDestination);
            }
            else{
                request.setAttribute("errorMessage", "You're not registered or you have already ask to change your password. Check your mail box.");
                dispatcher = request.getRequestDispatcher(errorDestination);
            }
        }
        else {
            request.setAttribute("errorMessage", "Email is not valid.");
            dispatcher = request.getRequestDispatcher(errorDestination);
        }

        if (dispatcher != null) dispatcher.forward(request, response);
    }

    private void sendForgottenPasswordEmail(String email, String link){
        Mail mail = new Mail();
        MailManager mailManager = MailManager.getInstance();

        try
        {
            mail.setFromAddress("jeewebproject@gmail.com");
            mail.setToAddress(email);
            mail.setSubject("Forgotten password on Boarder Games website");
            mail.setBody("Hello,\n\nThere is the link to change your password.\n\n"+link);

            // Set the date to current time if it's null
            if(mail.getDate() == null) {
                mail.setDate(new Date(Calendar.getInstance().getTimeInMillis()));
            }

            MailDAO.addMail(mail);
            mailManager.send(mail);
        }
        catch(Exception ignore) {}
    }

}
