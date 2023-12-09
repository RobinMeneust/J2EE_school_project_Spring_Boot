package j2ee_project.service.user;

import j2ee_project.dao.user.PermissionRepository;
import j2ee_project.model.user.Permission;
import j2ee_project.model.user.TypePermission;

public class PermissionService {
    PermissionRepository permissionRepository;

    public PermissionService(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    /**
     * Get a permission from its type
     * @param type Type of the permission searched
     * @return Permission whose type matches with the one given
     */
    public Permission getPermission(TypePermission type) {
        return permissionRepository.getPermissionByPermission(type);
    }
}
