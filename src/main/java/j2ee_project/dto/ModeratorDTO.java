package j2ee_project.dto;

import j2ee_project.model.user.Permission;
import j2ee_project.model.user.TypePermission;

import java.util.Set;

/**
 * This class is a Data Transfer Object which contain and check Moderator data
 *
 * @author Lucas VELAY
 */
public class ModeratorDTO extends UserDTO{

    private Set<Permission> permissions;

    /**
     * Instantiates a new Moderator dto.
     *
     * @param firstName       the first name
     * @param lastName        the last name
     * @param email           the email
     * @param password        the password
     * @param confirmPassword the confirm password
     * @param phoneNumber     the phone number
     */
    public ModeratorDTO(String firstName, String lastName, String email, String password, String confirmPassword, String phoneNumber) {
        super(firstName, lastName, email, password, confirmPassword,phoneNumber);
    }

    /**
     * Get permissions set.
     *
     * @return the set
     */
    public Set<Permission> getPermissions(){
        return this.permissions;
    }

    /**
     * Add permission.
     *
     * @param permission the permission
     */
    public void addPermission(Permission permission) {
        this.permissions.add(permission);
    }

    @Override
    public String toString() {
        return "ModeratorDTO{" +
                "firstName=" + this.getFirstName() +
                ", lastName=" + this.getLastName() +
                ", email=" + this.getEmail() +
                ", password=" + this.getPassword() +
                ", confirmPassword=" + this.getConfirmPassword() +
                ", phoneNumber=" + this.getPhoneNumber() +
                "permissions=" + this.getPermissions() +
                '}';
    }
}