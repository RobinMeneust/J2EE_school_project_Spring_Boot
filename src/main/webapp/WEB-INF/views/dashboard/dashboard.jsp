<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cf" uri="/WEB-INF/functions.tld"%>
<%@ page import="j2ee_project.model.user.TypePermission" %>
<html>
<head>
    <title>Dashboard</title>
    <jsp:include page="../../../include.jsp"/>
    <script type="application/javascript">
        function changeURLParameter(tab){
            let url = new URL(window.location.href);
            url.searchParams.set("tab",tab);
            window.history.replaceState(null, null, url.href);
        }

        $(document).ready(function () {
            $('#customers-table').DataTable();
            $('#moderators-table').DataTable();
            $('#products-table').DataTable();
            $('#categories-table').DataTable();
            $('#discounts-table').DataTable();
        });
    </script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard/dashboard.css">
    <script>
        function confirmDelete(type, id) {
            let confirmation = confirm("Are you sure you want to delete this one?");
            if (confirmation) {
                window.location.href = "delete-" + type + "?id=" + id;
            }
        }
    </script>
</head>
<body>
    <jsp:include page="../../../layout/header.jsp" />
    <c:set var="tab" value="${param.tab}"/>
    <c:set var="user" value="${cf:getModerator(sessionScope.user)}"/>
    <div class="container p-3 mt-5" style="min-height:100vh">
        <div class="d-flex">
            <div class="div-tabs mt-4">
                <div class="nav nav-pills d-flex flex-column align-items-stretch" id="pills-tab" role="tablist" aria-orientation="vertical">
                    <c:if test="${user.isAllowed(cf:getPermission(TypePermission.CAN_MANAGE_CUSTOMER)) || user.isAllowed(cf:getPermission(TypePermission.CAN_MANAGE_MODERATOR))}">
                        <div>
                            <span class="field-title">Users</span>
                            <hr>
                            <c:if test="${user.isAllowed(cf:getPermission(TypePermission.CAN_MANAGE_CUSTOMER))}">
                                <span class="d-flex justify-content-between flex-row">
                                    <button onclick="changeURLParameter('customers')"
                                        class="nav-link
                                        <c:if test="${tab=='customers'}">
                                            <c:out value="active"/>
                                        </c:if>"
                                        id="pills-customers-tab"
                                        data-bs-toggle="pill"
                                        data-bs-target="#pills-customers"
                                        type="button"
                                        role="tab"
                                        aria-controls="pills-customers"
                                        aria-selected="true">
                                        <span class="d-flex justify-content-between flex-row">
                                            <span>Customers</span>
                                            <span class="material-symbols-outlined" id="span-chevron-customers">
                                                chevron_right
                                            </span>
                                        </span>
                                </button>
                            </span>
                            </c:if>
                            <c:if test="${user.isAllowed(cf:getPermission(TypePermission.CAN_MANAGE_MODERATOR))}">
                                <span class="d-flex justify-content-between flex-row">
                                    <button onclick="changeURLParameter('moderators')"
                                        class="nav-link
                                        <c:if test="${tab=='moderators'}">
                                            <c:out value="active"/>
                                        </c:if>"
                                        id="pills-moderators-tab"
                                        data-bs-toggle="pill"
                                        data-bs-target="#pills-moderators"
                                        type="button"
                                        role="tab"
                                        aria-controls="pills-moderators"
                                        aria-selected="false">
                                        <span class="d-flex justify-content-between flex-row">
                                            <span>Moderators</span>
                                            <span class="material-symbols-outlined" id="span-chevron-moderators">
                                                chevron_right
                                            </span>
                                        </span>
                                    </button>
                                </span>
                            </c:if>
                        </div>
                    </c:if>
                    <c:if test="${user.isAllowed(cf:getPermission(TypePermission.CAN_MANAGE_PRODUCT)) || user.isAllowed(cf:getPermission(TypePermission.CAN_MANAGE_CATEGORY))}">
                        <div class="mt-5">
                            <span class="field-title">Catalogue</span>
                            <hr>
                            <c:if test="${user.isAllowed(cf:getPermission(TypePermission.CAN_MANAGE_PRODUCT))}">
                                <span class="d-flex justify-content-between flex-row">
                                    <button onclick="changeURLParameter('products')"
                                        class="nav-link
                                        <c:if test="${tab=='products'}">
                                            <c:out value="active"/>
                                        </c:if>"
                                        id="pills-products-tab"
                                        data-bs-toggle="pill"
                                        data-bs-target="#pills-products"
                                        type="button"
                                        role="tab"
                                        aria-controls="pills-products"
                                        aria-selected="false">
                                        <span class="d-flex justify-content-between flex-row">
                                            <span>Products</span>
                                            <span class="material-symbols-outlined" id="span-chevron-products">
                                                chevron_right
                                            </span>
                                        </span>
                                    </button>
                                </span>
                            </c:if>
                            <c:if test="${user.isAllowed(cf:getPermission(TypePermission.CAN_MANAGE_CATEGORY))}">
                                <span class="d-flex justify-content-between flex-row">
                                    <button onclick="changeURLParameter('categories')"
                                        class="nav-link
                                        <c:if test="${tab=='categories'}">
                                            <c:out value="active"/>
                                        </c:if>"
                                        id="pills-categories-tab"
                                        data-bs-toggle="pill"
                                        data-bs-target="#pills-categories"
                                        type="button"
                                        role="tab"
                                        aria-controls="pills-categories"
                                        aria-selected="false">
                                        <span class="d-flex justify-content-between flex-row">
                                            <span>
                                                Categories
                                            </span>
                                            <span class="material-symbols-outlined" id="span-chevron-categories">
                                                chevron_right
                                            </span>
                                        </span>
                                    </button>
                                </span>
                            </c:if>
                        </div>
                    </c:if>
                    <c:if test="${user.isAllowed(cf:getPermission(TypePermission.CAN_MANAGE_DISCOUNT))}">
                        <div class="mt-5">
                            <span class="field-title">Offers</span>
                            <hr>
                            <c:if test="${user.isAllowed(cf:getPermission(TypePermission.CAN_MANAGE_DISCOUNT))}">
                                <span class="d-flex justify-content-between flex-row">
                                    <button onclick="changeURLParameter('discounts')"
                                        class="nav-link
                                        <c:if test="${tab=='discounts'}">
                                            <c:out value="active"/>
                                        </c:if>"
                                        id="pills-discounts-tab"
                                        data-bs-toggle="pill"
                                        data-bs-target="#pills-discounts"
                                        type="button"
                                        role="tab"
                                        aria-controls="pills-discounts"
                                        aria-selected="false">
                                        <span class="d-flex justify-content-between flex-row">
                                            <span>
                                                Discounts
                                            </span>
                                            <span class="material-symbols-outlined" id="span-chevron-discounts">
                                                chevron_right
                                            </span>
                                        </span>
                                    </button>
                                </span>
                            </c:if>
                        </div>
                    </c:if>
                </div>
            </div>
            <div class="tab-content me-3 ms-3" id="pills-tabContent">
                <c:if test="${user.isAllowed(cf:getPermission(TypePermission.CAN_MANAGE_CUSTOMER))}">
                    <div class="tab-pane fade
                                <c:if test="${tab=='customers'}">
                                    <c:out value="show"/>
                                    <c:out value="active"/>
                                </c:if>"
                         id="pills-customers"
                         role="tabpanel"
                         aria-labelledby="pills-customers-tab">
                        <div class="div-add-data d-flex align-items-end flex-column mb-lg-2">
                            <a href="add-customer" class="add-data btn btn-primary" id="add-customer">Add Customer</a>
                        </div>
                        <c:set var="customers" value="${requestScope.customers}"/>
                        <table class="table table-striped table-hover" id="customers-table" data-filter-control-visible="false">
                            <thead class="align-middle">
                                <tr>
                                    <th class="text-center">Last Name</th>
                                    <th class="text-center">First Name</th>
                                    <th class="text-center">Street</th>
                                    <th class="text-center">Postal Code</th>
                                    <th class="text-center">City</th>
                                    <th class="text-center">Country</th>
                                    <th class="text-center">Email</th>
                                    <th class="text-center">Phone Number</th>
                                    <th class="text-center" data-sortable="false"></th>
                                    <th class="text-center" data-sortable="false"></th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var = "customer" items = "${customers}">
                                    <tr>
                                        <td class="text-center"><c:out value = "${customer.lastName}"/></td>
                                        <td class="text-center"><c:out value = "${customer.firstName}"/></td>
                                        <td class="text-center"><c:out value = "${customer.address.streetAddress}"/></td>
                                        <td class="text-center"><c:out value = "${customer.address.postalCode}"/></td>
                                        <td class="text-center"><c:out value = "${customer.address.city}"/></td>
                                        <td class="text-center"><c:out value = "${customer.address.country}"/></td>
                                        <td class="text-center"><c:out value = "${customer.email}"/></td>
                                        <td class="text-center"><c:out value = "${customer.phoneNumber}"/></td>
                                        <td class="text-center col-1">
                                            <a href="edit-customer?id=<c:out value = '${customer.id}'/>">
                                                <button class="btn rounded"><span class="material-symbols-outlined">edit</span></button>
                                            </a>
                                        </td>
                                        <td class="text-center col-1">
                                            <button onclick="confirmDelete('customer', ${customer.id})" class="btn rounded">
                                                <span class="material-symbols-outlined">delete</span>
                                            </button>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                    </table>
                    </div>
                </c:if>
                <c:if test="${user.isAllowed(cf:getPermission(TypePermission.CAN_MANAGE_MODERATOR))}">
                    <div class="tab-pane fade
                                    <c:if test="${tab=='moderators'}">
                                        <c:out value="show"/>
                                        <c:out value="active"/>
                                    </c:if>"
                         id="pills-moderators"
                         role="tabpanel"
                         aria-labelledby="pills-moderators-tab">
                        <div class="div-add-data d-flex align-items-end flex-column mb-lg-2">
                            <a href="add-moderator" class="add-data btn btn-primary" id="add-moderator">Add Moderator</a>
                        </div>
                        <c:set var="moderators" value="${requestScope.moderators}"/>
                        <table class="table table-striped table-hover" id="moderators-table" data-filter-control-visible="false">
                            <thead class="align-middle">
                            <tr>
                                <th class="text-center">Last Name</th>
                                <th class="text-center">First Name</th>
                                <th class="text-center">Permissions</th>
                                <th class="text-center">Email</th>
                                <th class="text-center">Phone Number</th>
                                <th class="text-center" data-sortable="false"></th>
                                <th class="text-center" data-sortable="false"></th>
                            </tr>
                            </thead>
                            <tbody class="align-middle">
                            <c:forEach var = "moderator" items = "${moderators}">
                                <tr>
                                    <td class="text-center"><c:out value = "${moderator.lastName}"/></td>
                                    <td class="text-center"><c:out value = "${moderator.firstName}"/></td>
                                    <td>
                                        <div class="d-flex justify-content-center">
                                            <ul class="mb-0">
                                                <c:forEach var="permission" items="${moderator.permissions}">
                                                    <li>
                                                        <c:out value="${permission.permission}"/>
                                                    </li>
                                                </c:forEach>
                                            </ul>
                                        </div>
                                    </td>
                                    <td class="text-center"><c:out value = "${moderator.email}"/></td>
                                    <td class="text-center"><c:out value = "${moderator.phoneNumber}"/></td>
                                    <td class="text-center col-1">
                                        <a href="edit-moderator?id=${moderator.id}">
                                            <button class="btn rounded"><span class="material-symbols-outlined">edit</span></button>
                                        </a>
                                    </td>
                                    <td class="text-center col-1">
                                        <button onclick="confirmDelete('moderator', ${moderator.id})" class="btn rounded">
                                            <span class="material-symbols-outlined">delete</span>
                                        </button>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:if>
                <c:if test="${user.isAllowed(cf:getPermission(TypePermission.CAN_MANAGE_PRODUCT))}">
                    <div class="tab-pane fade
                                    <c:if test="${tab=='products'}">
                                        <c:out value="show"/>
                                        <c:out value="active"/>
                                    </c:if>"
                         id="pills-products"
                         role="tabpanel"
                         aria-labelledby="pills-products-tab">
                        <div class="div-add-data d-flex align-items-end flex-column mb-lg-2">
                            <a href="add-product" class="add-data btn btn-primary" id="add-product">Add Product</a>
                        </div>
                        <c:set var="products" value="${requestScope.products}"/>
                        <table class="table table-striped table-hover" id="products-table" data-filter-control-visible="false">
                            <thead class="align-middle">
                            <tr>
                                <th class="text-center">Image</th>
                                <th class="text-center">Name</th>
                                <th class="text-center">Description</th>
                                <th class="text-center">Stock Quantity</th>
                                <th class="text-center">Price</th>
                                <th class="text-center">Weight</th>
                                <th class="text-center">Category</th>
                                <th class="text-center" data-sortable="false"></th>
                                <th class="text-center" data-sortable="false"></th>
                            </tr>
                            </thead>
                            <tbody class="align-middle">
                                <c:forEach var = "product" items = "${products}">
                                    <tr>
                                        <td>
                                            <img style="width: 78px; height: 50px; object-fit: contain;"
                                                 alt="product_img"
                                                 src="<c:out value="product/image?id=${product.id}" />"
                                                 class="card-img-top">
                                        </td>
                                        <td class="text-center"><c:out value="${product.name}"/></td>
                                        <td class="td-description"><c:out value="${product.description}"/></td>
                                        <td class="text-center"><c:out value="${product.stockQuantity}"/></td>
                                        <td class="text-center"><c:out value="${product.unitPrice}"/></td>
                                        <td class="text-center"><c:out value="${product.weight}"/></td>
                                        <td class="text-center"><c:out value="${product.category.name}"/></td>
                                        <td class="text-center col-1">
                                            <a href="edit-product?id=${product.id}">
                                                <button class="btn rounded"><span class="material-symbols-outlined">edit</span></button>
                                            </a>
                                        </td>
                                        <td class="text-center col-1">
                                            <button onclick="confirmDelete('product', ${product.id})" class="btn rounded">
                                                <span class="material-symbols-outlined">delete</span>
                                            </button>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:if>
                <c:if test="${user.isAllowed(cf:getPermission(TypePermission.CAN_MANAGE_CATEGORY))}">
                    <div class="tab-pane fade
                                    <c:if test="${tab=='categories'}">
                                        <c:out value="show"/>
                                        <c:out value="active"/>
                                    </c:if>"
                         id="pills-categories"
                         role="tabpanel"
                         aria-labelledby="pills-discounts-tab">
                        <div class="div-add-data d-flex align-items-end flex-column mb-lg-2">
                            <a href="add-category" class="add-data btn btn-primary" id="add-category">Add Category</a>
                        </div>
                        <c:set var="categories" value="${requestScope.categories}"/>
                        <table class="table table-striped table-hover" id="categories-table" data-filter-control-visible="false">
                            <thead class="align-middle">
                            <tr>
                                <th class="text-center">Name</th>
                                <th class="text-center">Description</th>
                                <th class="text-center">Discount</th>
                                <th class="text-center" data-sortable="false"></th>
                                <th class="text-center" data-sortable="false"></th>
                            </tr>
                            </thead>
                            <tbody class="align-middle">
                            <c:forEach var = "category" items = "${categories}">
                                <tr>
                                    <td class="text-center"><c:out value = "${category.name}"/></td>
                                    <td class="td-description"><c:out value = "${category.description}"/></td>
                                    <td class="text-center">
                                        <c:if test="${category.discount!=null}">
                                            <c:out value = "${category.discount.name}"/>
                                        </c:if>
                                    </td>
                                    <td class="text-center col-1">
                                        <a href="edit-category?id=${category.id}">
                                            <button class="btn rounded"><span class="material-symbols-outlined">edit</span></button>
                                        </a>
                                    </td>
                                    <td class="text-center col-1">
                                        <button onclick="confirmDelete('category', ${category.id})" class="btn rounded">
                                            <span class="material-symbols-outlined">delete</span>
                                        </button>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:if>
                <c:if test="${user.isAllowed(cf:getPermission(TypePermission.CAN_MANAGE_DISCOUNT))}">
                    <div class="tab-pane fade
                                    <c:if test="${tab=='discounts'}">
                                        <c:out value="show"/>
                                        <c:out value="active"/>
                                    </c:if>"
                         id="pills-discounts"
                         role="tabpanel"
                         aria-labelledby="pills-discounts-tab">
                        <div class="div-add-data d-flex align-items-end flex-column mb-lg-2">
                            <a href="add-discount" class="add-data btn btn-primary" id="add-discount">Add Discount</a>
                        </div>
                        <c:set var="discounts" value="${requestScope.discounts}"/>
                        <table class="table table-striped table-hover" id="discounts-table" data-filter-control-visible="false">
                            <thead class="align-middle">
                            <tr>
                                <th class="text-center">Name</th>
                                <th class="text-center">Start Date</th>
                                <th class="text-center">End Date</th>
                                <th class="text-center">Discount Percentage</th>
                                <th class="text-center" data-sortable="false"></th>
                                <th class="text-center" data-sortable="false"></th>
                            </tr>
                            </thead>
                            <tbody class="align-middle">
                            <c:forEach var = "discount" items = "${discounts}">
                                <tr>
                                    <td class="text-center"><c:out value = "${discount.name}"/></td>
                                    <td class="text-center"><c:out value = "${discount.startDate}"/></td>
                                    <td class="text-center"><c:out value = "${discount.endDate}"/></td>
                                    <td class="text-center"><c:out value = "${discount.discountPercentage}"/></td>
                                    <td class="text-center col-1">
                                        <a href="edit-discount?id=${discount.id}">
                                            <button class="btn rounded"><span class="material-symbols-outlined">edit</span></button>
                                        </a>
                                    </td>
                                    <td class="text-center col-1">
                                        <button onclick="confirmDelete('discount', ${discount.id})" class="btn rounded">
                                            <span class="material-symbols-outlined">delete</span>
                                        </button>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:if>
            </div>
        </div>
    </div>
    <jsp:include page="../../../layout/footer.jsp" />
</body>
</html>
