package j2ee_project.controller.catalog.product;

import j2ee_project.Application;
import j2ee_project.dto.catalog.ProductDTO;
import j2ee_project.model.catalog.Category;
import j2ee_project.model.catalog.Product;
import j2ee_project.model.user.Moderator;
import j2ee_project.model.user.TypePermission;
import j2ee_project.service.DTOService;
import j2ee_project.service.catalog.category.CategoryService;
import j2ee_project.service.catalog.product.ProductService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import static j2ee_project.staticServices.PermissionHelper.getPermission;

/**
 * This class is a servlet used to add a product. It's a controller in the MVC architecture of this project.
 */
@WebServlet("/add-product")
public class AddProductController extends HttpServlet {

    private static CategoryService categoryService;
    private static ProductService productService;

    /**
     * Initialize the services used by the class
     */
    @Override
    public void init() {
        ApplicationContext context = Application.getContext();
        categoryService = context.getBean(CategoryService.class);
        productService = context.getBean(ProductService.class);
    }

    /**
     * Get the page to add a product
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
                request.setAttribute("categories", categoryService.getCategories());
                RequestDispatcher view = request.getRequestDispatcher("WEB-INF/views/dashboard/add/addProduct.jsp");
                view.forward(request, response);
            } else {
                response.sendRedirect("dashboard");
            }
        }catch (Exception err){
            System.err.println(err.getMessage());
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    /**
     * Add a product to the DB
     * @param request Request object received by the servlet
     * @param response Response to be sent
     * @throws ServletException If the request for the GET could not be handled
     * @throws IOException If an input or output error is detected when the servlet handles the GET request
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int categoryId = Integer.parseInt(request.getParameter("category"));
        Category category = categoryService.getCategory(categoryId);

        String weightStr = request.getParameter("weight");
        Float weight = null;
        if (weightStr != null && !weightStr.isEmpty()) {
            weight = Float.valueOf(weightStr);
        }

        ProductDTO productDTO = new ProductDTO(
                request.getParameter("name"),
                Integer.parseInt(request.getParameter("stockQuantity")),
                Float.parseFloat(request.getParameter("unitPrice")),
                request.getParameter("description"),
                weight,
                category
        );

        Map<String, String> inputErrors = DTOService.productDataValidation(productDTO);

        String errorDestination = "WEB-INF/views/dashboard/add/addProduct.jsp";
        RequestDispatcher dispatcher = null;

        if (inputErrors.isEmpty()) {
            try {
                productService.addProduct(new Product(productDTO));
                response.sendRedirect("dashboard?tab=products");
            } catch (Exception exception) {
                System.err.println(exception.getMessage());
                request.setAttribute("RegisterProcessError", "Error during register process");
                dispatcher = request.getRequestDispatcher(errorDestination);
                dispatcher.include(request, response);
            }
        } else {
            request.setAttribute("InputError", inputErrors);
            dispatcher = request.getRequestDispatcher(errorDestination);
            dispatcher.include(request, response);
        }

        if (dispatcher != null) doGet(request, response);

    }
}