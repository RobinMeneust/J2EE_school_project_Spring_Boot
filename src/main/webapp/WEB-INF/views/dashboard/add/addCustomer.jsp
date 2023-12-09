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
        <h2>Add Customer</h2>
        <form id="add-customer-form" name="add-customer-form" action="add-customer" method="post">
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
            <div class="mb-3"  id="div-address">
                <div class="row input-group">
                    <div class="col">
                        <label class="form-label" for="street">Street :</label>
                        <input type="text" class="form-control" id="street" name="street" placeholder="Enter street" required>
                    </div>
                </div>
                <div class="row input-group">
                    <div class="col">
                        <label class="form-label" for="postal-code">Postal Code :</label>
                        <input type="text" class="form-control" id="postal-code" name="postal-code" placeholder="Enter postal code" required>
                    </div>
                    <div class="col">
                        <label class="form-label" for="city">City :</label>
                        <input type="text" class="form-control" id="city" name="city" placeholder="Enter city" required>
                    </div>
                    <div class="col">
                        <label class="form-label" for="country">Country :</label>
                        <input type="text" class="form-control" id="country" name="country" placeholder="Enter country" required>
                    </div>
                </div>
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
