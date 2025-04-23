<%--
  Created by IntelliJ IDEA.
  User: chael
  Date: 4/23/25
  Time: 2:07 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
  <title>Manage Visits - AtlasWay</title>
  <style>
    body { font-family: Arial, sans-serif; margin: 0; padding: 20px; }
    .container { max-width: 1000px; margin: 0 auto; }
    .filter-section { margin-bottom: 20px; padding: 15px; background: #f5f5f5; border-radius: 5px; }
    select { padding: 8px; border-radius: 4px; border: 1px solid #ddd; }
    table { width: 100%; border-collapse: collapse; }
    th, td { padding: 10px; text-align: left; border-bottom: 1px solid #ddd; }
    tr:hover { background-color: #f5f5f5; }
    .btn { padding: 8px 16px; background: #4CAF50; color: white; border: none; cursor: pointer; text-decoration: none; border-radius: 4px; }
    .btn-apply { margin-left: 10px; }
    .completed { color: #4CAF50; }
    .planned { color: #FF9800; }
  </style>
</head>
<body>
<div class="container">
  <h1>Manage Visits</h1>

  <div class="filter-section">
    <form action="/Atlas-Way /manager/visits" method="get">
      <label for="attractionId">Filter by Attraction:</label>
      <select id="attractionId" name="attractionId">
        <option value="">All Attractions</option>
        <c:forEach items="${attractions}" var="attraction">
          <option value="${attraction.attractionId}" ${selectedAttractionId == attraction.attractionId ? 'selected' : ''}>
              ${attraction.name}
          </option>
        </c:forEach>
      </select>
      <button type="submit" class="btn btn-apply">Apply Filter</button>
    </form>
  </div>

  <c:choose>
    <c:when test="${empty visits && selectedAttractionId == null}">
      <p>Please select an attraction to view visits.</p>
    </c:when>
    <c:when test="${empty visits && selectedAttractionId != null}">
      <p>No visits found for the selected attraction.</p>
    </c:when>
    <c:otherwise>
      <table>
        <thead>
        <tr>
          <th>Attraction</th>
          <th>Visitor</th>
          <th>Planned Date</th>
          <th>Status</th>
          <th>Visited On</th>
          <th>Rating</th>
          <th>Comments</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${visits}" var="visit">
          <tr>
            <td>${visit.attractionName}</td>
            <td>${visit.userName}</td>
            <td><fmt:formatDate value="${visit.plannedDate}" pattern="yyyy-MM-dd" /></td>
            <td class="${visit.visited ? 'completed' : 'planned'}">
                ${visit.visited ? 'Completed' : 'Planned'}
            </td>
            <td>
              <c:if test="${visit.visited}">
                <fmt:formatDate value="${visit.visitedDate}" pattern="yyyy-MM-dd" />
              </c:if>
            </td>
            <td>${visit.visited ? visit.rating : 'N/A'}</td>
            <td>${visit.comment}</td>
          </tr>
        </c:forEach>
        </tbody>
      </table>
    </c:otherwise>
  </c:choose>

  <div style="margin-top: 20px;">
    <a href="/Atlas-Way/manager/dashboard" class="btn">Back to Dashboard</a>
  </div>
</div>
</body>
</html>
