package j2ee_project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomePageController {
    @GetMapping(value = "/")
    public ModelAndView firstView()
    {
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("key", "value");
        return modelAndView;
    }
}
