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

    public ModeratorDTO(String firstName, String lastName, String email, String password, String confirmPassword, String phoneNumber, Set<Permission> permissions) {
        super(firstName, lastName, email, password, confirmPassword,phoneNumber);
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions(){
        return this.permissions;
    }
}