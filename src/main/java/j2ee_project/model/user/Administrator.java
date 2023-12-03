package j2ee_project.model.user;

import j2ee_project.dto.ModeratorDTO;
import jakarta.persistence.*;

@Entity
@Table(name="`Administrator`")
@PrimaryKeyJoinColumn(name = "`idModerator`")
public class Administrator extends Moderator{
    public Administrator(ModeratorDTO moderatorDTO) {
        super(moderatorDTO);
    }

    public Administrator() {
        super();
    }

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