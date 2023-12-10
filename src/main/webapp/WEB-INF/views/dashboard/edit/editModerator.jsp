<%@ page import="j2ee_project.model.user.TypePermission" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Dashboard</title>
    <jsp:include page="../../../../include.jsp" />
    <script src="${pageContext.request.contextPath}/dependencies/jquery/jquery.validate.min.js"></script>
</head>
<body>
<jsp:include page="../../../../layout/header.jsp" />
<c:set var="permissions" value="<%=TypePermission.values()%>"/>
<c:set var="moderator" value="${requestScope.moderator}"/>
<c:set var="permissionNameList" value="${requestScope.permissionNameList}"/>
<div class="d-flex flex-column align-items-center div-form">
    <h2>Edit Moderator</h2>
    <form class="d-flex align-items-center flex-column flex-wrap" id="edit-moderator-form" name="edit-moderator-form" action="edit-moderator?id=${moderator.id}" method="post">
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
        <div class="row mb-3 input-group" id="div-name">
            <div class="col">
                <label class="form-label" for="first-name">First Name :</label>
                <input type="text" class="form-control ${requestScope.InputError.firstName != null? 'is-invalid' : ''}" id="first-name" name="firstName" aria-describedby="firstNameHelp" value="${moderator.firstName}" required>
                <div class="invalid-feedback">
                    <c:out value="${requestScope.InputError.firstName}"/>
                </div>
            </div>
            <div class="col">
                <label class="form-label" for="last-name">Last Name :</label>
                <input type="text" class="form-control ${requestScope.InputError.lastName != null? 'is-invalid' : ''}" id="last-name" name="lastName" aria-describedby="lastNameHelp" value="${moderator.lastName}" required>
                <div class="invalid-feedback">
                    <c:out value="${requestScope.InputError.lastName}"/>
                </div>
            </div>
        </div>
        <div class="row mb-3 input-group" id="div-new-password">
            <div class="col">
                <label class="form-label" for="password">New password :</label>
                <input type="password" class="form-control ${requestScope.InputError.password != null? 'is-invalid' : ''}" id="password" name="password" aria-describedby="passwordHelp" placeholder="Enter new password">
                <div class="invalid-feedback">
                    <c:out value="${requestScope.InputError.password}"/>
                </div>
            </div>
            <div class="col">
                <label class="form-label" for="confirm-password">Password confirmation :</label>
                <input type="password" class="form-control ${requestScope.InputError.confirmPassword != null? 'is-invalid' : ''}" id="confirm-password" name="confirmPassword" aria-describedby="confirmPasswordHelp" placeholder="New password confirmation">
                <div class="invalid-feedback">
                    <c:out value="${requestScope.InputError.confirmPassword}"/>
                </div>
            </div>
        </div>
        <div class="mb-3" id="div-permissions">
            <span>Permissions :</span>
            <c:forEach var="permission" items="${permissions}">
                <div class="form-check form-switch ms-3">
                    <input class="form-check-input ${requestScope.InputError.permission != null? 'is-invalid' : ''}" type="checkbox" name="permissions" aria-describedby="${permission.id}Help" id="${permission.id}" value="${permission.ordinal()}"
                        <c:forEach var="permissionName" items="${permissionNameList}" varStatus="stop">
                            <c:if test="${fn:contains(permissionName, permission.name)}">
                                checked
                                <c:set var="stop" value="true"/>
                            </c:if>
                        </c:forEach>
                    >
                    <label class="form-check-label" for="${permission.id}">${permission.name}</label>
                </div>
            </c:forEach>
        </div>
        <div class="row mb-3 input-group" id="div-contact-details">
            <div class="col">
                <label class="form-label" for="email">Email :</label>
                <input type="email" class="form-control ${requestScope.InputError.email != null? 'is-invalid' : ''}" id="email" name="email" aria-describedby="emailHelp" placeholder="Enter an email" value="${moderator.email}" required>
                <div class="invalid-feedback">
                    <c:out value="${requestScope.InputError.email}"/>
                </div>
            </div>
            <div class="col">
                <label class="form-label" for="phone-number">Phone Number :</label>
                <input type="tel" class="form-control ${requestScope.InputError.phoneNumber != null? 'is-invalid' : ''}" id="phone-number" name="phoneNumber" aria-describedby="phoneNumberHelp" placeholder="Enter a phone number" value="${moderator.phoneNumber}">
                <div class="invalid-feedback">
                    <c:out value="${requestScope.InputError.phoneNumber}"/>
                </div>
            </div>
        </div>
        <div class="row mb-3 input-group" id="div-password">
            <div class="col">
                <label class="form-label" for="old-password">Password :</label>
                <input type="password" class="form-control ${requestScope.InputError.oldPassword != null? 'is-invalid' : ''}" id="old-password" name="oldPassword" aria-describedby="oldPasswordHelp" placeholder="Enter moderator's password" required>
                <div class="invalid-feedback">
                    <c:out value="${requestScope.InputError.oldPassword}"/>
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
        }, "Name not valid")
        $.validator.addMethod("patternPassword", function (value){
            return value.match(/^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,24}$/) != null;
        }, "Password is not valid : it needs letters, numbers, special characters @$!%*#?& and length between 8 and 24.")
        $.validator.addMethod("patternEmail", function (value){
            return value.match(/^[\w-.]+@([\w-]+\.)+[\w-]{2,4}$/) != null;
        }, "Email is not valid.")

        $("form[name='edit-moderator-form']").validate({
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
                    email: true,
                    patternEmail : true
                },
                oldPassword: {
                    required: true,
                    patternPassword: true
                }
            },
            messages: {
                firstName: {
                    required: "Please enter the moderator's first name",
                    max: "First name can not exceed 30 characters.",
                    patternName: "First name is not valid : only letters and -' are authorized.",
                },
                lastName: {
                    required: "Please enter the moderator's last name",
                    max: "Last name can not exceed 30 characters.",
                    patternName: "Last name is not valid : only letters and -' are authorized.",
                },
                email: {
                    required: "Please enter an email address",
                    patternEmail: "Email is not valid."
                },
                oldPassword: {
                    required: "Please provide a password",
                    patternPassword : "Password is not valid : it needs letters, numbers, special characters @$!%*#?& and length between 8 and 24."
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
