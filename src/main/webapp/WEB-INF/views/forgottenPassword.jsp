<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: CYTech Student
  Date: 22/11/2023
  Time: 12:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Forgotten password</title>
    <jsp:include page="../../include.jsp"/>
    <script src="${pageContext.request.contextPath}/dependencies/jquery/jquery.validate.min.js"></script>
</head>
<body>
<jsp:include page="../../layout/header.jsp"/>
<main  class="container">
    <h1>Forgotten password</h1>
    <form id="forgottenPasswordForm" method="post" action="${pageContext.request.contextPath}/forgotten-password-controller">
        <c:if test="${requestScope.errorMessage != null}">
            <div class="alert alert-danger" role="alert">
                <c:out value="${requestScope.errorMessage}"/>
            </div>
        </c:if>
        <c:if test="${requestScope.noForgottenPassword != null}">
            <div class="alert alert-danger" role="alert">
                <c:out value="${requestScope.noForgottenPassword}"/>
            </div>
        </c:if>
        <div class="form-group">
            <label for="email">Email address</label>
            <input type="email" class="form-control ${requestScope.InputError.email != null ? 'is-invalid' : ''}" id="email" name="email" aria-describedby="emailHelp" placeholder="Email" required>
            <div class="invalid-feedback">
                <c:out value="${requestScope.InputError.email}"/>
            </div>
        </div>
        <button id="submitButton" type="submit" class="btn btn-primary">Submit</button>
    </form>
</main>
<jsp:include page="../../layout/footer.jsp"/>
<script>
    $(document).ready(function(){
        $("#loginForm").validate({
            rules: {
                email: {
                    required: true,
                    email: true
                },
            },
            messages: {
                email: {
                    required: "Last name can not be blank.",
                    email: "Email is not valid."
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
