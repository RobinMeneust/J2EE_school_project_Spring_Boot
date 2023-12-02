package j2ee_project.model.user;

import jakarta.persistence.*;

@Entity
@Table(name="`Permission`")
public class Permission {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "permission", nullable = false, length = 50, unique = true)
    @Enumerated(EnumType.STRING)
    private TypePermission permission;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TypePermission getPermission() {
        return permission;
    }

    public void setPermission(TypePermission permission) {
        this.permission = permission;
    }

    public Permission(){

    }

    public Permission(TypePermission permission){
        this.permission = permission;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Permission that = (Permission) o;

        if (id != that.id) return false;
        if (permission != null ? !permission.equals(that.permission) : that.permission != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (permission != null ? permission.hashCode() : 0);
        return result;
    }
}