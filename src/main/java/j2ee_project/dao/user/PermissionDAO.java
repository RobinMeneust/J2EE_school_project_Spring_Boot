package j2ee_project.dao.user;

import j2ee_project.dao.JPAUtil;
import j2ee_project.model.user.Permission;
import j2ee_project.model.user.TypePermission;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

/**
 * Class that interact with the database to edit the Permission table in the database
 *
 * @author Robin Meneust
 */
public class PermissionDAO {
    /**
     * Get a permission from its type
     * @param type Type of the permission searched
     * @return Permission whose type matches with the one given
     */
    public static Permission getPermission(TypePermission type) {
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Permission permission = null;
        try {
            permission = entityManager.createQuery("FROM Permission WHERE permission = :permissionType", Permission.class).setParameter("permissionType", type).getSingleResult();
        } catch(Exception ignore) {}


        transaction.commit();
        entityManager.close();
        return permission;
    }
}
