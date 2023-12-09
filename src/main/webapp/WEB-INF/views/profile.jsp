        <%--
  Created by IntelliJ IDEA.
  User: Gandy Théo
  Date: 30/10/2023
  Time: 20:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.util.List" %>
<%@ page import="j2ee_project.model.order.Orders" %>
<%@ page import="j2ee_project.model.loyalty.LoyaltyLevel" %>
<%@ page import="j2ee_project.model.loyalty.LoyaltyAccount" %>
<%@ page import="j2ee_project.model.user.Customer" %>
<%@ page import="j2ee_project.model.Address" %>
<%@ page import="java.util.Set" %>
        <%@ page import="j2ee_project.service.AuthService" %>
        <%@ page import="j2ee_project.model.user.User" %>
        <%@ page import="java.util.TreeSet" %>
        <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" name="viewport" content="width=device-width, initial-scale=1, text/html, charset=UTF-8">
    <jsp:include page="../../include.jsp" />
    <title>Profile</title>
    <style>
        h2{
            text-align: center;
        }

        div.container{
            width: 100%;
            max-width: 100%;
        }

        div.tab-content{
            width: 70%;
        }

        div.nav-tabs{
            width: 30%;
        }

        .step-container {
            position: relative;
            text-align: center;
            transform: translateY(-43%);
        }

        .step-circle {
            width: 30px;
            height: 30px;
            border-radius: 50%;
            background-color: #fff;
            border: 2px solid #9c36b5;
            line-height: 30px;
            font-weight: bold;
            display: flex;
            align-items: center;
            justify-content: center;
            margin-bottom: 10px;
            cursor: pointer;
        }

        #multi-step-form{
            overflow-x: hidden;
        }

        .horizontal-scrollable > .row {
            overflow-x: auto;
            white-space: nowrap;
        }

        .horizontal-scrollable > .row > .col-xs-4 {
            display: inline-block;
            float: none;
        }
    </style>
</head>
<body>
<jsp:include page="../../layout/header.jsp" />

<%
    Customer customer = (Customer) request.getAttribute("customer");
    if(customer == null) {
        response.sendRedirect("login");
    }
    LoyaltyAccount loyaltyAccount = customer.getLoyaltyAccount();
    List<LoyaltyLevel> loyaltyLevels = (List<LoyaltyLevel>) request.getAttribute("loyaltyLevels");
    String activeTab = request.getParameter("active-tab");
    Address address;
    String customerFirstName = null;
    String customerLastName = null;
    String customerPhoneNumber = null;
    String customerEmail = null;
    String customerAddress = null;
    String customerPostalCode =null;
    String customerCountry =null;
    String customerCity =null;
    TreeSet<Orders> orders = null;

    if (request.getAttribute("customer") != null) {
        address = customer.getAddress();
        customerFirstName = customer.getFirstName();
        customerLastName = customer.getLastName();
        customerEmail = customer.getEmail();
        customerPhoneNumber = customer.getPhoneNumber();
        if (customer.getAddress()!=null) {
            customerAddress = address.getStreetAddress();
            customerPostalCode = address.getPostalCode();
            customerCountry = address.getCountry();
            customerCity = address.getCity();
        }
        orders = new TreeSet<>(customer.getOrders());
    }%>
