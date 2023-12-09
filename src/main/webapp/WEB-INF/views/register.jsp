<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: CYTech Student
  Date: 05/11/2023
  Time: 18:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
    <jsp:include page="../../include.jsp"/>
    <script src="${pageContext.request.contextPath}/dependencies/jquery/jquery.validate.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/auth_pages.css">
</head>
<body>
    <jsp:include page="../../layout/header.jsp"/>
    <main class="container">
        <h1>Register</h1>
        <form id="registerForm" name="registerForm" method="post" action="${pageContext.request.contextPath}/register-customer-controller">
            <c:if test="${requestScope.emailOrPhoneNumberInDbError != null}">
                <div class="alert alert-danger" role="alert">
                    <c:out value="${requestScope.emailOrPhoneNumberInDbError}"/>
                </div>
            </c:if>
            <c:if test="${requestScope.RegisterProcessError != null}">
                <div class="alert alert-danger" role="alert">
                    <c:out value="${requestScope.RegisterProcessError}"/>
                </div>
            </c:if>
            <div class="form-group">
                <label for="firstName">First name</label>
                <input type="text" class="form-control ${requestScope.InputError.firstName != null? 'is-invalid' : ''}" id="firstName" name="firstName" aria-describedby="firstNameHelp" placeholder="First name" required>
                <div class="invalid-feedback">
                    <c:out value="${requestScope.InputError.firstName}"/>
                </div>
            </div>
            <div class="form-group">
                <label for="lastName">Last name</label>
                <input type="text" class="form-control ${requestScope.InputError.lastName != null ? 'is-invalid' : ''}" id="lastName" name="lastName" aria-describedby="lastNameHelp" placeholder="Last name" required>
                <div class="invalid-feedback">
                    <c:out value="${requestScope.InputError.lastName}"/>
                </div>
            </div>
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
            <div class="form-group">
                <label for="confirmPassword">Password confirmation</label>
                <input type="password" class="form-control ${requestScope.InputError.confirmPassword != null ? 'is-invalid' : ''}" id="confirmPassword" name="confirmPassword" aria-describedby="confirmPasswordHelp" placeholder="Password confirmation" required>
                <div class="invalid-feedback">
                    <c:out value="${requestScope.InputError.confirmPassword}"/>
                </div>
            </div>
            <div class="form-group">
                <label for="phoneNumber">Phone number</label>
                <input type="text" class="form-control ${requestScope.InputError.phoneNumber != null ? 'is-invalid' : ''}" id="phoneNumber" name="phoneNumber" aria-describedby="phoneNumberHelp" placeholder="Phone number" required>
                <div class="invalid-feedback">
                    <c:out value="${requestScope.InputError.phoneNumber}"/>
                </div>
            </div>

            <button id="submitButton" type="submit" class="btn btn-primary">Submit</button>
        </form>
    </main>
    <jsp:include page="../../layout/footer.jsp"/>
    <script>
        /*$.validator.addMethod("patternName", function (value){
            return value.match(/^[a-zA-ZÀ-ÖØ-öø-ÿ\-']*$/) != null;
        }, "Name not valid")
        $.validator.addMethod("patternPassword", function (value){
            return value.match(/^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,24}$/) != null;
        }, "Password is not valid : it needs letters, numbers, special characters @$!%*#?& and length between 8 and 24.")
        $.validator.addMethod("patternPhoneNumber", function (value){
            return value.match(/^[0-9]{10}$/) != null;
        }, "Phone number must be composed by 10 numbers with this format : 0000000000")*/
        $(function()
        {
            $.validator.addMethod("patternName", function (value, element){
                return this.optional(element) || /^[a-zA-ZÀ-ÖØ-öø-ÿ\-']*$/.test(value);
            }, "Name not valid")
            $.validator.addMethod("patternPassword", function (value){
                return value.match(/^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,24}$/) != null;
            }, "Password is not valid : it needs letters, numbers, special characters @$!%*#?& and length between 8 and 24.")
            $.validator.addMethod("patternPhoneNumber", function (value){
                return value.match(/^[0-9]{10}$/) != null;
            }, "Phone number must be composed by 10 numbers with this format : 0000000000")

            $("form[name='registerForm']").validate({
                rules: {
                    firstName: {
                        required: true,
                        maxlength: 30,
                        patternName: true,
                    },
                    lastName: {
                        required: true,
                        maxlength: 30,
                        patternName: true,
                    },
                    email: {
                        required: true,
                        email: true
                    },
                    password: {
                        required: true,
                        patternPassword: true
                    },
                    confirmPassword: {
                        required: true,
                        equalTo: "#password"
                    },
                    phoneNumber: {
                        required: true,
                        patternPhoneNumber: true
                    }
                },
                messages: {
                    firstName: {
                        required: "Please enter your first name",
                        max: "First name can not exceed 30 characters.",
                        patternName: "First name is not valid : only letters and -' are authorized.",
                    },
                    lastName: {
                        required: "Please enter your last name",
                        max: "Last name can not exceed 30 characters.",
                        patternName: "Last name is not valid : only letters and -' are authorized.",
                    },
                    email: {
                        required: "Please enter an email address",
                        email: "Email is not valid."
                    },
                    password: {
                        required: "Please provide a password",
                    },
                    confirmPassword: {
                        required: "Please enter a confirmation password",
                        equalTo: "Password and Confirm Password must match."
                    },
                    phoneNumber: {
                        required: "Please enter a phone number",
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
