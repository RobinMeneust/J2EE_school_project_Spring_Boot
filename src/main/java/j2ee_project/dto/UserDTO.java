package j2ee_project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * This class is a Data Transfer Object which contain and check User data
 *
 * @author Lucas VELAY
 */
public class UserDTO {

    @NotBlank(message = "First name can not be blank.")
    @Size(max = 30, message = "First name can not exceed 30 characters.")
    @Pattern(regexp = "^[a-zA-ZÀ-ÖØ-öø-ÿ\\-']*$", message = "First name is not valid : only letters and -' are authorized.")
    private String firstName;
    @NotBlank(message = "Last name can not be blank.")
    @Size(max = 30, message = "Last name can not exceed 30 characters.")
    @Pattern(regexp = "^[a-zA-ZÀ-ÖØ-öø-ÿ-]*$", message = "Last name is not valid : only letters and -' are authorized.")
    private String lastName;
    @Pattern(regexp = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$",message = "Email is not valid.")
    private String email;
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,24}$", message = "Password is not valid : it needs letters, numbers, special characters @$!%*#?& and length between 8 and 24.")
    private String password;
    private String confirmPassword;
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be composed by 10 numbers with this format : 0000000000")
    private String phoneNumber;

    /**
     * Instantiates a new User dto.
     *
     * @param firstName       the first name
     * @param lastName        the last name
     * @param email           the email
     * @param password        the password
     * @param confirmPassword the confirm password
     * @param phoneNumber     the phone number
     */
    public UserDTO(String firstName, String lastName, String email, String password, String confirmPassword, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.phoneNumber = phoneNumber;
    }

    /**
     * Get first name
     *
     * @return First name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Get last name
     *
     * @return Last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Get email
     *
     * @return Email email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Gets confirm password.
     *
     * @return the confirm password
     */
    public String getConfirmPassword() {
        return confirmPassword;
    }

    /**
     * Gets phone number.
     *
     * @return the phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Set password.
     *
     * @param hashPassword the hash password
     */
    public void setPassword(String hashPassword){
        this.password = hashPassword;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "firstName=" + firstName +
                ", lastName=" + lastName +
                ", email=" + email +
                ", password=" + password +
                ", confirmPassword=" + confirmPassword +
                ", phoneNumber=" + phoneNumber +
                '}';
    }
}