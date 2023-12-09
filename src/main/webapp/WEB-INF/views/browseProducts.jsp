<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="j2ee_project.model.catalog.Product" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ taglib prefix="cf" uri="/WEB-INF/functions.tld"%>
<%--
  Created by IntelliJ IDEA.
  User: robin
  Date: 30/10/2023
  Time: 20:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Products</title>
    <jsp:include page="../../include.jsp" />
    <script src="${pageContext.request.contextPath}/dependencies/rangeSlider/toolcool-range-slider.min.js"></script>
    <script src="${pageContext.request.contextPath}/dependencies/rangeSlider/tcrs-generated-labels.min.js"></script>
    <script src="${pageContext.request.contextPath}/dependencies/rangeSlider/tcrs-moving-tooltip.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/cart.js"></script>
</head>
<body>
<jsp:include page="../../layout/header.jsp" />
<%
    List<Product> products = (List<Product>) request.getAttribute("products");
    if(products == null) {
        products = new ArrayList<>();
    }

    Integer pageIndex = null;
    Object pageIndexObj = request.getAttribute("page");
    if(pageIndexObj instanceof String) {
        pageIndex = Integer.parseInt((String) pageIndexObj);
    } else if(pageIndexObj instanceof Integer) {
        pageIndex = (Integer) pageIndexObj;
    }

    if(pageIndex == null || pageIndex < 0) { // by default the current page is the first one
        pageIndex = 1;
    }

    Long totalPages = (Long) request.getAttribute("totalPages");
    if(totalPages == null) {
        totalPages = 0L;
    }

    String name = request.getParameter("name");
    String category = request.getParameter("category");
    String minPrice = request.getParameter("min-price");
    String maxPrice = request.getParameter("min-price");

    if(minPrice != null && minPrice.trim().isEmpty()) {
        minPrice = null;
    }
    if(maxPrice != null && maxPrice.trim().isEmpty()) {
        maxPrice = null;
    }
%>
<c:set var="customer" value="${cf:getCustomer(user)}"/>
<c:set var="cart" value="${cf:getCart(sessionCart,customer)}"/>

<c:set var="products" value="<%=products%>" />
<c:set var="pageIndex" value="<%=pageIndex%>"/>
<c:set var="totalPages" value="<%=totalPages%>"/>
<c:set var="name" value="<%=name%>"/>
<c:set var="category" value="<%=category%>"/>
<c:set var="minPrice" value="<%=minPrice%>"/>
<c:set var="maxPrice" value="<%=maxPrice%>"/>

<c:url var="currentURLWithoutPage" value="browse-products?">
    <c:forEach var="pageParameter" items="${param}">
        <c:if test="${pageParameter.key != 'page'}">
            <c:param name="${pageParameter.key}" value="${pageParameter.value}"/>
        </c:if>
    </c:forEach>
</c:url>



