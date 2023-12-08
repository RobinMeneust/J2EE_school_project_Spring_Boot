package j2ee_project.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

import j2ee_project.dao.MailDAO;
import j2ee_project.model.Mail;
import j2ee_project.service.MailManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * This class is a servlet used to send mails. It's a controller in the MVC architecture of this project.
 *
 * @author Robin MENEUST
 */
@WebServlet("/send-mail")
public class MailSenderController extends HttpServlet
{
    /**
     * Send a mail with the parameters given in the request object. An error is sent to the sender in the request object if the mail could not be sent
     * @param request Request object received by the servlet
     * @param response Response to be sent
     * @throws ServletException If the request for the POST could not be handled
     * @throws IOException If an input or output error is detected when the servlet handles the POST request
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String URLRedirect = getServletContext().getContextPath();
        Mail mail = new Mail();
        MailManager mailManager = MailManager.getInstance();

        BufferedReader reader = request.getReader();
        JSONTokener tokener = new JSONTokener(reader);
        JSONObject paramsObject = new JSONObject(tokener);

        try
        {
            mail.setFromAddress("jeewebproject@gmail.com");
            mail.setToAddress(paramsObject.getString("to"));
            mail.setSubject(paramsObject.getString("subject"));
            mail.setBody(paramsObject.getString("body"));

            try {
                if(paramsObject.has("date")) {
                    JSONObject dateObj = (JSONObject) paramsObject.get("date");

                    if(dateObj.keySet().contains("day") && dateObj.keySet().contains("month") && dateObj.keySet().contains("year")) {
                        Date date = new Date(new GregorianCalendar(dateObj.getInt("year"), dateObj.getInt("month")-1, dateObj.getInt("day")).getTimeInMillis());
                        mail.setDate(date);
                    }
                }
            } catch(Exception ignore) {}

            // Set the date to current time if it's null
            if(mail.getDate() == null) {
                mail.setDate(new Date(Calendar.getInstance().getTimeInMillis()));
            }

            MailDAO.addMail(mail);
            mailManager.send(mail);
        }
        catch(Exception e) {
            URLRedirect += "?error=mailCouldNotBeSent";
        }
        response.sendRedirect(URLRedirect);
    }

}
