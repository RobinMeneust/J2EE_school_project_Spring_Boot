package j2ee_project.controller.auth;

import j2ee_project.dto.CustomerDTO;
import j2ee_project.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.net.http.HttpRequest;
import java.util.Map;

@RestController
public class AuthenticationController {

    @Autowired
    private AuthService authService;

    @GetMapping(value = "/register")
    public ModelAndView getRegisterPage()
    {
        return new ModelAndView("register");
    }

    @GetMapping(value = "/login")
    public ModelAndView getLoginPage()
    {
        return new ModelAndView("login");
    }

    @GetMapping(value = "/logout")
    public ModelAndView logOutUser(HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        return new ModelAndView("index");
    }

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
