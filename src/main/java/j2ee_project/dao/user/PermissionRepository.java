package j2ee_project.dao.user;

import j2ee_project.model.user.Permission;
import j2ee_project.model.user.TypePermission;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository to interacts with the Permission table in the DB
 *
 * @author Robin Meneust
 */
public interface PermissionRepository extends JpaRepository<Permission,Long> {
    Permission getPermissionByPermission(TypePermission permissionType);
}
