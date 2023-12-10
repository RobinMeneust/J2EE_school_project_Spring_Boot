package j2ee_project.controller.auth;

import j2ee_project.dto.CustomerDTO;
import j2ee_project.service.AuthService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.util.Map;

/**
 * The type Authentication controller.
 */
@RestController
public class AuthenticationController {

    @Autowired
    private AuthService authService;

    /**
     * Get a page to register on the website
     *
     * @return the page to go
     */
    @GetMapping(value = "/register")
    public ModelAndView getRegisterPage()
    {
        return new ModelAndView("register");
    }

    /**
     * Get a page to login on the website
     *
     * @return the page to go
     */
    @GetMapping(value = "/login")
    public ModelAndView getLoginPage()
    {
        return new ModelAndView("login");
    }

    /**
     * Log out the current user.
     *
     * @param request Request object received by the servlet
     * @return the page to go
     */
    @GetMapping(value = "/logout")
    public ModelAndView logOutUser(HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        return new ModelAndView("index");
    }

    /**
     * Register a customer with the parameters given in the request object. Different errors can be sent to the sender in the request object if a problem occur
     *
     * @param customerDTO the customer dto
     * @param request     Request object received by the servlet
     * @return the page to go
     */
    @PostMapping(value = "/register")
    public ModelAndView registerCustomer(@ModelAttribute CustomerDTO customerDTO, HttpServletRequest request)
    {
        Map<String, String> errorsMessages = authService.newCustomerProcess(customerDTO, request);
        ModelAndView modelAndView;
        if (errorsMessages == null){
            modelAndView = new ModelAndView("/index");
        }else {
            modelAndView = new ModelAndView("/register");
            modelAndView.addObject("InputError", errorsMessages);
        }
        return modelAndView;
    }

    /**
     * Log in a user with the parameters given in the request object. Different errors can be sent to the sender in the request object if a problem occur
     *
     * @param email    the email
     * @param password the password
     * @param request  Request object received by the servlet
     * @return the page to go
     */
    @PostMapping(value = "/login")
    public ModelAndView login(@RequestParam String email, @RequestParam String password, HttpServletRequest request)
    {
        String errorMessages = authService.loginProcess(email, password, request);
        ModelAndView modelAndView;
        if (errorMessages == null){
            modelAndView = new ModelAndView("/index");
        }else {
            modelAndView = new ModelAndView("/login");
            modelAndView.addObject("LoggingProcessError", errorMessages);
        }
        return modelAndView;
    }

}
