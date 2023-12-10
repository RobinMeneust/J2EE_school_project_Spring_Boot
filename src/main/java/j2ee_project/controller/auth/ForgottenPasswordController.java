package j2ee_project.controller.auth;

import j2ee_project.service.AuthService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Map;

@RestController
public class ForgottenPasswordController {

    @Autowired
    private AuthService authService;

    /**
     * Get page to ask to change password on the website
     * @return the page to go
     */
    @GetMapping(value = "/forgotten-password")
    public ModelAndView getForgottenPasswordPage()
    {
        return new ModelAndView("forgottenPassword");
    }

    /**
     * Get page to change password on the website
     * @param token the token in the url to authenticate the user
     * @return the page to go
     */
    @GetMapping(value = "/change-password")
    public ModelAndView getChangePasswordPage(@RequestParam String token)
    {
        String errorMessage = authService.changePasswordPageVerifications(token);
        ModelAndView modelAndView;
        if (errorMessage == null){
            modelAndView = new ModelAndView("/changePassword");
            modelAndView.addObject("forgottenPasswordToken", token);
        }
        else{
            modelAndView = new ModelAndView("/forgottenPassword");
            modelAndView.addObject("noForgottenPassword", errorMessage);
        }
        return modelAndView;
    }

    /**
     * Start the changing password process for a user with the parameters given in the request object. Different errors can be sent to the sender in the request object if a problem occur
     * @param request Request object received by the servlet
     * @return the page to go
     */
    @PostMapping(value = "/forgotten-password")
    public ModelAndView startForgottenPasswordProcess(@RequestParam String email, HttpServletRequest request)
    {
        String errorMessage = authService.startForgottenPasswordProcess(email, request);
        ModelAndView modelAndView;
        if(errorMessage == null){
            modelAndView = new ModelAndView("/index");
        }
        else{
            modelAndView = new ModelAndView("/forgottenPassword");
            modelAndView.addObject("errorMessage", errorMessage);
        }
        return modelAndView;
    }


    /**
     * Get page to change password on the website
     * @param request Request object received by the servlet
     * @return the page to go
     */
    @PostMapping(value = "/change-password")
    public ModelAndView changePassword(@RequestParam String password, @RequestParam String confirmPassword, @RequestParam String forgottenPasswordToken, HttpServletRequest request)
    {
        Map<String, String> errorMessage = authService.changePassword(password, confirmPassword, forgottenPasswordToken);
        ModelAndView modelAndView;
        if(errorMessage == null){
            modelAndView = new ModelAndView("/index");
        }
        else if(errorMessage.containsKey("Error1")){
            modelAndView = new ModelAndView("/forgottenPassword");
            modelAndView.addObject("errorMessage", errorMessage.get("Error1"));
        }
        else{
            modelAndView = new ModelAndView("/changePassword");
            modelAndView.addObject("errorMessage", errorMessage.get("Error2"));
            modelAndView.addObject("forgottenPasswordToken", forgottenPasswordToken);
        }
        return modelAndView;
    }



}
