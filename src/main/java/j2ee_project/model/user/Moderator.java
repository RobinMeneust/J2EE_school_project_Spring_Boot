package j2ee_project.model.user;

import j2ee_project.dto.ModeratorDTO;
import j2ee_project.dto.UserDTO;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

/**
 * User with special permissions which are given specified in his attributes
 */
@Entity
@PrimaryKeyJoinColumn(name = "idUser")
@Inheritance(strategy = InheritanceType.JOINED)
public class Moderator extends User{

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "ModeratorPermission",
            joinColumns = @JoinColumn(name = "idModerator"),
            inverseJoinColumns = @JoinColumn(name = "idPermission")
    )
    private Set<Permission> permissions = new HashSet<>();

    /**
     * Instantiates a new Moderator.
     */
    public Moderator(){
        super();
    }

    /**
     * Instantiates a new Moderator.
     *
     * @param moderatorDTO the moderator dto
     */
    public Moderator(ModeratorDTO moderatorDTO) {
        super(moderatorDTO);
        this.permissions = moderatorDTO.getPermissions();
    }


    /**
     * Gets permissions.
     *
     * @return the permissions
     */
    public Set<Permission> getPermissions() {
        return permissions;
    }

    /**
     * Add permission.
     *
     * @param permission the permission
     */
    public void addPermission(Permission permission){
        this.permissions.add(permission);
    }

    /**
     * Remove permission.
     *
     * @param permission the permission
     */
    public void removePermission(Permission permission){
        if(permissions == null || !permissions.contains(permission)) {
            return;
        }
        permissions.remove(permission);
    }

    /**
     * Check if he has the given permission
     *
     * @param permission the permission tested
     * @return True if he has the permission and false otherwise
     */
    public boolean isAllowed(Permission permission) {
        if(getPermissions()==null) return false;
        return getPermissions().contains(permission);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Moderator moderator = (Moderator) o;

        if (this.getId() != moderator.getId()) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return this.getId();
    }
}