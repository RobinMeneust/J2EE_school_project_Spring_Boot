<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: CYTech Student
  Date: 11/11/2023
  Time: 16:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <jsp:include page="../../include.jsp"/>
    <script src="${pageContext.request.contextPath}/dependencies/jquery/jquery.validate.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/auth_pages.css">
</head>
<body>
    <jsp:include page="../../layout/header.jsp"/>
<main class="container">
    <h1>Login</h1>
    <form id="loginForm" method="post" action="${pageContext.request.contextPath}/login-controller">
        <c:if test="${requestScope.LoggingProcessError != null}">
            <div class="alert alert-danger" role="alert">
                <c:out value="${requestScope.LoggingProcessError}"/>
            </div>
        </c:if>
        <div class="form-group">
            <label for="email">Email address</label>
            <input type="email" class="form-control ${requestScope.InputError.email != null ? 'is-invalid' : ''}" id="email" name="email" aria-describedby="emailHelp" placeholder="Email" required>
            <div class="invalid-feedback">
                <c:out value="${requestScope.InputError.email}"/>
            </div>
        </div>
        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" class="form-control ${requestScope.InputError.password != null ? 'is-invalid' : ''}" id="password" name="password" aria-describedby="passwordHelp" placeholder="Password" required>
            <div class="invalid-feedback">
                <c:out value="${requestScope.InputError.password}"/>
            </div>
        </div>
        <div>
            <a href="forgotten-password">Forgotten password</a>
        </div>
        <button id="submitButton" type="submit" class="btn btn-primary">Submit</button>
    </form>
</main>
    <jsp:include page="../../layout/footer.jsp"/>
<script>
    $(document).ready(function(){
        $.validator.addMethod("patternPassword", function (value){
            return value.match(/^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,24}$/) != null;
        }, "Password is not valid : it needs letters, numbers, special characters @$!%*#?& and length between 8 and 24.")
        $("#loginForm").validate({
            rules: {
                email: {
                    required: true,
                    email: true
                },
                password: {
                    required: true,
                    patternPassword: true
                },
            },
            messages: {
                email: {
                    required: "Last name can not be blank.",
                    email: "Email is not valid."
                },
                password: {
                    required: "Please provide a password",
                },
            },
            submitHandler: function(form) {
                form.submit();
            }
        })
    })
</script>
</body>
</html>


