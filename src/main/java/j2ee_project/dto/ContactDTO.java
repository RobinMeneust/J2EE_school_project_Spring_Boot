package j2ee_project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * This class is a Data Transfer Object which contain and check form contact data
 *
 * @author Lucas VELAY
 */
public class ContactDTO {

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
    @NotBlank(message = "Subject can not be blank.")
    @Size(max = 50, message = "First name can not exceed 50 characters.")
    private String subject;
    @NotBlank(message = "Message can not be blank.")
    @Size(max = 256, message = "Message can not exceed 256 characters.")
    private String bodyMessage;

    public ContactDTO(String firstName, String lastName, String email, String subject, String bodyMessage) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.subject = subject;
        this.bodyMessage = bodyMessage;
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

    public String getSubject() {
        return subject;
    }

    public String getBodyMessage() {
        return bodyMessage;
    }

    @Override
    public String toString() {
        return "ContactDTO{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", subject='" + subject + '\'' +
                ", bodyMessage='" + bodyMessage + '\'' +
                '}';
    }
}
