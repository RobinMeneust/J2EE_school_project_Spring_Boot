package j2ee_project.controller.catalog.category;

import j2ee_project.Application;
import j2ee_project.model.Discount;
import j2ee_project.model.catalog.Category;
import j2ee_project.service.catalog.category.CategoryService;
import j2ee_project.service.discount.DiscountService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.springframework.context.ApplicationContext;

import java.io.IOException;


/**
 * This class is a servlet used to add a category. It's a controller in the MVC architecture of this project.
 */
@WebServlet("/add-category")
public class AddCategoryController extends HttpServlet {

    private static CategoryService categoryService;
    private static DiscountService discountService;

    @Override
    public void init() {
        ApplicationContext context = Application.getContext();
        categoryService = context.getBean(CategoryService.class);
        discountService = context.getBean(DiscountService.class);
    }
    /**
     * Get the page to add a category
     * @param request Request object received by the servlet
     * @param response Response to be sent
     * @throws ServletException If the request for the GET could not be handled
     * @throws IOException If an input or output error is detected when the servlet handles the GET request
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setAttribute("discounts", discountService.getDiscounts());
            RequestDispatcher view = request.getRequestDispatcher("WEB-INF/views/dashboard/add/addCategory.jsp");
            view.forward(request,response);
        }catch (Exception err){
            System.err.println(err.getMessage());
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    /**
     * Add a category to the DB
     * @param request Request object received by the servlet
     * @param response Response to be sent
     * @throws ServletException If the request for the GET could not be handled
     * @throws IOException If an input or output error is detected when the servlet handles the GET request
     */

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Category category = new Category();

        category.setName(request.getParameter("name"));
        category.setDescription(request.getParameter("description"));

        String discountStr = (request.getParameter("discount").isEmpty()) ? null : request.getParameter(("discount"));
        if (discountStr != null) {
            int discountId = Integer.parseInt(discountStr);
            Discount discount = discountService.getDiscount(discountId);
            category.setDiscount(discount);
        }
        categoryService.addCategory(category);

        try {
            response.sendRedirect("dashboard?tab=categories");
        }catch (Exception err){
            System.err.println(err.getMessage());
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}