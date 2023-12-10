package j2ee_project.service;

import j2ee_project.dto.CustomerDTO;
import j2ee_project.dto.ModeratorDTO;
import j2ee_project.model.Mail;
import j2ee_project.model.loyalty.LoyaltyAccount;
import j2ee_project.model.loyalty.LoyaltyProgram;
import j2ee_project.model.user.*;
import j2ee_project.service.loyalty.LoyaltyProgramService;
import j2ee_project.service.mail.MailService;
import j2ee_project.service.order.CartService;
import j2ee_project.service.user.ForgottenPasswordService;
import j2ee_project.service.user.UserService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


// import static j2ee_project.service.CartManager.copySessionCartToCustomer;


/**
 * This is a service class which contains method for authentication
 *
 * @author Lucas VELAY
 */
@Service
public class AuthService {
    @Autowired
    private UserService userService;

    @Autowired
    private LoyaltyProgramService loyaltyProgramService;

    @Autowired
    private MailService mailService;

    @Autowired
    private CartService cartService;

    @Autowired
    private CartManager cartManager;

    @Autowired
    private ForgottenPasswordService forgottenPasswordService;


    /**
     * Process the login
     *
     * @param email the email
     * @param password the password
     * @param request the request object
     * @return error message if it needs
     */
    public String loginProcess(String email, String password, HttpServletRequest request){
        try {
            User user = this.logIn(email, password);
            if(user == null) {
                return "Error during logging, check your email and your password";
            }
            else {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);

                // Copy the session cart to the current user cart (and override it if it's not empty) if the user is a customer
                if(user instanceof Customer customer) {
                    cartManager.copySessionCartToCustomerEmptyCart(request, customer);

                    // Refresh the user's cart
                    customer.setCart(cartService.getCartFromCustomerId(customer.getId()));
                    session.setAttribute("user", customer);
                }

                return null;
            }
        }catch (Exception e) {
            System.err.println(e.getMessage());
            return "Error during logging, check your email and your password";
        }
    }


    /**
     * Method used to check if the login information are in the database
     *
     * @param email email
     * @param password password
     * @return the found user
     * @throws NoSuchAlgorithmException exception throws by password hash methods
     * @throws InvalidKeySpecException exception throws by password hash methods
     */
    public User logIn(String email, String password) throws NoSuchAlgorithmException, InvalidKeySpecException{
        User user = userService.getUserFromEmail(email);
        if(user == null){
            return null;
        }
        if(!HashService.validatePassword(password, user.getPassword())){
            return null;
        }
        return user;
    }

    /**
     * Treat the new customer registering
     *
     * @param customerDTO the customerDto of the customer to register
     * @param request the request object
     * @return error message if it needs
     */
    public Map<String, String> newCustomerProcess(CustomerDTO customerDTO, HttpServletRequest request){
        Map<String, String> inputErrors = DTOService.userDataValidation(customerDTO);

        if(!inputErrors.isEmpty()){
            return inputErrors;
        }
        if (userService.emailOrPhoneNumberIsInDb(customerDTO.getEmail(), customerDTO.getPhoneNumber())) {
            inputErrors.put("emailOrPhoneNumberInDbError", "Email or phone number already used");
            return inputErrors;
        }

        try {
            User user = this.registerCustomer(customerDTO);

            sendConfirmationMail(user.getEmail());

            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            // Copy the session cart to the current user cart (and override it if it's not empty) if the user is a customer
            if (user instanceof Customer customer) {
                cartManager.copySessionCartToCustomerEmptyCart(request, customer);

                // Refresh the user's cart
                customer.setCart(cartService.getCartFromCustomerId(customer.getId()));
                session.setAttribute("user", customer);
            }
            return null;
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
            inputErrors.put("RegisterProcessError", "Error during register process");
            return inputErrors;
        }
    }

    /**
     * Register a customer in the database after hashed password generation
     * @param customerDTO customer data transfer object
     * @return the registered user
     * @throws NoSuchAlgorithmException exception throws by password hash methods
     * @throws InvalidKeySpecException exception throws by password hash methods
     */
    public User registerCustomer(CustomerDTO customerDTO) throws NoSuchAlgorithmException, InvalidKeySpecException {
        customerDTO.setPassword(HashService.generatePasswordHash(customerDTO.getPassword()));
        Customer customer = new Customer(customerDTO);
        LoyaltyAccount loyaltyAccount = new LoyaltyAccount();
        loyaltyAccount.setLoyaltyPoints(0);
        LoyaltyProgram loyaltyProgram = loyaltyProgramService.getLoyaltyProgram();
        loyaltyAccount.setEndDate(Date.valueOf(LocalDate.now().plusDays(loyaltyProgram.getDurationNbDays())));
        loyaltyAccount.setLoyaltyProgram(loyaltyProgram);
        customer.setLoyaltyAccount(loyaltyAccount);
        userService.addUser(customer);
        return customer;
    }

    /**
     * Send the registering confirmation email
     *
     * @param email the destination email
     */
    private void sendConfirmationMail(String email){
        Mail mail = new Mail();
        MailManager mailManager = MailManager.getInstance();

        try
        {
            mail.setFromAddress("jeewebproject@gmail.com");
            mail.setToAddress(email);
            mail.setSubject("Register confirmation on Boarder Games website");
            mail.setBody("Hello,\nWe confirm your registration on the Boarder Games website.\n");

            // Set the date to current time if it's null
            if(mail.getDate() == null) {
                mail.setDate(new Date(Calendar.getInstance().getTimeInMillis()));
            }

            mailService.addMail(mail);
            mailManager.send(mail);
        }
        catch(Exception ignore) {}
    }

    /**
     * Start the forgotten password process
     *
     * @param email the destination email
     * @param request Request object received by the servlet
     * @return error message if it needs
     */
    public String startForgottenPasswordProcess(String email, HttpServletRequest request){

        if(!email.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")){
            return "Email is not valid.";
        }

        User user = userService.getUserFromEmail(email);

        if(user == null || user.getForgottenPassword() != null){
            return "You're not registered or you have already ask to change your password. Check your mail box.";
        }

        String token;
        do{
            token = HashService.generateToken(20);
        }while(forgottenPasswordService.getForgottenPasswordFromToken(token) != null);

        String link = "http://localhost:8080" + request.getContextPath() + "/change-password?token=" + token;
        sendForgottenPasswordEmail(email, link);
        ForgottenPassword forgottenPassword = new ForgottenPassword(user, token);
        forgottenPasswordService.addForgottenPassword(forgottenPassword);
        return null;
    }

    /**
     * Send the email to start the recovery password process
     *
     * @param email the destination email
     * @param link the link in the message
     */
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

            mailService.addMail(mail);
            mailManager.send(mail);
        }
        catch(Exception ignore) {}
    }

    /**
     * Verify the token
     *
     * @param token the token
     * @return error message if it needs
     */
    public String  changePasswordPageVerifications(String token){
        ForgottenPassword forgottenPassword = forgottenPasswordService.getForgottenPasswordFromToken(token);

        if(forgottenPassword == null){
            return "There is no forgotten password linked to this link. You can do a demand here.";
        }

        return null;
    }

    /**
     * Change password process
     *
     * @param password the password
     * @param confirmPassword the confirmation password
     * @param token the token
     * @return error message if it needs
     */
    public Map<String, String> changePassword(String password, String confirmPassword, String token){

        ForgottenPassword forgottenPassword = forgottenPasswordService.getForgottenPasswordFromToken(token);
        Map<String, String> errorMessage = new HashMap<>();

        if(forgottenPassword == null){
            errorMessage.put("Error1", "You don't have started change password process.");
            return errorMessage;
        }

        if(!password.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,24}$") || !password.equals(confirmPassword)){
            errorMessage.put("Error2", "Password is not valid : it needs letters, numbers, special characters @$!%*#?& and length between 8 and 24.\nPassword and confirm password much match.");
            return errorMessage;
        }

        User user =forgottenPasswordService.getUser(forgottenPassword);

        if(user == null){
            errorMessage.put("Error2", "An error occur");
            return errorMessage;

        }

        try {
            user.setPassword(HashService.generatePasswordHash(password));
            userService.updateUser(user);
            forgottenPasswordService.removeForgottenPassword(forgottenPassword);
            return null;
        }catch (Exception e) {
            errorMessage.put("Error2", "An error occur");
            return errorMessage;
        }
    }


    /**
     * Register a moderator in the database after hashed password generation
     * @param moderatorDTO moderator data transfer object
     * @return the registered user
     * @throws NoSuchAlgorithmException exception throws by password hash methods
     * @throws InvalidKeySpecException exception throws by password hash methods
     */
    public User registerModerator(ModeratorDTO moderatorDTO) throws NoSuchAlgorithmException, InvalidKeySpecException {
        moderatorDTO.setPassword(HashService.generatePasswordHash(moderatorDTO.getPassword()));
        Moderator moderator = new Moderator(moderatorDTO);
        userService.addUser(moderator);
        return moderator;
    }


    /**
     * Check if a moderator has a precise permission
     *
     * @param user the moderator to check
     * @param typePermission the tested permission
     * @return true or false if the moderator has or no the permission
     */
    public boolean checkModerator(User user, TypePermission typePermission){
        if(user == null){
            return false;
        }
        if(!(user instanceof Moderator)){
            return false;
        }
        Permission permission = new Permission();
        permission.setPermission(typePermission);
        if(!((Moderator) user).getPermissions().contains(permission)){
            return false;
        }
        return true;
    }

    /**
     * Get a customer object from a user object
     *
     * @param user the user
     * @return the customer or null
     */
    public Customer getCustomer(User user) {
        if(user instanceof Customer) {
            return (Customer) user;
        } else {
            return null;
        }
    }

    /**
     * Get a moderator object from a user object
     *
     * @param user the user
     * @return the moderator or null
     */
    public Moderator getModerator(User user) {
        if(user instanceof Moderator) {
            return (Moderator) user;
        } else {
            return null;
        }
    }
}