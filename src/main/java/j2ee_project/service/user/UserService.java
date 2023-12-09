package j2ee_project.service.user;

import j2ee_project.dao.user.UserRepository;
import j2ee_project.model.user.User;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Delete a user from the database
     *
     * @param user the user to delete
     */
    public void deleteUser(User user){
        userRepository.delete(user);
    }

    /**
     * Add a user in the database
     *
     * @param user the user to add
     */
    public void addUser(User user){
        userRepository.save(user);
    }

    /**
     * Update a user in the database
     *
     * @param user the user to update
     */
    public void updateUser(User user){
        userRepository.save(user);
    }

    /**
     * Check if an email and a phone number are in the database
     *
     * @param email       the email to check
     * @param phoneNumber the phone number
     * @return the boolean indicating the presence of the email
     */
    public boolean emailOrPhoneNumberIsInDb(String email, String phoneNumber){
        Integer count = userRepository.countByEmailOrPhoneNumber(email,phoneNumber);
        return count != null && count > 0;
    }

    /**
     * Get user from the database with his email
     *
     * @param email the email of the user to get
     * @return the recovered user or null if not
     */
    public User getUserFromEmail(String email){
        return userRepository.findByEmail(email);
    }

    /**
     * Get user by id
     *
     * @param id ID of the user
     * @return User fetched
     */
    public User getUser(int id) {
        return userRepository.findById(id);
    }
}
