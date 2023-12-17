package j2ee_project.service.user;

import j2ee_project.repository.user.UserRepository;
import j2ee_project.model.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The type User service.
 */
@Service
public class UserService {
    private final UserRepository userRepository;

    /**
     * Instantiates a new User service.
     *
     * @param userRepository the user repository
     */
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Delete a user from the database
     *
     * @param user the user to delete
     */
    @Transactional
    public void deleteUser(User user){
        userRepository.delete(user);
    }

    /**
     * Add a user in the database
     *
     * @param user the user to add
     */
    @Transactional
    public void addUser(User user){
        userRepository.save(user);
    }

    /**
     * Update a user in the database
     *
     * @param user the user to update
     */
    @Transactional
    public void updateUser(User user){
        userRepository.save(user);
    }

    /**
     * Check if an email and a phone number are in the database
     *
     * @param userId      the user id
     * @param email       the email to check
     * @param phoneNumber the phone number
     * @return the boolean indicating the presence of the email
     */
    @Transactional
    public boolean emailOrPhoneNumberIsInDb(Integer userId, String email, String phoneNumber){
        Integer count = userRepository.countByEmailOrPhoneNumber(email,phoneNumber);
        if (userId != null){
            User user = userRepository.findById(userId);
            if (count != null && (user.getEmail().equals(email) || user.getPhoneNumber().equals(phoneNumber))){
                count--;
            }
        }
        return count != null && count > 0;
    }

    /**
     * Get user from the database with his email
     *
     * @param email the email of the user to get
     * @return the recovered user or null if not
     */
    @Transactional
    public User getUserFromEmail(String email){
        return userRepository.findByEmail(email);
    }

    /**
     * Get user by id
     *
     * @param id ID of the user
     * @return User fetched
     */
    @Transactional
    public User getUser(int id) {
        return userRepository.findById(id);
    }
}
