package j2ee_project.service;

/**
 * Exception used to describe the case where the mail could not be sent
 *
 * @author Robin Meneust
 */
public class SendMailFailureException extends Exception {
    /**
     * Constructs a new exception with the given error message that is added to the exception: "ERROR: The mail could not be sent\n " followed by the full error message
     *
     * @param e the exception message
     */
    public SendMailFailureException(String e) {
        super("The mail could not be send\n"+e);
    }
}