package j2ee_project.staticServices;

import j2ee_project.Application;
import j2ee_project.model.user.Permission;
import j2ee_project.model.user.TypePermission;
import j2ee_project.service.user.PermissionService;
import org.springframework.context.ApplicationContext;

public class PermissionHelper {

    public static final PermissionService permissionService;

    static {
        ApplicationContext context = Application.getContext();
        permissionService = context.getBean(PermissionService.class);
    }

    public static Permission getPermission(TypePermission typePermission){
        return permissionService.getPermission(typePermission);
    }
}
