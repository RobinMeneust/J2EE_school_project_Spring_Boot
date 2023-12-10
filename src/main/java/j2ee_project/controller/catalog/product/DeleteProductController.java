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
 * This class is a servlet used to delete a product. It's a controller in the MVC architecture of this project.
 */
@WebServlet("/delete-product")
public class DeleteProductController extends HttpServlet {

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
     * Delete a product whose ID is given in the param "id"
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
                String productIdStr = request.getParameter("id");
                int productId = -1;

                if (productIdStr != null && !productIdStr.trim().isEmpty()) {
                    try {
                        productId = Integer.parseInt(productIdStr);
                    } catch (Exception ignore) {
                    }
                }

                if (productId <= 0) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Product ID must be positive");
                    return;
                }
                productService.deleteProduct(productId);
                response.sendRedirect("dashboard?tab=products");
            } else {
                response.sendRedirect("dashboard");
            }
        }catch (Exception err){
            System.err.println(err.getMessage());
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}