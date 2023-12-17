package j2ee_project.service.user;

import j2ee_project.repository.user.PermissionRepository;
import j2ee_project.model.user.Permission;
import j2ee_project.model.user.TypePermission;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The type Permission service.
 */
@Service
public class PermissionService {
    /**
     * The Permission repository.
     */
    PermissionRepository permissionRepository;

    /**
     * Instantiates a new Permission service.
     *
     * @param permissionRepository the permission repository
     */
    public PermissionService(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    /**
     * Get a permission from its type
     *
     * @param type Type of the permission searched
     * @return Permission whose type matches with the one given
     */
    @Transactional
    public Permission getPermission(TypePermission type) {
        return permissionRepository.getPermissionByPermission(type);
    }
}
