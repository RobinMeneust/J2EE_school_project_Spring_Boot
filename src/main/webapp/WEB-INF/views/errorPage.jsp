<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Error Page</title>
</head>
<body>

<h2>Error Details</h2>

<%
    // Retrieve the exception object from the model
    Exception exception = (Exception) request.getAttribute("exception");

    if (exception != null) {
%>
<div>
    <p><strong>Error Message:</strong> <%= exception.getMessage() %></p>
    <p><strong>Stack Trace:</strong></p>
    <pre><%= exception.getStackTrace() %></pre>
</div>
<%
} else {
%>
<p>No exception details available.</p>
<%
    }
%>

</body>
</html>
