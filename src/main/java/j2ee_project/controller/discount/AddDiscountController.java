package j2ee_project.controller.discount;

import j2ee_project.Application;
import j2ee_project.dto.discount.DiscountDTO;
import j2ee_project.model.Discount;
import j2ee_project.model.user.Moderator;
import j2ee_project.model.user.TypePermission;
import j2ee_project.service.DTOService;
import j2ee_project.service.discount.DiscountService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.sql.Date;
import java.util.Map;

import static j2ee_project.staticServices.PermissionHelper.getPermission;

/**
 * This class is a servlet used to add a discount. It's a controller in the MVC architecture of this project.
 */
@WebServlet("/add-discount")
public class AddDiscountController extends HttpServlet {

    private static DiscountService discountService;

    @Override
    public void init() {
        ApplicationContext context = Application.getContext();
        discountService = context.getBean(DiscountService.class);
    }

    /**
     * Get the page to add a discount
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
                    && moderator.isAllowed(getPermission(TypePermission.CAN_MANAGE_DISCOUNT))) {
                RequestDispatcher view = request.getRequestDispatcher("WEB-INF/views/dashboard/add/addDiscount.jsp");
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
     * Add a discount to the DB
     * @param request Request object received by the servlet
     * @param response Response to be sent
     * @throws ServletException If the request for the GET could not be handled
     * @throws IOException If an input or output error is detected when the servlet handles the GET request
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DiscountDTO discountDTO = new DiscountDTO(
                request.getParameter("name"),
                Date.valueOf(request.getParameter("startDate")),
                Date.valueOf(request.getParameter("endDate")),
                Integer.parseInt(request.getParameter("discountPercentage"))
        );

        Map<String, String> inputErrors = DTOService.discountDataValidation(discountDTO);

        String errorDestination = "WEB-INF/views/dashboard/add/addDiscount.jsp";
        RequestDispatcher dispatcher = null;

        if (inputErrors.isEmpty()){
            try {
                discountService.addDiscount(new Discount(discountDTO ));
                response.sendRedirect("dashboard?tab=discounts");
            } catch(Exception exception){
                System.err.println(exception.getMessage());
                request.setAttribute("RegisterProcessError","Error during register process");
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