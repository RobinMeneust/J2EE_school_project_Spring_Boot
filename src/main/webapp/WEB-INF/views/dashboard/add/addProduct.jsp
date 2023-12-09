<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Dashboard</title>
    <jsp:include page="../../../../include.jsp" />
</head>
<body>
    <jsp:include page="../../../../layout/header.jsp"/>
    <div class="d-flex flex-column align-items-center div-form">
        <h2>Add Product</h2>
        <form action="add-product" method="post">
            <div class="row mb-3 input-group" id="div-image">
                <div class="col">
                    <label class="form-label" for="image">Image :</label>
                    <input type="file" class="form-control" id="image" name="image" value="">
                </div>
            </div>
            <div class="row mb-3 input-group" id="div-principal-informations">
                <div class="col">
                    <label class="form-label" for="name">Name :</label>
                    <input type="text" class="form-control" id="name" name="name" value="" required>
                </div>
                <div class="col">
                    <label class="form-label" for="stock-quantity">Stock Quantity :</label>
                    <input type="number" class="form-control" id="stock-quantity" name="stock-quantity" value="" required>
                </div>
                <div class="col">
                    <label class="form-label" for="unit-price">Unit Price :</label>
                    <input type="text" class="form-control" id="unit-price" name="unit-price" value="" required>
                </div>
            </div>
            <div class="row mb-3 input-group" id="div-description">
                <div class="col">
                    <label class="form-label" for="description">Description :</label>
                    <textarea class="form-control" id="description" name="description" rows="3" required></textarea>
                </div>
            </div>
            <div class="row mb-3 input-group" id="div-other-informations">
                <div class="col">
                    <label class="form-label" for="weight">Weight :</label>
                    <input type="text" class="form-control" id="weight" name="weight" value="">
                </div>
                <div class="col">
                    <label class="form-label" for="categoryId">Category :</label>
                    <select class="form-select" id="categoryId" name="category" required>
                        <option value=""></option>
                        <c:set var="categories" value="${requestScope.categories}"/>
                        <c:forEach var="category" items="${categories}">
                            <option id="${category.name}" name="${category.name}" value="${category.id}">${category.name}</option>
                        </c:forEach>
                    </select>
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
