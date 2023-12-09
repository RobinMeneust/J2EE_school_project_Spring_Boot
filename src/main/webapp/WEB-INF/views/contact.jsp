<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: CYTech Student
  Date: 13/11/2023
  Time: 08:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Contact</title>
    <jsp:include page="../../include.jsp"/>
    <script src="${pageContext.request.contextPath}/dependencies/jquery/jquery.validate.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/contact.css">
</head>
<body>
<jsp:include page="../../layout/header.jsp"/>
<main class="container">
    <h1>Contact us</h1>
    <form id="contactForm" name="contactForm" method="post" action="${pageContext.request.contextPath}/contact-controller">
        <c:if test="${requestScope.SuccessSending != null}">
            <div class="alert alert-success" role="alert">
                <c:out value="${requestScope.SuccessSending}"/>
            </div>
        </c:if>
        <c:if test="${requestScope.ContactSendingError != null}">
            <div class="alert alert-danger" role="alert">
                <c:out value="${requestScope.ContactSendingError}"/>
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
            <label for="subject">Subject</label>
            <input type="text" class="form-control ${requestScope.InputError.subject != null ? 'is-invalid' : ''}" id="subject" name="subject" aria-describedby="lastNameHelp" placeholder="Subject" required>
            <div class="invalid-feedback">
                <c:out value="${requestScope.InputError.subject}"/>
            </div>
        </div>
        <div class="form-group">
            <label for="bodyMessage">Message</label>
            <textarea type="text" class="form-control ${requestScope.InputError.bodyMessage != null ? 'is-invalid' : ''}" id="bodyMessage" name="bodyMessage" aria-describedby="phoneNumberHelp" placeholder="Message" required rows="7"></textarea>
            <div class="invalid-feedback">
                <c:out value="${requestScope.InputError.bodyMessage}"/>
            </div>
        </div>

        <button id="submitButton" type="submit" class="btn btn-primary">Submit</button>
    </form>
</main>
<jsp:include page="../../layout/footer.jsp"/>
<script>
    $(function()
    {
        $.validator.addMethod("patternName", function (value, element){
            return this.optional(element) || /^[a-zA-ZÀ-ÖØ-öø-ÿ\-']*$/.test(value);
        }, "Name not valid")


        $("form[name='contactForm']").validate({
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
                Subject: {
                    required: true,
                    maxlength: 64
                },
                bodyMessage: {
                    required: true,
                    maxlength: 256
                }
            },
            messages: {
                firstName: {
                    required: "Please enter your first name",
                    maxLength: "First name can not exceed 30 characters.",
                    patternName: "First name is not valid : only letters and -' are authorized.",
                },
                lastName: {
                    required: "Please enter your last name",
                    maxLength: "Last name can not exceed 30 characters.",
                    patternName: "Last name is not valid : only letters and -' are authorized.",
                },
                email: {
                    required: "Please enter an email address",
                    email: "Email is not valid."
                },
                Subject: {
                    required: "Please provide a password",
                    maxLength: "Subject can not exceed 64 characters.",

                },
                bodyMessage: {
                    required: "Please enter a phone number",
                    maxLength: "Message can not exceed 256 characters.",
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
