<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Dashboard</title>
    <jsp:include page="../../../../include.jsp" />
    <script src="${pageContext.request.contextPath}/dependencies/jquery/jquery.validate.min.js"></script>
    <style>
        .custom-file-input {
            visibility: hidden;
            position: absolute;
        }

        .custom-file-label {
            cursor: pointer;
            display: inline-block;
        }
    </style>
    <script>
        function updateProductImage(input){
            let form = document.getElementById('updateImage');
            form.submit();
        }
    </script>
</head>
<body>
<jsp:include page="../../../../layout/header.jsp"/>
<c:set var="product" value="${requestScope.product}"/>
<div class="d-flex flex-column align-items-center div-form">
    <h2>Edit Product</h2>
    <div>
        <img style="width: 156px; height: 100px; object-fit: contain;"
              alt="product_img"
              src="<c:out value="product/image?id=${product.id}"/>"
             class="card-img-top">
        <form id="updateImage" action="product/image?id=${product.id}" method="post" enctype="multipart/form-data">
            <label class="custom-file-label btn btn-primary" for="file">Upload new image</label>
            <input class="custom-file-input" type="file" id="file" name="file" accept="image/*" onchange="updateProductImage(this)">
        </form>
    </div>
    <form class="d-flex align-items-center flex-column flex-wrap" id="edit-product-form" name="edit-product-form" action="edit-product?id=${product.id}" method="post">
        <div class="row mb-3 input-group" id="div-main-informations">
            <div class="col">
                <label class="form-label" for="name">Name :</label>
                <input type="text" class="form-control ${requestScope.InputError.name != null? 'is-invalid' : ''}" id="name" name="name" aria-describedby="nameHelp" value="${product.name}" required>
                <div class="invalid-feedback">
                    <c:out value="${requestScope.InputError.name}"/>
                </div>
            </div>
            <div class="col">
                <label class="form-label" for="stock-quantity">Stock Quantity :</label>
                <input type="number" class="form-control ${requestScope.InputError.stockQuantity != null? 'is-invalid' : ''}" id="stock-quantity" name="stockQuantity" aria-describedby="stockQuantityHelp" value="${product.stockQuantity}" required>
                <div class="invalid-feedback">
                    <c:out value="${requestScope.InputError.stockQuantity}"/>
                </div>
            </div>
            <div class="col">
                <label class="form-label" for="unit-price">Unit Price :</label>
                <input type="text" class="form-control ${requestScope.InputError.unitPrice != null? 'is-invalid' : ''}" id="unit-price" name="unitPrice" aria-describedby="unitPriceHelp" value="${product.unitPrice}" required>
                <div class="invalid-feedback">
                    <c:out value="${requestScope.InputError.unitPrice}"/>
                </div>
            </div>
        </div>
        <div class="row mb-3 input-group" id="div-description">
            <div class="col">
                <label class="form-label" for="description">Description :</label>
                <textarea class="form-control ${requestScope.InputError.description != null? 'is-invalid' : ''}" id="description" name="description" aria-describedby="descriptionHelp" rows="3" required>${product.description}</textarea>
                <div class="invalid-feedback">
                    <c:out value="${requestScope.InputError.description}"/>
                </div>
            </div>
        </div>
        <div class="row mb-3 input-group" id="div-other-informations">
            <div class="col">
                <label class="form-label" for="weight">Weight :</label>
                <input type="text" class="form-control ${requestScope.InputError.weight != null? 'is-invalid' : ''}" id="weight" name="weight" aria-describedby="weightHelp" value="${product.weight}">
                <div class="invalid-feedback">
                    <c:out value="${requestScope.InputError.weight}"/>
                </div>
            </div>
            <div class="col">
                <label class="form-label" for="categoryId">Category :</label>
                <select class="form-select" id="categoryId" name="category" aria-describedby="categoryHelp" required>
                    <option value=""></option>
                    <c:set var="categories" value="${requestScope.categories}"/>
                    <c:forEach var="category" items="${categories}">
                        <option id="${category.name}" name="${category.name}" value="${category.id}" <c:if test="${product.category.name eq category.name}">selected</c:if>>${category.name}</option>
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
<script>
    $(function()
    {
        $.validator.addMethod("patternName", function (value, element){
            return this.optional(element) || /^[a-zA-ZÀ-ÖØ-öø-ÿ\-' ]*$/.test(value);
        }, "Name not valid.")
        $.validator.addMethod("patternStockQuantity", function (value, element){
            return this.optional(element) || /^[0-9]*$/.test(value);
        }, "Stock quantity is not valid.")
        $.validator.addMethod("patternUnitPrice", function (value, element){
            return this.optional(element) || /^\d+(\.\d{1,2})?$/.test(value);
        }, "Unit price is not valid.")
        $.validator.addMethod("patternWeight", function (value, element){
            return this.optional(element) || /^\d+(\.\d*)?$/.test(value);
        }, "Name not valid")


        $("form[name='edit-product-form']").validate({
            rules: {
                name: {
                    required: true,
                    maxlength: 30,
                    patternName: true,
                },
                stockQuantity: {
                    required: true,
                    patternStockQuantity: true
                },
                unitPrice: {
                    required: true,
                    patternUnitPrice: true
                },
                description: {
                    maxlength: 300
                },
                weight: {
                    patternWeight: true
                }
            },
            messages: {
                name: {
                    required: "Please enter a product name",
                    max: "Name can not exceed 30 characters.",
                    patternName: "Name is not valid : only letters and -' are authorized.",
                },
                stockQuantity: {
                    required: "Please enter a stock quantity.",
                    patternStockQuantity: "stock quantity is not valid."
                },
                unitPrice: {
                    required: "Please enter a unit price.",
                    patternUnitPrice: "Unit price is not valid."
                },
                description: {
                    max : "Description can not exceed 300 characters."
                },
                weight: {
                    patternWeight: "Weight is not valid."
                }
            },
            submitHandler: function(form) {
                form.submit();
            }
        })
    })
</script>
</body>
</html>
