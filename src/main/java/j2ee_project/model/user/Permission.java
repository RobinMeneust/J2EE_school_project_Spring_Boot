package j2ee_project.model.user;

import jakarta.persistence.*;

/**
 * Permission (e.g. edit products...)
 */
@Entity
public class Permission {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "permission", nullable = false, length = 50, unique = true)
    @Enumerated(EnumType.STRING)
    private TypePermission permission;

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets permission.
     *
     * @return the permission
     */
    public TypePermission getPermission() {
        return permission;
    }

    /**
     * Sets permission.
     *
     * @param permission the permission
     */
    public void setPermission(TypePermission permission) {
        this.permission = permission;
    }

    /**
     * Instantiates a new Permission.
     */
    public Permission(){}

    /**
     * Instantiates a new Permission.
     *
     * @param permission the permission
     */
    public Permission(TypePermission permission){
        this.permission = permission;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Permission that = (Permission) o;

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