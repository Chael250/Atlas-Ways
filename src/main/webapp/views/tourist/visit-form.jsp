<%--
  Created by IntelliJ IDEA.
  User: chael
  Date: 4/23/25
  Time: 2:47 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Plan a Visit - AtlasWay</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; padding: 20px; }
        .form-container { max-width: 600px; margin: 0 auto; }
        .form-group { margin-bottom: 15px; }
        label { display: block; margin-bottom: 5px; font-weight: bold; }
        select, input { width: 100%; padding: 8px; box-sizing: border-box; }
        .submit-button { background-color: #3498db; color: white; border: none; padding: 10px 20px; cursor: pointer; }
        .cancel-link { display: inline-block; margin-left: 10px; }
    </style>
</head>
<body>
<div class="form-container">
    <h1>Plan a Visit</h1>

    <form action="/Atlas-Way/tourist/visit/save" method="post">
        <div class="form-group">
            <label for="attractionId">Select Attraction:</label>
            <c:choose>
                <c:when test="${not empty attraction}">
                    <input type="hidden" name="attractionId" value="${attraction.attractionId}">
                    <p>${attraction.name} - ${attraction.city}, ${attraction.country}</p>
                </c:when>
                <c:otherwise>
                    <select id="attractionId" name="attractionId" required>
                        <option value="">-- Select an Attraction --</option>
                        <c:forEach var="attr" items="${attractions}">
                            <option value="${attr.attractionId}">${attr.name} - ${attr.city}, ${attr.country}</option>
                        </c:forEach>
                    </select>
                </c:otherwise>
            </c:choose>
        </div>

        <div class="form-group">
            <label for="visitDate">Visit Date:</label>
            <input type="date" id="visitDate" name="visitDate" required min="${java.time.LocalDate.now()}">
        </div>

        <div class="form-group">
            <button type="submit" class="submit-button">Plan Visit</button>
            <a href="/Atlas-Way/tourist/visits" class="cancel-link">Cancel</a>
        </div>
    </form>
</div>

<script>
    // Set min date to today
    document.addEventListener('DOMContentLoaded', function() {
        var today = new Date();
        var dd = String(today.getDate()).padStart(2, '0');
        var mm = String(today.getMonth() + 1).padStart(2, '0');
        var yyyy = today.getFullYear();
        today = yyyy + '-' + mm + '-' + dd;
        document.getElementById('visitDate').min = today;
    });
</script>
</body>
</html>
