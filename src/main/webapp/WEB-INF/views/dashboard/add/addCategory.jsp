<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Dashboard</title>
    <jsp:include page="../../../../include.jsp" />
</head>
<body>
    <jsp:include page="../../../../layout/header.jsp" />
    <div class="d-flex flex-column align-items-center div-form">
        <h2>Add Category</h2>
        <form id="add-category-form" name="add-category-form" action="add-category" method="post">
            <div class="row mb-3 input-group" id="div-information">
                <div class="col">
                    <label class="form-label" for="name">Name :</label>
                    <input type="text" class="form-control" id="name" name="name" placeholder="Enter category name" required>
                </div>
                <div class="col">
                    <label class="form-label" for="discountId">Discount :</label>
                    <select class="form-select" id="discountId" name="discount">
                        <option value=""></option>
                        <c:set var="discounts" value="${requestScope.discounts}"/>
                        <c:forEach var="discount" items="${discounts}">
                            <option id="${discount.name}" name="${discount.name}" value="${discount.id}">${discount.name}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="row mb-3 input-group" id="div-description">
                <div class="col">
                    <label class="form-label" for="description">Description :</label>
                    <textarea class="form-control" id="description" name="description" rows="3" required></textarea>
                </div>
            </div>
            <button type="submit" class="btn btn-primary">
                Submit
            </button>
        </form>
    </div>
    <jsp:include page="../../../../layout/footer.jsp" />
</body>
</html>
