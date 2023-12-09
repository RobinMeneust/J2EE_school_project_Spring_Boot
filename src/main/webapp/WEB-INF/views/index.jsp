<%@ page import="java.util.List" %>
<%@ page import="org.hibernate.Session" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="cf" uri="/WEB-INF/functions.tld"%>

<!DOCTYPE html>
<html>
<head>
	<jsp:include page="../../include.jsp" />
	<link href="../../css/carousel_style.css" rel="stylesheet" type="text/css">
	<title>Home</title>
</head>
<body>
	<jsp:include page="../../layout/header.jsp" />

	<c:set var="featuredProducts" value="${cf:getFeaturedProducts()}"/>
	<c:set var="categories" value="${cf:getCategories()}"/>

	<div class="container p-3 mt-5" style="min-height:100vh">
		<c:if test="${featuredProducts != null && featuredProducts.size() != 0}">
		<div class="mb-5">
			<h2 class="display-2 p-3">Featured products</h2>

			<div id="carousel-recommendations" class="carousel slide w-" data-bs-ride="carousel">
				<div class="carousel-inner">
					<c:forEach var="i" begin="0" end="${Math.min(10,featuredProducts.size()-1)}">
						<c:if test="${i%3 == 0}">
							<div class="carousel-item <c:if test="${i == 0}">active</c:if>" data-bs-interval="10000">
								<div class="row">
						</c:if>
									<div class="col-sm text-center">
										<a class="text-decoration-none text-body" href="get-product-page?id=${featuredProducts.get(i).getId()}">
											<img style="width: 390px; height: 250px; object-fit: contain;" src="product/image?id=${featuredProducts.get(i).getId()}" class="d-block" alt="${featuredProducts.get(i).getName()}_img">
											<div>
												<h5><c:out value="${featuredProducts.get(i).getName()}"/></h5>
											</div>
										</a>
									</div>
						<c:if test="${i%3 == 2}">
								</div>
							</div>
						</c:if>
					</c:forEach>
					<c:if test="${Math.min(10,featuredProducts.size()-1) % 3 != 2}"> <%-- if the 2 div were not closed--%>
								<c:forEach var="i" begin="0" end="${1 - (Math.min(10,featuredProducts.size()-1) % 3)}">
									<div class="col-sm"></div>
								</c:forEach>
								</div>
							</div>
					</c:if>
				</div>
				<button class="carousel-control-prev" type="button" data-bs-target="#carousel-recommendations" data-bs-slide="prev">
					<span class="carousel-control-prev-icon" aria-hidden="true"></span>
					<span class="visually-hidden">Previous</span>
				</button>
				<button class="carousel-control-next" type="button" data-bs-target="#carousel-recommendations" data-bs-slide="next">
					<span class="carousel-control-next-icon" aria-hidden="true"></span>
					<span class="visually-hidden">Next</span>
				</button>
			</div>
		</div>
		</c:if>
		<div class="mb-5">
			<h2 class="display-2 p-3">Categories</h2>
			<ul>
			<c:forEach items="${categories}" var="category">
				<li>
					<a class="text-decoration-none" href="browse-products?category=<c:out value="${category.getName()}"/>"><c:out value="${category.getName()}"/></a>
				</li>
			</c:forEach>
			</ul>
		</div>
		<div class="mb-5">
			<h2 class="display-2 p-3">Boarder Games</h2>
			<p>
				A website were you will find various board games.<br>
				It has been created by four engineering students for a school project using Jakarta Persistence, Hibernate and other tools.<br>
				GitHub: <a href="https://github.com/RobinMeneust/J2EE_school_project">Link</a><br>
			</p>
		</div>
	</div>
	<jsp:include page="../../layout/footer.jsp" />
</body>
</html>