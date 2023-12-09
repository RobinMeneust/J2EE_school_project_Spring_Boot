<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="j2ee_project.model.catalog.Product" %>
<%@ page import="j2ee_project.model.Discount" %>
<%@ taglib prefix="cf" uri="/WEB-INF/functions.tld"%>
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
    <title>Product</title>
    <jsp:include page="../../include.jsp" />
    <script src="${pageContext.request.contextPath}/js/cart.js"></script>
</head>
<body>
<jsp:include page="../../layout/header.jsp" />
<%
    Product product = (Product) request.getAttribute("product");
    Discount discount = product.getCategory().getDiscount();
    Integer discountPercentage = 0;
    if(discount != null) {
        discountPercentage = discount.getDiscountPercentage();
    }
%>

<c:set var="product" value="<%=product%>"/>
<c:set var="discountPercentage" value="<%=discountPercentage%>"/>
<c:set var="customer" value="${cf:getCustomer(user)}"/>
<c:set var="cart" value="${cf:getCart(sessionCart,customer)}"/>


<div class="container p-3 mt-5" style="min-height:100vh">
    <div id="error-alert-box" class="alert invisible alert-warning alert-dismissible fade" role="alert">
        <strong>Failure</strong> The product could not be added to the cart.
        <button type="button" class="close btn" data-bs-dismiss="alert" aria-label="Close">
            <span aria-hidden="true">&times;</span>
        </button>
    </div>
    <h3 class="display-3"><c:out value="${product.getName()}"/></h3>
    <h6 class="display-6 mb-3 text-secondary"><c:out value="${product.getCategory().getName()}"/></h6>
    <div class="row g-5 justify-content-start">
        <div class="col">
            <img class="rounded" style="width: 420px; height: 300px; object-fit: contain;" alt="product_img" src="<c:out value="product/image?id=${product.getId()}" />">
        </div>
        <div class="col" style="text-align: justify ;min-width:350px; max-width:600px">
            <p>
                <c:out value="${product.getDescription()}" />
            </p>
        </div>
        <div class="col bg-secondary-subtle shadow p-3 rounded d-flex align-items-start flex-column" style="min-width:250px; max-width:450px">
            <div class="p-2 mb-auto">
                <c:choose>
                    <c:when test="${discountPercentage != null && discountPercentage > 0}">
                        <span class="text-secondary text-decoration-line-through"><fmt:formatNumber type = "number" maxFractionDigits  = "2" value = "${product.getUnitPrice()}"/> €</span> <span class="text-success"><c:out value="(-${discountPercentage} %)"/></span>
                        <h6 class="display-6"><fmt:formatNumber type = "number" maxFractionDigits  = "2" value = "${product.getUnitPrice()*(1-(discountPercentage/100))}"/> €</h6>
                    </c:when>
                    <c:otherwise>
                        <h6 class="display-6"><fmt:formatNumber type = "number" maxFractionDigits  = "2" value = "${product.getUnitPrice()}"/> €</h6>
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="p-2 mb-auto w-100">
                <div class="row">
                    <div class="col text-start"><span class="material-symbols-outlined">local_shipping</span> <span>Home delivery</span></div>
                    <div class="col text-end">5.00 € Shipping</div>
                </div>
            </div>
            <c:choose>
                <c:when test="${product.getStockQuantity()==0}">
                    <button class="btn btn-danger" disabled>Out of stock</button>
                </c:when>
                <c:when test="${cart != null && cart.getCartItems() != null && cart.containsProduct(product.getId())}">
                    <button class="btn btn-success w-100" disabled>Already in cart</button>
                </c:when>
                <c:otherwise>
                    <button onclick="addToCart(this, ${product.getId()})" class="btn btn-primary w-100">Add to cart</button>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>
<jsp:include page="../../layout/footer.jsp" />
</body>
</html>
