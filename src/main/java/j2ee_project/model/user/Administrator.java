package j2ee_project.model.user;

import j2ee_project.dto.ModeratorDTO;
import jakarta.persistence.*;

/**
 * Moderator with all permission and additional special ones (e.g. delete moderators)
 */
@Entity
@Table(name = "`Administrator`")
@PrimaryKeyJoinColumn(name = "`idModerator`")
public class Administrator extends Moderator{
    /**
     * Instantiates a new Administrator.
     *
     * @param moderatorDTO the moderator dto
     */
    public Administrator(ModeratorDTO moderatorDTO) {
        super(moderatorDTO);
    }

    /**
     * Instantiates a new Administrator.
     */
    public Administrator() {
        super();
    }

    /**
     * Administrator is allowed regardless the permission
     *
     * @param permission the permission tested
     * @return True if he has the permission and false otherwise
     */
    @Override
    public boolean isAllowed(Permission permission) {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Administrator administrator = (Administrator) o;

        if (this.getId() != administrator.getId()) return false;

        return true;
    }

}