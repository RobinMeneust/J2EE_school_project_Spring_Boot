package j2ee_project.controller.catalog.product;

import j2ee_project.Application;
import j2ee_project.model.user.Moderator;
import j2ee_project.model.user.TypePermission;
import j2ee_project.service.catalog.product.ProductService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.springframework.context.ApplicationContext;

import java.io.IOException;

import static j2ee_project.staticServices.PermissionHelper.getPermission;

/**
 * This class is a servlet used to get the list of products. It's a controller in the MVC architecture of this project.
 */
@WebServlet("/get-products")
public class GetProductsController extends HttpServlet {

    private static ProductService productService;

    /**
     * Initialize the services used by the class
     */
    @Override
    public void init() {
        ApplicationContext context = Application.getContext();
        productService = context.getBean(ProductService.class);
    }

    /**
     * Get the list of the products in the DB
     * @param request Request object received by the servlet
     * @param response Response to be sent
     * @throws ServletException If the request for the GET could not be handled
     * @throws IOException If an input or output error is detected when the servlet handles the GET request
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            Object obj = session.getAttribute("user");
            if (obj instanceof Moderator moderator
                    && moderator.isAllowed(getPermission(TypePermission.CAN_MANAGE_PRODUCT))) {
                request.setAttribute("products", productService.getProducts());
            } else {
                response.sendRedirect("dashboard");
            }
        }catch (Exception err){
            System.err.println(err.getMessage());
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}