<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Dashboard</title>
    <jsp:include page="../../../../include.jsp" />
    <script src="${pageContext.request.contextPath}/dependencies/rangeSlider/tcrs-generated-labels.min.js"></script>
    <script src="${pageContext.request.contextPath}/dependencies/rangeSlider/tcrs-moving-tooltip.min.js"></script>
    <script src="${pageContext.request.contextPath}/dependencies/rangeSlider/toolcool-range-slider.min.js"></script>
</head>
<body>
    <jsp:include page="../../../../layout/header.jsp" />
    <div class="d-flex flex-column align-items-center div-form">
        <h2>Add Discount</h2>
        <form id="add-discount-form" name="add-discount-form" action="add-discount" method="post">
            <div class="row mb-3 input-group" id="div-name&">
                <div class="col">
                    <label class="form-label" for="name">Name :</label>
                    <input type="text" class="form-control" id="name" name="name" placeholder="Enter discount name" required>
                </div>
            </div>
            <div class="row mb-3 input-group" id="div-date">
                <div class="col">
                    <label for="start-date">Start Date :</label>
                    <input type="date" id="start-date" name="start-date" value="">
                </div>
                <div class="col">
                    <label for="end-date">End Date :</label>
                    <input type="date" id="end-date" name="end-date" value="">
                </div>
            </div>
            <div class="row mb-3 input-group"  id="div-discount-percentage">
                <div class="col">
                    <fieldset class="p-1">
                        <legend class="w-auto float-none">Discount Percentage</legend>
                        <tc-range-slider
                                id="discountPercentageSlider"

                                min="0"
                                max="100"
                                value="50"
                                step="1"
                                round="0"

                                moving-tooltip="true"
                                moving-tooltip-distance-to-pointer="40"
                                moving-tooltip-width="35"
                                moving-tooltip-height="30"
                                moving-tooltip-bg="#721d82"
                                moving-tooltip-text-color="#efefef"
                                moving-tooltip-units="%"
                        ></tc-range-slider>
                    </fieldset>
                </div>
            </div>
            <button type="submit" class="btn btn-primary">
                Submit
            </button>
        </form>
    </div>
    <script>
        const discountPercentageSlider = document.getElementById('discountPercentageSlider');
        const discountPercentage = document.getElementById('discount-percentage');
        discountPercentageSlider.addEventListener('change', (evt) => {
            discountPercentage.setAttribute("value", evt.detail.value);
        });
    </script>
    <jsp:include page="../../../../layout/footer.jsp" />
</body>
</html>
