package j2ee_project.controller.catalog.category;

import j2ee_project.Application;
import j2ee_project.dto.catalog.CategoryDTO;
import j2ee_project.model.Discount;
import j2ee_project.model.catalog.Category;
import j2ee_project.model.user.Moderator;
import j2ee_project.model.user.TypePermission;
import j2ee_project.service.DTOService;
import j2ee_project.service.catalog.category.CategoryService;
import j2ee_project.service.discount.DiscountService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import static j2ee_project.staticServices.PermissionHelper.getPermission;

@WebServlet("/edit-category")
public class EditCategoryController extends HttpServlet {
    private CategoryService categoryService;
    private DiscountService discountService;

    @Override
    public void init() {
        ApplicationContext context = Application.getContext();
        categoryService = context.getBean(CategoryService.class);
        discountService = context.getBean(DiscountService.class);
    }

    /**
     * Get the page to edit a category
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
            if (obj instanceof Moderator user
                    && user.isAllowed(getPermission(TypePermission.CAN_MANAGE_CATEGORY))) {
                String categoryIdStr = request.getParameter("id");
                int categoryId = -1;

                if (categoryIdStr != null && !categoryIdStr.trim().isEmpty()) {
                    try {
                        categoryId = Integer.parseInt(categoryIdStr);
                    } catch (Exception ignore) {
                    }
                }

                if (categoryId <= 0) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Category ID must be positive");
                }

                Category category = categoryService.getCategory(categoryId);
                request.setAttribute("category", category);
                request.setAttribute("discounts", discountService.getDiscounts());
                RequestDispatcher view = request.getRequestDispatcher("WEB-INF/views/dashboard/edit/editCategory.jsp");
                view.forward(request, response);
            } else {
                response.sendRedirect("dashboard");
            }
        } catch(Exception err) {
            System.err.println(err.getMessage());
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    /**
     * Edit a category to the DB
     * @param request Request object received by the servlet
     * @param response Response to be sent
     * @throws ServletException If the request for the GET could not be handled
     * @throws IOException If an input or output error is detected when the servlet handles the GET request
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String categoryIdStr = request.getParameter("id");
        int categoryId = -1;

        if (categoryIdStr != null && !categoryIdStr.trim().isEmpty()) {
            try {
                categoryId = Integer.parseInt(categoryIdStr);
            } catch (Exception ignore) {
            }
        }

        if (categoryId <= 0) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Category ID must be positive");
        }

        Category category = categoryService.getCategory(categoryId);
        
        

        String discountStr = (request.getParameter("discount").isEmpty()) ? null : request.getParameter(("discount"));
        Discount discount = null;
        if (discountStr != null) {
            int discountId = Integer.parseInt(discountStr);
            discount = discountService.getDiscount(discountId);
        }

        try {

            CategoryDTO categoryDTO = new CategoryDTO(
                    request.getParameter("name"),
                    request.getParameter("description"),
                    discount
            );

            Map<String, String> inputErrors = DTOService.categoryDataValidation(categoryDTO);

            String errorDestination = "WEB-INF/views/dashboard/add/editCategory.jsp";
            RequestDispatcher dispatcher = null;

            if (inputErrors.isEmpty()) {
                try {
                    categoryService.updateCategory(category);
                    response.sendRedirect("dashboard?tab=categories");
                } catch (Exception exception) {
                    System.err.println(exception.getMessage());
                    request.setAttribute("ModificationProcessError", "Error during Modification process");
                    dispatcher = request.getRequestDispatcher(errorDestination);
                    dispatcher.include(request, response);
                }
            } else {
                request.setAttribute("InputError", inputErrors);
                dispatcher = request.getRequestDispatcher(errorDestination);
                dispatcher.include(request, response);
            }

            if (dispatcher != null) doGet(request, response);

        } catch (Exception e) {
            System.err.println(e.getMessage());
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private static Category getCategory(Optional<Category> categoryOptional, CategoryDTO categoryDTO) {
        Category category = categoryOptional.get();
        if (categoryDTO.getName() != null && !categoryDTO.getName().isEmpty()) {
            category.setName(categoryDTO.getName());
        }
        if (categoryDTO.getDescription() != null && !categoryDTO.getDescription().isEmpty()) {
            category.setDescription(categoryDTO.getDescription());
        }
        if (categoryDTO.getDiscount() != null) {
            category.setDiscount(categoryDTO.getDiscount());
        }
        return category;
    }
}