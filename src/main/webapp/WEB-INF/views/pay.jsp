<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cf" uri="/WEB-INF/functions.tld"%>
<%@ page import="j2ee_project.repository.order.OrdersDAO"%>
<%@ page import="j2ee_project.model.order.Orders"%>

<%--
  Created by IntelliJ IDEA.
  User: robin
  Date: 01/11/2023
  Time: 00:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Pay</title>
    <jsp:include page="../../include.jsp" />
    <script src="https://js.stripe.com/v3/"></script>
    <script src="${pageContext.request.contextPath}/js/checkout.js" defer></script>
</head>
<body>
<jsp:include page="../../layout/header.jsp" />
<div class="container p-3 mt-5" style="min-height:100vh">
    <%
        String orderIdStr = request.getParameter("order-id");
        try {
            Integer.parseInt(orderIdStr); // Check if the string corresponds to an integer
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,"order-id param is required and must be an integer");
        }
        Orders order = OrdersDAO.getOrder(orderIdStr);

        if(order == null || order.getOrderItems() == null || order.getOrderItems().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,"The order associated to order-id is invalid or empty");
        }
    %>
    <div id="error-alert-box" class="my-5 alert alert-warning fade d-none" role="alert">
        <span>An issue occurred. Please check your internet connection or contact us</span>
    </div>
    <div id="main-content" class="d-none">
        <c:set var="order" value="<%=order%>"/>
        <%--For testing purposes use the card number: 4242424242424242--%>
        <p>
            You need to pay <span id="amount-to-be-paid"></span> €<br>
            A receipt will be sent to you to your email address
        </p>

        <div>
            <h5>Order:</h5>
            <ul class="list-group">
                <c:forEach items="${order.getOrderItems()}" var="item">
                    <li class="list-group-item d-flex justify-content-between align-items-center">
                        ${item.getProduct().getName()}
                        <span class="badge bg-primary rounded-pill">${item.getQuantity()}</span>
                    </li>
                </c:forEach>
            </ul>
        </div>

        <form id="payment-form">
            <div id="payment-element" class="me-5 my-4 p-4 w-50" style="background-color: lightgray">
                <!--Stripe.js injects the Payment Element-->
            </div>

            <button id="submit" class="pay-btn btn btn-primary" type="submit">
                <span class="spinner-pay-btn spinner-border spinner-border-sm visually-hidden" aria-hidden="true"></span>
                <span class="spinner-pay-btn visually-hidden" role="status">Loading...</span>
                <span id="pay-btn-text">Pay now</span>
            </button>

            <div id="payment-message" class="visually-hidden"></div>
        </form>
    </div>
</div>
<jsp:include page="../../layout/footer.jsp" />
</body>
</html>
