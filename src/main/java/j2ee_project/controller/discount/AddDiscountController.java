package j2ee_project.controller.discount;

import j2ee_project.dao.discount.DiscountDAO;
import j2ee_project.model.Discount;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.Date;

/**
 * This class is a servlet used to add a discount. It's a controller in the MVC architecture of this project.
 */
@WebServlet("/add-discount")
public class AddDiscountController extends HttpServlet {
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
            RequestDispatcher view = request.getRequestDispatcher("WEB-INF/views/dashboard/add/addDiscount.jsp");
            view.forward(request,response);
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
        Discount discount = new Discount();

        discount.setName(request.getParameter("name"));

        Date startDate = Date.valueOf(request.getParameter("start-date"));
        discount.setStartDate(startDate);
        Date endDate = Date.valueOf(request.getParameter("end-date"));
        discount.setEndDate(endDate);

        discount.setDiscountPercentage(Integer.parseInt(request.getParameter("discount-percentage")));
        DiscountDAO.addDiscount(discount);

        try {
            response.sendRedirect("dashboard?tab=discounts");
        }catch (Exception err){
            System.err.println(err.getMessage());
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}