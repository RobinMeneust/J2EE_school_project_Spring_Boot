<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cf" uri="/WEB-INF/functions.tld"%>
<%--
  Created by IntelliJ IDEA.
  User: robin
  Date: 03/11/2023
  Time: 01:16
  To change this template use File | Settings | File Templates.
--%>
<c:set var="customer" value="${cf:getCustomer(user)}"/>
<footer class="d-flex flex-wrap justify-content-between align-items-center py-3 my-4 border-top">
    <div class="container">
        <p class="col-md-4 mb-0 text-muted">&copy; 2023 Boarder Games</p>

        <ul class="nav col-md-4 justify-content-end">
            <li class="nav-item"><a href="${pageContext.request.contextPath}" class="nav-link px-2 text-muted">Home</a></li>
            <li class="nav-item"><a href="browse-products" class="nav-link px-2 text-muted">Products</a></li>
            <c:if test="${not empty customer}">
                <li class="nav-item"><a href="profile-informations?customerId=${customer.id}" class="nav-link px-2 text-muted">Profile</a></li>
            </c:if>
            <li class="nav-item"><a href="cart" class="nav-link px-2 text-muted">Cart</a></li>
            <li class="nav-item"><a href="contact" class="nav-link px-2 text-muted">Contact</a></li>
        </ul>
    </div>
</footer>
