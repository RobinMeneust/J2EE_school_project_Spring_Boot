package j2ee_project.controller.auth;

import j2ee_project.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@RestController
public class ForgottenPasswordController {

    @Autowired
    private AuthService authService;

    @GetMapping(value = "/forgotten-password")
    public ModelAndView getForgottenPasswordPage()
    {
        return new ModelAndView("forgottenPassword");
    }

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
