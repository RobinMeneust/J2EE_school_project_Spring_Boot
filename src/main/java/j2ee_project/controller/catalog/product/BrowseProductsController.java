package j2ee_project.controller.catalog.product;

import j2ee_project.Application;
import j2ee_project.service.catalog.category.CategoryService;
import j2ee_project.service.catalog.product.ProductService;
import j2ee_project.service.discount.DiscountService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationContext;

import java.io.IOException;

/**
 * This class is a servlet used to browse products. It's a controller in the MVC architecture of this project.
 *
 * @author Robin MENEUST
 */
@WebServlet("/browse-products")
public class BrowseProductsController extends HttpServlet
{

    private static ProductService productService;

    @Override
    public void init() {
        ApplicationContext context = Application.getContext();
        productService = context.getBean(ProductService.class);
    }

    /**
     * Get a page to browse the list of products. Manages pagination and search filters
     * @param request Request object received by the servlet
     * @param response Response to be sent
     * @throws ServletException If the request for the GET could not be handled
     * @throws IOException If an input or output error is detected when the servlet handles the GET request
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String pageStr = request.getParameter("page");
        int page = 1;

        if(pageStr != null && !pageStr.trim().isEmpty()) {
            try {
                page = Integer.parseInt(pageStr);
            } catch(Exception ignore) {}
        }

        String name = request.getParameter("name");
        String category = request.getParameter("category");
        Float minPrice = request.getParameter("min-price") != null ? Float.parseFloat(request.getParameter("min-price")) : null;
        Float maxPrice = request.getParameter("max-price") != null ? Float.parseFloat(request.getParameter("max-price")) : null;

        request.setAttribute("page", page);
        request.setAttribute("products", productService.getProducts(name, category, minPrice, maxPrice, 15*(page-1),15));
        long totalPages = ((productService.getSize(name, category, minPrice, maxPrice)-1) / 15) + 1;
        request.setAttribute("totalPages", totalPages);


        if(page <= 1 && page > totalPages) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            RequestDispatcher view = request.getRequestDispatcher("WEB-INF/views/browseProducts.jsp");
            view.forward(request, response);
        } catch(Exception err) {
            // The forward didn't work
            System.err.println(err.getMessage());
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

}
