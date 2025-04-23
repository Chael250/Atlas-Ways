<%--
  Created by IntelliJ IDEA.
  User: chael
  Date: 4/23/25
  Time: 2:46 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${attraction.name} - AtlasWay</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; padding: 20px; }
        .attraction-details { margin-bottom: 30px; }
        .attraction-meta { display: flex; gap: 20px; margin: 15px 0; }
        .meta-item { background: #f5f5f5; padding: 10px; border-radius: 3px; }
        .description { line-height: 1.6; }
        .action-button { display: inline-block; padding: 10px 15px; background: #3498db; color: white; text-decoration: none; border-radius: 5px; margin-top: 20px; }
    </style>
</head>
<body>
<div class="attraction-details">
    <h1>${attraction.name}</h1>

    <div class="attraction-meta">
        <div class="meta-item">
            <strong>Location:</strong> ${attraction.city}, ${attraction.country}
        </div>
        <div class="meta-item">
            <strong>Category:</strong> ${attraction.category}
        </div>
        <c:if test="${not empty attraction.avgRating}">
            <div class="meta-item">
                <strong>Rating:</strong> ${attraction.avgRating}/5 (${attraction.numRatings} reviews)
            </div>
        </c:if>
    </div>

    <div class="description">
        <h3>Description</h3>
        <p>${attraction.description}</p>
    </div>

    <c:if test="${not empty attraction.openingHours}">
        <div>
            <h3>Opening Hours</h3>
            <p>${attraction.openingHours}</p>
        </div>
    </c:if>

    <c:if test="${not empty attraction.entryFee}">
        <div>
            <h3>Entry Fee</h3>
            <p>${attraction.entryFee}</p>
        </div>
    </c:if>

    <a href="/Atlas-Way/tourist/visit/add?attractionId=${attraction.attractionId}" class="action-button">Plan a Visit</a>
</div>

<p><a href="/Atlas-Way/tourist/attractions">Back to Attractions</a></p>
</body>
</html>
