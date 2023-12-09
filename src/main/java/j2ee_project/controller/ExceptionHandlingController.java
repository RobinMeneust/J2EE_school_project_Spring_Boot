package j2ee_project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ExceptionHandlingController {

    @ExceptionHandler(value = Exception.class)
    public ModelAndView redirectToErrorPage(Exception e) {
        ModelAndView mav = new ModelAndView("errorPage");
        mav.getModelMap().addAttribute("exception", e);
        return mav;
    }
}
