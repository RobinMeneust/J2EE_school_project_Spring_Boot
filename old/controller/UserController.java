package j2ee_project.controller;

import java.util.Optional;

import j2ee_project.repository.UserRepository;
import j2ee_project.model.user.User;
import j2ee_project.service.UserService;
import j2ee_project.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }

    @GetMapping("/user/{id}")
    Optional<User> findById(@PathVariable("id") Long id){
        return userService.getUserById(id);
    }
}