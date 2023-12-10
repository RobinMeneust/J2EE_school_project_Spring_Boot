<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Dashboard</title>
    <jsp:include page="../../../../include.jsp" />
    <script src="${pageContext.request.contextPath}/dependencies/rangeSlider/tcrs-generated-labels.min.js"></script>
    <script src="${pageContext.request.contextPath}/dependencies/rangeSlider/tcrs-moving-tooltip.min.js"></script>
    <script src="${pageContext.request.contextPath}/dependencies/rangeSlider/toolcool-range-slider.min.js"></script>
    <script src="${pageContext.request.contextPath}/dependencies/jquery/jquery.validate.min.js"></script>
</head>
<body>
<jsp:include page="../../../../layout/header.jsp" />
<c:set var="discount" value="${requestScope.discount}"/>
<div class="d-flex flex-column align-items-center div-form">
    <h2>Edit Discount</h2>
    <form class="d-flex align-items-center flex-column flex-wrap" id="edit-discount-form" name="edit-discount-form" action="edit-discount?id=${discount.id}" method="post">
        <div class="row mb-3 input-group" id="div-name">
            <div class="col">
                <label class="form-label" for="name">Name :</label>
                <input type="text" class="form-control ${requestScope.InputError.name != null? 'is-invalid' : ''}" id="name" name="name" aria-describedby="nameHelp" value="${discount.name}" required>
                <div class="invalid-feedback">
                    <c:out value="${requestScope.InputError.name}"/>
                </div>
            </div>
        </div>
        <div class="row mb-3 input-group" id="div-date">
            <div class="col">
                <label class="form-label" for="start-date">Start Date :</label>
                <input type="date" class="form-control ${requestScope.InputError.startDate != null? 'is-invalid' : ''}" id="start-date" name="startDate" aria-describedby="startDateHelp" value="${discount.startDate}">
                <div class="invalid-feedback">
                    <c:out value="${requestScope.InputError.startDate}"/>
                </div>
            </div>
            <div class="col">
                <label class="form-label" for="end-date">End Date :</label>
                <input type="date" class="form-control ${requestScope.InputError.endDate != null? 'is-invalid' : ''}" id="end-date" name="endDate" aria-describedby="endDateHelp" value="${discount.endDate}">
                <div class="invalid-feedback">
                    <c:out value="${requestScope.InputError.endDate}"/>
                </div>
            </div>
        </div>
        <div class="row mb-3 input-group"  id="div-discount-percentage">
            <input type="hidden" id="discount-percentage" name="discountPercentage" aria-describedby="discountPercentageHelp" value="${discount.discountPercentage}"/>
            <div class="col">
                <fieldset class="p-1 mb-5">
                    <legend class="w- float-none">Discount Percentage</legend>
                    <tc-range-slider
                            id="discountPercentageSlider"

                            min="0"
                            max="100"
                            value="${discount.discountPercentage}"
                            step="1"
                            round="0"

                            moving-tooltip="true"
                            moving-tooltip-distance-to-pointer="-40"
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
<script>
    $(function()
    {
        $.validator.addMethod("patternName", function (value, element){
            return this.optional(element) || /^[a-zA-ZÀ-ÖØ-öø-ÿ\-' ]*$/.test(value);
        }, "Name not valid")
        $.validator.addMethod("patternStartDate", function (value){
            return value.match(/^\d{4}-\d{2}-\d{2}$/) != null;
        }, "Start Date not valid.")
        $.validator.addMethod("patternEndDate", function (value){
            return value.match(/^\d{4}-\d{2}-\d{2}$/) != null;
        }, "End Date not valid.")
        $.validator.addMethod("compareDate", function (value){
            let startDate = document.getElementById("start-date").value;
            return Date.parse(startDate) <= Date.parse(value);
        }, "End date must be after start date.")


        $("form[name='edit-discount-form']").validate({
            rules: {
                name: {
                    required: true,
                    maxlength: 30,
                    patternName: true,
                },
                startDate: {
                    required: true,
                    patternStartDate: true
                },
                endDate: {
                    required: true,
                    patternEndDate: true,
                    compareDate:true
                }
            },
            messages: {
                name: {
                    required: "Please enter a discount name",
                    max: "Name can not exceed 30 characters.",
                    patternName: "Name is not valid : only letters and -' are authorized.",
                },
                startDate: {
                    required: "Please enter a start date.",
                    patternStartDate: "Start date is not valid."
                },
                endDate: {
                    required: "Please enter a end date.",
                    patternEndDate: "End date is not valid.",
                    compareDate: "End date must be after start date."
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
