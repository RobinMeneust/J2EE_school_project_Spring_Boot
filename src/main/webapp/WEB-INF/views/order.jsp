<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="cf" uri="/WEB-INF/functions.tld"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="j2ee_project.model.order.OrderStatus" %>
<!DOCTYPE html>
<html>
<head>
    <title>Order</title>
    <jsp:include page="../../include.jsp" />
</head>
<body>
<jsp:include page="../../layout/header.jsp" />
<div class="container p-3 mt-5" style="min-height:100vh">
    <c:choose>
        <c:when test="${order != null && order.getOrderItems() != null && !order.getOrderItems().isEmpty()}">
            <c:if test="${order.getOrderStatus() == OrderStatus.CANCELLED}">
                <div class="text-danger bold p-2">
                    This order has been cancelled because our shop could not accept it. It might be because of an expired discount.
                </div>
            </c:if>

            <div class="card">
                <div class="card-body mx-3">
                    <div class="container">
                        <h3 class="mx-3 my-4 display-3">Order n°<c:out value="${order.getId()}"/></h3>
                        <p class="my-2">Date: <c:out value="${order.getDate()}"/></p>
                        <table class="table mt-5">
                            <tr>
                                <th class="align-middle col-8">Item</th>
                                <th class="text-center align-middle col-2">Quantity</th>
                                <th class="text-center align-middle col-2">Amount paid</th>
                            </tr>
                            <c:forEach items="${order.getOrderItems()}" var="item">
                                <tr>
                                    <td class="align-middle col-8"><c:out value="${item.getProduct().getName()}"/></td>
                                    <td class="text-center align-middle col-2"><c:out value="${item.getQuantity()}"/></td>
                                    <td class="text-center align-middle col-2"><fmt:formatNumber type = "number" maxFractionDigits  = "2" value = "${item.getTotal()}"/> €</td>
                                </tr>
                            </c:forEach>
                            <tr>
                                <td class="align-middle col-8">Shipment fees</td>
                                <td class="text-center align-middle col-2"></td>
                                <td class="text-center align-middle col-2">5 €</td>
                            </tr>
                        </table>
                        <div class="row">
                            <div class="col-12">
                                <p class="float-end fw-bold">Total: <fmt:formatNumber type = "number" maxFractionDigits  = "2" value = "${order.getTotal()}"/> €</p>
                            </div>
                            <hr>
                        </div>
                    </div>
                    <c:if test="${order.getOrderStatus() == OrderStatus.WAITING_PAYMENT}">
                        <div class="container">
                            <a class="btn btn-primary" href="${pageContext.request.contextPath}/pay?order-id=${order.getId()}" role="button">Pay</a>
                        </div>
                    </c:if>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <div id="error-alert-box" class="my-5 alert alert-warning fade show" role="alert">
                <span>Your order is empty.</span>
            </div>
        </c:otherwise>
    </c:choose>
</div>
<jsp:include page="../../layout/footer.jsp" />
</body>
</html>
