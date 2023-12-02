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
    @Pattern(regexp = "^[a-zA-ZÀ-ÖØ-öø-ÿ-]*$", message = "First name is not valid : only letters and -' are authorized.")
    private String lastName;
    @Pattern(regexp = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$",message = "Email is not valid.")
    private String email;
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,24}$", message = "Password is not valid : it needs letters, numbers, special characters @$!%*#?& and length between 8 and 24.")
    private String password;
    private String confirmPassword;
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be composed by 10 numbers with this format : 0000000000")
    private String phoneNumber;

    public UserDTO(String firstName, String lastName, String email, String password, String confirmPassword, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.phoneNumber = phoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPassword(String hashPassword){
        this.password = hashPassword;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}