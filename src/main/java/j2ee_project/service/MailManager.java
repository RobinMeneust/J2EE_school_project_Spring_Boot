package j2ee_project.service;

import j2ee_project.model.Mail;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.InputStream;
import java.sql.Date;
import java.util.Calendar;
import java.util.Properties;

/**
 * Class that manage the mail server credentials and session.
 * It's used to send mails.
 * It's a singleton.
 *
 * @author Robin Meneust
 */
public class MailManager {
    /**
     * Unique instance of this class (lazy instantiation).
     */
    private static MailManager instance;

    /**
     * Jakarta Mail session
     */
    private final Session session;

    /**
     * Get this class instance.
     * @return This class instance
     */
    public static MailManager getInstance() {
        if(instance == null) {
            instance = new MailManager();
        }
        return instance;
    }

    /**
     * Test if a String is null or empty or with only blank spaces
     * @param s String tested
     * @return True if the String is empty and false otherwise
     */
    public static boolean isEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }

    /**
     * Constructor of this class. Create the Jakarta Mail session
     */
    private MailManager() {
        String host = "smtp.gmail.com";

        // Setup mail server
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        session = Session.getInstance(properties, new jakarta.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {
                // Read the credentials file

                String credentialsFilePath = "/credentials.json";
                InputStream inputStream = MailManager.class.getResourceAsStream(credentialsFilePath);
                if (inputStream == null) {
                    throw new NullPointerException("Cannot find the credentials file");
                }
                JSONTokener tokener = new JSONTokener(inputStream);
                JSONObject credentials = new JSONObject(tokener);

                // Check if the credentials object contains the required fields

                if(!credentials.has("gmail") || !credentials.getJSONObject("gmail").has("username") || !credentials.getJSONObject("gmail").has("password")) {
                    throw new RuntimeException("Invalid credentials");
                }
                JSONObject gmailCredentials = credentials.getJSONObject("gmail");
                return new PasswordAuthentication(gmailCredentials.getString("username"), gmailCredentials.getString("password"));
            }
        });

        // Used to debug SMTP issues
        session.setDebug(true);
    }


    /**
     * Send a mail by using the server defined in the session associated to this class
     * @param mail Object containing the data of the mail to be sent
     * @throws SendMailFailureException If the mail could not be sent (invalid session, invalid parameters...)
     */
    public void send(Mail mail) throws SendMailFailureException {
        // Recipient's email ID needs to be mentioned.
        if(isEmpty(mail.getToAddress()) || isEmpty(mail.getFromAddress())) {
            throw new SendMailFailureException("'From' or 'To' email addresses are empty");
        }

        // Set the date to current time if it's null
        if(mail.getDate() == null) {
            mail.setDate(new Date(Calendar.getInstance().getTimeInMillis()));
        }

        // Set the subject and body to an empty String if it's null to prevent an Exception from being thrown when it's used
        if(mail.getSubject() == null) {
            mail.setSubject("");
        }
        if(mail.getBody() == null) {
            mail.setBody("");
        }


        try {
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(mail.getFromAddress()));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(mail.getToAddress()));
            message.setSubject(mail.getSubject());
            message.setText(mail.getBody());

            Transport.send(message);
        } catch (Exception e) {
            throw new SendMailFailureException(e.getMessage());
        }
    }
}