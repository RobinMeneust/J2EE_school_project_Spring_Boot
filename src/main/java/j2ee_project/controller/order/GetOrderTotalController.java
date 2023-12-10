package j2ee_project.controller.order;

import j2ee_project.Application;
import j2ee_project.model.order.Orders;
import j2ee_project.model.user.Customer;
import j2ee_project.model.user.User;
import j2ee_project.service.AuthService;
import j2ee_project.service.OrdersManager;
import j2ee_project.service.order.OrdersService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Get the order total price as a JSON object. It's a controller in the MVC architecture of this project.
 *
 * @author Robin Meneust
 */
@WebServlet("/get-order-total")
public class GetOrderTotalController extends HttpServlet {

    private static OrdersService ordersService;
    private static AuthService authService;

    @Override
    public void init() {
        ApplicationContext context = Application.getContext();
        ordersService = context.getBean(OrdersService.class);
        authService = context.getBean(AuthService.class);
    }

    /**
     * Get the total price of the order associated to the given "order-id" as a JSON object with the key "total"
     * @param request Request object received by the servlet
     * @param response Response to be sent
     * @throws ServletException If the request for the GET could not be handled
     * @throws IOException If an input or output error is detected when the servlet handles the GET request
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        int orderId = Integer.parseInt(request.getParameter("order-id"));
        Orders order = ordersService.getOrder(orderId);

        if(order == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No order is associated to this ID");
            return;
        }

        Object obj = session.getAttribute("user");
        Customer customer = null;
        if(obj instanceof User) {
            customer = authService.getCustomer((User) obj);
        }

        String error = OrdersManager.checkOrder(order, customer);

        if(error != null && !error.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, error);
            return;
        }

        PrintWriter out = response.getWriter();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("total",order.getTotal());
        out.print(jsonObject);
        out.flush();
    }
}