<c:set var="orders" value="<%=orders%>"/>
<c:set var="customer" value="<%=customer%>"/>
<c:set var="loyaltyLevels" value="<%=loyaltyLevels%>"/>
<c:set var="activeTab" value="<%=activeTab%>"/>
<c:set var="loyaltyAccount" value="<%=loyaltyAccount%>"/>



    <div class="container p-3 mt-5" style="min-height:100vh">
        <div class="d-flex align-items-start">
            <nav>
                <div class="nav nav-tabs flex-column" id="nav-tab" role="tablist">
                    <button class="nav-link <c:if test="${activeTab == 1}">active</c:if>" id="nav-profile-informations-tab" data-bs-toggle="tab" data-bs-target="#nav-profile-informations" type="submit" role="tab" aria-controls="nav-profile-informations" aria-selected="true"><a href="profile-informations?customerId=${customer.id}">Profile informations</a></button>
                        <button class="nav-link <c:if test="${activeTab == 2}">active</c:if>" id="nav-loyalty-account-tab" data-bs-toggle="tab" data-bs-target="#nav-loyalty-account" type="submit" role="tab" aria-controls="nav-loyalty-account" aria-selected="false"><a href="loyalty-redeem?customerId=${customer.id}&loyaltyAccountId=${customer.loyaltyAccount.id}">Loyalty account</a></button>
                        <button class="nav-link <c:if test="${activeTab == 3}">active</c:if>" id="nav-order-history-tab" data-bs-toggle="tab" data-bs-target="#nav-order-history" type="submit" role="tab" aria-controls="nav-order-history" aria-selected="false"><a href="order-history?id=${customer.id}">Order history</a></button>
                </div>
            </nav>
            <div class="tab-content" id="nav-tabContent">
                <div class="tab-pane fade <c:if test="${activeTab == 1}">show active</c:if>" id="nav-profile-informations" role="tabpanel" aria-labelledby="nav-profile-informations-tab"> <h2>Profile informations</h2>
                    <p></p>
                    <form action="profile-informations?id=${customer.id}&addressId=${customer.address.id}" method="post">
                        <div class="form-group">
                            <label for="userFirstName">Name</label>
                            <input type="text" class="form-control" id="userFirstName" name="userFirstName" value="<%=customerFirstName%>">
                        </div>
                        <div class="form-group">
                            <label for="userLastName">Surname</label>
                            <input type="text" class="form-control" id="userLastName" name="userLastName" value="<%=customerLastName%>">
                        </div>
                        <div class="form-group">
                            <label for="userEmail">Email address</label>
                            <input type="email" class="form-control" id="userEmail" name="userEmail" value="<%=customerEmail%>">
                        </div>
                        <div class="form-group">
                            <label for="userPassword">Password</label>
                            <input type="password" class="form-control" id="userPassword" name="userPassword" placeholder="******">
                        </div>
                        <div class="form-group">
                            <label for="userPhoneNumber">Phone number</label>
                            <input type="text" class="form-control" id="userPhoneNumber" name="userPhoneNumber" value="<%=customerPhoneNumber == null ? "" : customerPhoneNumber%>">
                        </div>
                        <div class="form-group">
                            <label for="userAddress">Address</label>
                            <input type="text" class="form-control" id="userAddress" name="userAddress" value="<%=customerAddress%>">
                        </div>
                        <div class="form-group">
                            <label for="userPostalCode">Postal code</label>
                            <input type="text" class="form-control" id="userPostalCode" name="userPostalCode" value="<%=customerPostalCode%>">
                        </div>
                        <div class="form-group">
                            <label for="userCity">City</label>
                            <input type="text" class="form-control" id="userCity" name="userCity" value="<%=customerCity%>">
                        </div>
                        <div class="form-group">
                            <label for="userCountry">Country</label>
                            <input type="text" class="form-control" id="userCountry" name="userCountry" value="<%=customerCountry%>">
                        </div>
                        <p></p>
                        <button type="submit" class="btn btn-primary">Update profile</button>
                    </form>
                </div>
                <div class="tab-pane fade <c:if test="${activeTab == 2}">show active</c:if>" id="nav-loyalty-account" role="tabpanel" aria-labelledby="nav-loyalty-account-tab"> <h2>Loyalty account</h2>
                    <p></p>
                    <c:if test="${not empty loyaltyAccount}">
                        <p>You have : <c:out value="${loyaltyAccount.getLoyaltyPoints()}"  /> loyalty points. </p>
                        <div id="container" class="container mt-5">
                            <div class="container horizontal-scrollable">
                                <div class="progress px-1" style="height: 3px;">
                                    <div class="progress-bar" role="progressbar" style="width: 0;" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100"></div>
                                </div>
                                <div class="step-container d-flex justify-content-between">
                                    <% if(loyaltyLevels!=null){for (LoyaltyLevel loyaltyLevel : loyaltyLevels) {%>
                                        <div class="step-circle" onclick="displayStep(<%=loyaltyLevel.getId()%>)">
                                            <%=loyaltyLevel.getId()%></div>
                                    <%}}%>
                                </div>
                            </div>
                            <%if(loyaltyLevels!=null){ for (LoyaltyLevel loyaltyLevel : loyaltyLevels) {%>
                                <div class="step step-<%=loyaltyLevel.getId()%>">
                                    <form id="multi-step-form" action="loyalty-redeem?customerId=${customer.id}&loyaltyAccountId=${customer.loyaltyAccount.id}&loyaltyLevelId=<%=loyaltyLevel.getId()%>" method="post">

                                    <h3><%= loyaltyLevel.getDiscount().getDiscountPercentage()%>% Discount</h3>
                                    <div class="mb-3">
                                        <%if(loyaltyAccount.getLoyaltyPoints()>loyaltyLevel.getRequiredPoints()){
                                            if (loyaltyAccount.isLevelUsed(loyaltyLevel)){%>
                                                <p> already redeemed</p>
                                            <%}else{%>
                                                <button type="submit" class="btn btn-success">Redeem</button>
                                            <%}
                                        }else{%>
                                            <p> You do not have enough loyalty points</p>
                                        <%}%>
                                    </div>
                                    <% if (loyaltyLevel.getId() > 1) {
                                        if (loyaltyLevel.getId() > loyaltyLevels.size()) {%>
                                            <button type="button" class="btn btn-primary prev-step">Previous</button>
                                            <button type="button" class="btn btn-primary next-step">Next</button>
                                        <%} else {%>
                                    <button type="button" class="btn btn-primary prev-step">Previous</button>
                                        <%}%>
                                    <%} else {%>
                                    <button type="button" class="btn btn-primary next-step">Next</button>
                                        <%}%>
                                    </form>
                                </div>
                            <%}}%>
                        </div>
                        <c:if test="${not empty loyaltyAccount.getAvailableDiscounts()}">
                            <div>
                                <h6 class="display-6">Available discounts</h6>
                                <table class="table table-striped">
                                    <tr>
                                        <th>Name</th>
                                        <th>Value</th>
                                        <th>Start date</th>
                                        <th>End date</th>
                                    </tr>
                                    <c:forEach var="discount" items="${loyaltyAccount.getAvailableDiscounts()}">
                                        <c:if test="${!discount.hasExpired()}">
                                            <tr>
                                                <td><c:out value="${discount.getName()}"/></td>
                                                <td>- <c:out value="${discount.getDiscountPercentage()}"/> %</td>
                                                <td><c:out value="${discount.getStartDate()}"/></td>
                                                <td><c:out value="${discount.getEndDate()}"/></td>
                                            </tr>
                                        </c:if>
                                    </c:forEach>
                                </table>
                            </div>
                        </c:if>
                        <script>
                            var currentStep = 1;
                            var updateProgressBar;
                            function displayStep(stepNumber) {
                                if (stepNumber >= 1 && stepNumber <= ${loyaltyLevels.size()}) {
                                    $(".step-" + currentStep).hide();
                                    $(".step-" + stepNumber).show();
                                    currentStep = stepNumber;
                                    updateProgressBar();
                                }
                            }

                            $(document).ready(function() {

                                setTimeout(function (){
                                    $(".step").hide();
                                    $(".step-1").show();
                                },20);

                                $('#multi-step-form').find('.step').slice(1).hide();

                                $(".next-step").click(function() {
                                    if (currentStep < ${loyaltyLevels.size()}) {
                                        $(".step-" + currentStep);
                                        currentStep++;
                                        setTimeout(function() {
                                            $(".step").hide();
                                            $(".step-" + currentStep).show();
                                            updateProgressBar();
                                        }, 20);
                                    }
                                });

                                $(".prev-step").click(function() {
                                    if (currentStep > 1) {
                                        $(".step-" + currentStep);
                                        currentStep--;
                                        setTimeout(function() {
                                            $(".step").hide();
                                            $(".step-" + currentStep).show();
                                            updateProgressBar();
                                        }, 20);
                                    }
                                });

                                updateProgressBar = function() {
                                    var progressPercentage = ((currentStep-1)/(${loyaltyLevels.size()}-1)) * 100;
                                    $(".progress-bar").css("width", progressPercentage + "%");
                                }
                            });
                        </script>
                    </c:if>
                    <c:if test="${empty loyaltyAccount}">
                        <p>You do not have a loyalty Account</p>
                    </c:if>
                </div>
                <div class="tab-pane fade <c:if test="${activeTab == 3}">show active</c:if>" id="nav-order-history" role="tabpanel" aria-labelledby="nav-order-history-tab"> <h2>Order history</h2>
                    <table class="table table-striped table-hover" id="customers-table" style="width: 100%" data-filter-control-visible="false">
                        <thead>
                            <tr>
                                <th>N°</th>
                                <th>Date</th>
                                <th>number of items purchased</th>
                                <th>Status</th>
                            </tr>
                        </thead>
                        <%if(orders!=null){
                            for (Orders order: orders){%>
                                <tr>
                                    <td><a href="order?order-id=<%=order.getId()%>"><%=order.getId()%></a></td>
                                    <td><%=order.getDate()%></td>
                                    <td><%=order.getTotal()%></td>
                                    <td><%=order.getOrderStatus()%></td>
                                </tr>
                            <%}
                        }%>
                    </table>
                </div>
            </div>
        </div>
    </div>
<jsp:include page="../../layout/footer.jsp" />
</body>
</html>

