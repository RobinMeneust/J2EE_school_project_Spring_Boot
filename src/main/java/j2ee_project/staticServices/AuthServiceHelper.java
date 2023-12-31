package j2ee_project.staticServices;

import j2ee_project.Application;
import j2ee_project.model.catalog.Category;
import j2ee_project.model.user.Customer;
import j2ee_project.model.user.Moderator;
import j2ee_project.model.user.User;
import j2ee_project.service.AuthService;
import j2ee_project.service.catalog.category.CategoryService;
import org.eclipse.tags.shaded.org.apache.xpath.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

/**
 * The type Auth service helper.
 */
public class AuthServiceHelper {

    private static final AuthService authService;

    static {
        ApplicationContext context = Application.getContext();
        authService = context.getBean(AuthService.class);
    }

    /**
     * Get a customer from a user
     *
     * @param user the user
     * @return the customer or null
     */
    public static Customer getCustomer(User user) {
        return authService.getCustomer(user);
    }

    /**
     * Get a moderator from a user
     *
     * @param user the user
     * @return the customer or null
     */
    public static Moderator getModerator(User user) {
        return authService.getModerator(user);
    }
}