package j2ee_project.service;

import java.util.Collection;
import java.util.Optional;

import j2ee_project.dao.UserRepository;
import j2ee_project.model.user.User;

public interface UserService {
    Optional<User> getUserById(Long id);
}
