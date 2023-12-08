package j2ee_project.controller;

import j2ee_project.dao.MailDAO;
import j2ee_project.dto.ContactDTO;
import j2ee_project.model.Mail;
import j2ee_project.service.DTOService;
import j2ee_project.service.MailManager;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;
import java.util.Map;

/**
 * This class is a servlet used to send contact message. It's a controller in the MVC architecture of this project.
 *
 * @author Lucas VELAY
 */
@WebServlet(name = "ContactController", value = "/contact-controller")
public class ContactController extends HttpServlet {

    /**
     * Send a contact mail to our mailbox and register the mail in the database Different errors can be sent to the sender in the request object if a problem occur
     * @param request Request object received by the servlet
     * @param response Response to be sent
     * @throws ServletException If the servlet encountered difficulty at some point
     * @throws IOException If an I/O operation has failed or is interrupted
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ContactDTO contact = new ContactDTO(
                request.getParameter("firstName"),
                request.getParameter("lastName"),
                request.getParameter("email"),
                request.getParameter("subject"),
                request.getParameter("bodyMessage")
        );
        Map<String, String> inputErrors = DTOService.contactDataValidation(contact);
        String errorDestination = "WEB-INF/views/contact.jsp";
        String noErrorDestination = "WEB-INF/views/contact.jsp";
        RequestDispatcher dispatcher = null;
        if(inputErrors.isEmpty()){
            try {
                sendContactMail(contact);
                request.setAttribute("SuccessSending","Success sending");
                dispatcher = request.getRequestDispatcher(noErrorDestination);
            } catch(Exception exception){
                System.err.println(exception.getMessage());
                request.setAttribute("ContactSendingError","Error during sending");
                dispatcher = request.getRequestDispatcher(errorDestination);
            }
        }
        else{
            request.setAttribute("InputError", inputErrors);
            dispatcher = request.getRequestDispatcher(errorDestination);
        }

        if (dispatcher != null) dispatcher.forward(request, response);

    }

    /**
     * Send a mail with the form contact data
     *
     * @param contactDTO Data Transfer Object which contain the data of the mail to send
     */
    private void sendContactMail(ContactDTO contactDTO){
        Mail mail = new Mail();
        MailManager mailManager = MailManager.getInstance();

        try
        {
            mail.setFromAddress("jeewebproject@gmail.com");
            mail.setToAddress("jeewebproject@gmail.com");
            mail.setSubject("[Contact Form] - " + contactDTO.getSubject());
            mail.setBody(
                    "First name : " + contactDTO.getFirstName() +
                    "\nLast name : " + contactDTO.getLastName() +
                    "\nEmail : " + contactDTO.getEmail() +
                    "\nSubject : " + contactDTO.getSubject() +
                    "\nMessage : \n" + contactDTO.getBodyMessage());

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