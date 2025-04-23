<%--
  Created by IntelliJ IDEA.
  User: chael
  Date: 4/23/25
  Time: 2:45 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Attractions - AtlasWay</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; padding: 20px; }
        .search-form { margin-bottom: 20px; }
        .search-input { padding: 8px; width: 300px; }
        .search-button { padding: 8px 15px; background: #3498db; color: white; border: none; cursor: pointer; }
        .attraction-grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(250px, 1fr)); gap: 20px; }
        .attraction-card { border: 1px solid #ddd; border-radius: 5px; padding: 15px; }
        .attraction-name { margin-top: 0; }
        .view-link { display: inline-block; text-decoration: none; padding: 5px 10px; background: #3498db; color: white; border-radius: 3px; margin-top: 10px; }
    </style>
</head>
<body>
<h1>Explore Attractions</h1>

<form action="/Atlas-Way/tourist/attractions" method="get" class="search-form">
    <input type="text" name="search" placeholder="Search attractions..." value="${searchQuery}" class="search-input">
    <button type="submit" class="search-button">Search</button>
</form>

<div class="attraction-grid">
    <c:forEach var="attraction" items="${attractions}">
        <div class="attraction-card">
            <h3 class="attraction-name">${attraction.name}</h3>
            <p>${attraction.city}, ${attraction.country}</p>
            <p>${attraction.description.length() > 100 ? attraction.description.substring(0, 100).concat('...') : attraction.description}</p>
            <a href="/Atlas-Way/tourist/attraction?id=${attraction.attractionId}" class="view-link">View Details</a>
        </div>
    </c:forEach>

    <c:if test="${empty attractions}">
        <p>No attractions found. Try a different search term.</p>
    </c:if>
</div>

<p><a href="/Atlas-Way/tourist/dashboard">Back to Dashboard</a></p>
</body>
</html>
