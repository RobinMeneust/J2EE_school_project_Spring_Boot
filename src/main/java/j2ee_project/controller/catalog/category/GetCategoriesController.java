package j2ee_project.controller.catalog.category;

import j2ee_project.Application;
import j2ee_project.service.catalog.category.CategoryService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.springframework.context.ApplicationContext;

import java.io.IOException;

/**
 * This class is a servlet used to get a list of the categories. It's a controller in the MVC architecture of this project.
 */
@WebServlet("/get-categories")
public class GetCategoriesController extends HttpServlet {
    private static CategoryService categoryService;

    @Override
    public void init() {
        ApplicationContext context = Application.getContext();
        categoryService = context.getBean(CategoryService.class);
    }
    /**
     * Get the categories list
     * @param request Request object received by the servlet
     * @param response Response to be sent
     * @throws ServletException If the request for the GET could not be handled
     * @throws IOException If an input or output error is detected when the servlet handles the GET request
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setAttribute("categories", categoryService.getCategories());
        }catch (Exception err){
            System.err.println(err.getMessage());
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}