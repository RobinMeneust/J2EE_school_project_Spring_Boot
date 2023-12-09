<%@ page import="j2ee_project.model.user.TypePermission" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Dashboard</title>
    <jsp:include page="../../../../include.jsp" />
</head>
<body>
    <jsp:include page="../../../../layout/header.jsp" />
    <c:set var="permissions" value="<%=TypePermission.values()%>"/>
    <div class="d-flex flex-column align-items-center div-form">
        <h2>Add Moderator</h2>
        <form id="add-moderator-form" name="add-moderator-form" action="add-moderator" method="post">
            <div class="row mb-3 input-group" id="div-name">
                <div class="col">
                    <label class="form-label" for="last-name">Last Name :</label>
                    <input type="text" class="form-control" id="last-name" name="last-name" placeholder="Enter last name" required>
                </div>
                <div class="col">
                    <label class="form-label" for="first-name">First Name :</label>
                    <input type="text" class="form-control" id="first-name" name="first-name" placeholder="Enter first name" required>
                </div>
            </div>
            <div class="row mb-3 input-group" id="div-password">
                <div class="col">
                    <label class="form-label" for="password">Password :</label>
                    <input type="password" class="form-control" id="password" name="password" placeholder="Enter password" required>
                </div>
            </div>
            <div class="mb-3" id="div-permissions">
                <span>Permissions :</span>
                <c:forEach var="permission" items="${permissions}">
                    <div class="form-check form-switch ms-3">
                        <input class="form-check-input" type="checkbox" name="permissions" id="${permission.id}" value="${permission.ordinal()}">
                        <label class="form-check-label" for="${permission.id}">${permission.name}</label>
                    </div>
                </c:forEach>
            </div>
            <div class="row mb-3 input-group" id="div-contact-details">
                <div class="col">
                    <label class="form-label" for="email">Email :</label>
                    <input type="email" class="form-control" id="email" name="email" placeholder="Enter email" required>
                </div>
                <div class="col">
                    <label class="form-label" for="phone-number">Phone Number :</label>
                    <input type="tel" class="form-control" id="phone-number" name="phone-number" placeholder="Enter phone number">
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
