<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Dashboard</title>
    <jsp:include page="../../../../include.jsp" />
    <script src="${pageContext.request.contextPath}/dependencies/jquery/jquery.validate.min.js"></script>
</head>
<body>
    <jsp:include page="../../../../layout/header.jsp" />
    <div class="d-flex flex-column align-items-center div-form">
        <h2>Add Category</h2>
        <form class="d-flex align-items-center flex-column flex-wrap" id="add-category-form" name="add-category-form" action="add-category" method="post">
            <div class="row mb-3 input-group" id="div-information">
                <div class="col">
                    <label class="form-label" for="name">Name :</label>
                    <input type="text" class="form-control ${requestScope.InputError.name != null? 'is-invalid' : ''}" id="name" name="name" aria-describedby="nameHelp" placeholder="Enter category name" required>
                    <div class="invalid-feedback">
                        <c:out value="${requestScope.InputError.name}"/>
                    </div>
                </div>
                <div class="col">
                    <label class="form-label" for="discountId">Discount :</label>
                    <select class="form-select" id="discountId" name="discount" aria-describedby="discountHelp">
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
                    <textarea class="form-control ${requestScope.InputError.description != null? 'is-invalid' : ''}" id="description" name="description" aria-describedby="descriptionHelp" rows="3" required></textarea>
                    <div class="invalid-feedback">
                        <c:out value="${requestScope.InputError.description}"/>
                    </div>
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
                return this.optional(element) || /^[a-zA-ZÀ-ÖØ-öø-ÿ\-']*$/.test(value);
            }, "Name not valid.")

            $("form[name='add-category-form']").validate({
                rules: {
                    name: {
                        required: true,
                        maxlength: 30,
                        patternName: true,
                    },
                    description: {
                        maxlength: 300
                    }
                },
                messages: {
                    name: {
                        required: "Please enter a category name",
                        max: "Name can not exceed 30 characters.",
                        patternName: "Name is not valid : only letters and -' are authorized.",
                    },
                    description: {
                        max : "Description can not exceed 300 characters."
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