<div class="container p-3 mt-5" style="min-height:100vh">
    <h1 class="display-1">Browse products</h1>
    <button class="btn btn-outline-primary" type="button" data-bs-toggle="offcanvas" data-bs-target="#offcanvasScrolling" aria-controls="offcanvasScrolling">
        <span class="material-symbols-outlined">filter_alt</span>
    </button>
    <div class="offcanvas offcanvas-start" data-bs-scroll="true" data-bs-backdrop="false" tabindex="-1" id="offcanvasScrolling" aria-labelledby="offcanvasScrollingLabel">
        <div class="offcanvas-header">
            <h5 class="offcanvas-title" id="offcanvasScrollingLabel">Search Filters</h5>
            <button type="button" class="btn-close text-reset" data-bs-dismiss="offcanvas" aria-label="Close"></button>
        </div>
        <div class="offcanvas-body">
            <form action="browse-products" method="get">
                <div class="form-floating mb-2">
                    <input value="${name}" placeholder="Chess" class="form-control" type="text" id="name" name="name">
                    <label for="name">Name</label>
                </div>
                <div class="form-floating mb-2">
                    <input value="${category}" placeholder="Strategy" class="form-control" type="text" id="category" name="category"><br><br>
                    <label for="category">Category</label>
                </div>
                <input type="hidden" id="min-price" name="min-price"/>
                <input type="hidden" id="max-price" name="max-price"/>

                <fieldset class="border p-5">
                    <legend class="w-auto float-none">Price</legend>
                    <tc-range-slider
                            id="priceSlider"
                            min="0"
                            max="100"
                            step="5"
                            value1="0"
                            value2="100"
                            round="0"
                            moving-tooltip="true"
                            moving-tooltip-distance-to-pointer="40"
                            moving-tooltip-width="35"
                            moving-tooltip-height="30"
                            moving-tooltip-bg="#721d82"
                            moving-tooltip-text-color="#efefef"
                            moving-tooltip-units=" €"
                            <c:if test="${minPrice != null && maxPrice != null && minPrice<maxPrice && maxPrice<100 && minPrice>0}">
                                set="[${minPrice},${maxPrice}]"
                            </c:if>
                    ></tc-range-slider>
                </fieldset>
                <input class="btn btn-primary mt-5" type="submit" value="Submit">
            </form>
            <script>
                const priceSlider = document.getElementById('priceSlider');
                const minPriceInput = document.getElementById('min-price');
                const maxPriceInput = document.getElementById('max-price');
                priceSlider.addEventListener('change', (evt) => {
                    minPriceInput.setAttribute("value", evt.detail.value1);
                    maxPriceInput.setAttribute("value", evt.detail.value2);
                });
            </script>
        </div>
    </div>

    <div class="row my-4 g-4 justify-content-start">
        <c:forEach var = "product" items="${products}">
            <div class="col my-3 mx-2" style="max-width:400px;">
                <div class="card hover-shadow hover-zoom" style="width: 390px; height:390px;">
                    <a href="get-product-page?id=<c:out value="${product.getId()}"/>" style="text-decoration: none">
                        <img style="width: 390px; height: 250px; object-fit: contain;" alt="product_img" src="<c:out value="product/image?id=${product.getId()}" />" class="card-img-top">
                    </a>
                    <div class="card-body">
                        <div class="d-flex justify-content-between">
                            <span class="font-weight-bold"><c:out value="${product.getName()}" /></span>
                            <span class="font-weight-bold"><fmt:formatNumber type = "number" maxFractionDigits  = "2" value = "${product.getUnitPrice()}"/> €</span>
                        </div>
                        <p class="card-text text-success mb-1 mt-1">
                            <c:if test="${not empty product.getCategory().getDiscount() && product.getCategory().getDiscount().getDiscountPercentage() > 0}">
                                <c:out value="-${product.getCategory().getDiscount().getDiscountPercentage()} %"/>
                            </c:if>
                        </p>
                        <p class="card-text text-success mb-1 mt-1">
                            <c:choose>
                                <c:when test="${product.getStockQuantity()==0}">
                                    <button class="btn btn-danger" disabled>Out of stock</button>
                                </c:when>
                                <c:when test="${cart != null && cart.getCartItems() != null && cart.containsProduct(product.getId())}">
                                    <button class="btn btn-success" disabled>Already in cart</button>
                                </c:when>
                                <c:otherwise>
                                    <button onclick="addToCart(this, ${product.getId()})" class="btn btn-primary">Add to cart</button>
                                </c:otherwise>
                            </c:choose>
                        </p>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>

    <nav>
        <ul class="pagination justify-content-center">
            <c:if test="${pageIndex>1}">
                <li class="page-item"><a class="page-link" href="${currentURLWithoutPage}page=1">First</a></li>
                <li class="page-item">
                    <a class="page-link" href="${currentURLWithoutPage}page=${pageIndex-1}" aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                </li>
            </c:if>

            <c:forEach var="i" begin="${Math.max(1,pageIndex-3)}" end="${Math.min(pageIndex+3, totalPages)}" step="1">
                <c:choose>
                    <c:when test="${i == pageIndex}">
                        <li class="page-item active"><a class="page-link" href="${currentURLWithoutPage}page=<c:out value="${i}"/>"><c:out value="${i}"/></a></li>
                    </c:when>
                    <c:otherwise>
                        <li class="page-item"><a class="page-link" href="${currentURLWithoutPage}page=<c:out value="${i}"/>"><c:out value="${i}"/></a></li>
                    </c:otherwise>
                </c:choose>
            </c:forEach>

            <c:if test="${pageIndex<totalPages}">
                <li class="page-item">
                    <a class="page-link" href="${currentURLWithoutPage}page=${pageIndex+1}" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </li>
                <li class="page-item"><a class="page-link" href="${currentURLWithoutPage}page=<c:out value="${totalPages}"/>">Last</a></li>
            </c:if>
        </ul>
    </nav>
</div>
<jsp:include page="../../layout/footer.jsp" />
</body>
</html>
