package j2ee_project.controller.catalog.product;

import j2ee_project.Application;
import j2ee_project.model.catalog.Category;
import j2ee_project.model.catalog.Product;
import j2ee_project.service.catalog.category.CategoryService;
import j2ee_project.service.catalog.product.ProductService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.springframework.context.ApplicationContext;

import java.io.IOException;

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
            request.setAttribute("categories", categoryService.getCategories());
            RequestDispatcher view = request.getRequestDispatcher("WEB-INF/views/dashboard/add/addProduct.jsp");
            view.forward(request,response);
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
        Product product = new Product();

        product.setName(request.getParameter("name"));
        product.setStockQuantity(Integer.parseInt(request.getParameter("stock-quantity")));
        product.setUnitPrice(Integer.parseInt(request.getParameter("unit-price")));
        product.setDescription(request.getParameter("description"));
        String weight = request.getParameter("weight");
        if (weight != null){
            product.setWeight(Float.valueOf(weight));
        }

        int categoryId = Integer.parseInt(request.getParameter("category"));
        Category category = categoryService.getCategory(categoryId);
        product.setCategory(category);

        productService.addProduct(product);

        try {
            response.sendRedirect("dashboard?tab=products");
        }catch (Exception err){
            System.err.println(err.getMessage());
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}