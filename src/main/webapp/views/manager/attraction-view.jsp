<%--
  Created by IntelliJ IDEA.
  User: chael
  Date: 4/23/25
  Time: 2:06 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>${attraction.name} - AtlasWay</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; padding: 20px; }
        .container { max-width: 1000px; margin: 0 auto; }
        .attraction-header { display: flex; margin-bottom: 20px; }
        .attraction-image { width: 300px; height: 200px; object-fit: cover; margin-right: 20px; border-radius: 5px; }
        .attraction-details { flex: 1; }
        .info-item { margin-bottom: 10px; }
        .info-label { font-weight: bold; }
        .visits-section { margin-top: 30px; }
        table { width: 100%; border-collapse: collapse; }
        th, td { padding: 10px; text-align: left; border-bottom: 1px solid #ddd; }
        tr:hover { background-color: #f5f5f5; }
        .btn { padding: 8px 16px; background: #4CAF50; color: white; border: none; cursor: pointer; text-decoration: none; border-radius: 4px; display: inline-block; margin-right: 5px; }
        .btn-edit { background: #FF9800; }
    </style>
</head>
<body>
<div class="container">
    <h1>${attraction.name}</h1>

    <div class="attraction-header">
        <c:choose>
            <c:when test="${not empty attraction.imageUrl}">
                <img src="${attraction.imageUrl}" alt="${attraction.name}" class="attraction-image">
            </c:when>
            <c:otherwise>
                <div class="attraction-image" style="background-color: #f0f0f0; display: flex; align-items: center; justify-content: center;">
                    No Image Available
                </div>
            </c:otherwise>
        </c:choose>

        <div class="attraction-details">
            <div class="info-item">
                <span class="info-label">Location:</span> ${attraction.location}, ${attraction.city}, ${attraction.country}
            </div>
            <div class="info-item">
                <span class="info-label">Category:</span> ${attraction.category}
            </div>
            <div class="info-item">
                <span class="info-label">Price:</span> $${attraction.price}
            </div>
            <div class="info-item">
                <span class="info-label">Opening Hours:</span> ${attraction.openingHours}
            </div>
            <div class="info-item">
                <span class="info-label">Contact:</span> ${attraction.contactInfo}
            </div>
        </div>
    </div>

    <div>
        <h3>Description</h3>
        <p>${attraction.description}</p>
    </div>

    <div class="visits-section">
        <h2>Visit Information</h2>

        <c:choose>
            <c:when test="${empty visits}">
                <p>No visits recorded for this attraction yet.</p>
            </c:when>
            <c:otherwise>
                <table>
                    <thead>
                    <tr>
                        <th>Visitor</th>
                        <th>Planned Date</th>
                        <th>Status</th>
                        <th>Visited On</th>
                        <th>Rating</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${visits}" var="visit">
                        <tr>
                            <td>${visit.userName}</td>
                            <td><fmt:formatDate value="${visit.plannedDate}" pattern="yyyy-MM-dd" /></td>
                            <td>${visit.visited ? 'Completed' : 'Planned'}</td>
                            <td>
                                <c:if test="${visit.visited}">
                                    <fmt:formatDate value="${visit.visitedDate}" pattern="yyyy-MM-dd" />
                                </c:if>
                            </td>
                            <td>${visit.visited ? visit.rating : 'N/A'}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>
    </div>

    <div style="margin-top: 20px;">
        <a href="/Atlas-Way/manager/attraction/edit?id=${attraction.attractionId}" class="btn btn-edit">Edit Attraction</a>
        <a href="/Atlas-Way/manager/attractions" class="btn">Back to Attractions</a>
    </div>
</div>
</body>
</html>
