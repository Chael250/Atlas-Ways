<%--
  Created by IntelliJ IDEA.
  User: chael
  Date: 4/23/25
  Time: 2:47 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Visits - AtlasWay</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; padding: 20px; }
        .filter-tabs { display: flex; margin-bottom: 20px; }
        .filter-tab { padding: 10px 15px; text-decoration: none; color: #333; border: 1px solid #ddd; }
        .filter-tab.active { background-color: #3498db; color: white; }
        table { width: 100%; border-collapse: collapse; }
        th, td { text-align: left; padding: 10px; border-bottom: 1px solid #ddd; }
        .action-button { display: inline-block; margin: 2px; padding: 5px 8px; text-decoration: none; color: white; border-radius: 3px; font-size: 0.8em; }
        .btn-primary { background-color: #3498db; }
        .btn-success { background-color: #2ecc71; }
        .btn-danger { background-color: #e74c3c; }
        .btn-warning { background-color: #f39c12; }
        .success-message { background-color: #d4edda; color: #155724; padding: 10px; border-radius: 5px; margin-bottom: 20px; }
    </style>
</head>
<body>
<h1>My Visits</h1>

<c:if test="${param.success != null}">
    <div class="success-message">${param.success}</div>
</c:if>

<div class="filter-tabs">
    <a href="Atlas-Way/tourist/visits?filter=all" class="filter-tab ${filterType == 'all' ? 'active' : ''}">All Visits</a>
    <a href="/Atlas-Way/tourist/visits?filter=planned" class="filter-tab ${filterType == 'planned' ? 'active' : ''}">Planned</a>
    <a href="/Atlas-Way/tourist/visits?filter=completed" class="filter-tab ${filterType == 'completed' ? 'active' : ''}">Completed</a>
</div>

<c:if test="${empty visits}">
    <p>No visits found.</p>
</c:if>

<c:if test="${not empty visits}">
    <table>
        <thead>
        <tr>
            <th>Attraction</th>
            <th>Date</th>
            <th>Status</th>
            <th>Rating</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="visit" items="${visits}">
            <tr>
                <td>${visit.attractionName}</td>
                <td>${visit.visitDate}</td>
                <td>${visit.visited ? 'Completed' : 'Planned'}</td>
                <td>${visit.rating > 0 ? visit.rating : '-'}</td>
                <td>
                    <c:if test="${!visit.visited}">
                        <form action="/Atlas-Way/tourist/visit/mark-visited" method="post" style="display:inline;">
                            <input type="hidden" name="id" value="${visit.visitId}">
                            <button type="submit" class="action-button btn-success">Mark Visited</button>
                        </form>
                        <form action="${pageContext.request.contextPath}/tourist/visit/delete" method="post" style="display:inline;" onsubmit="return confirm('Are you sure you want to delete this visit?');">
                            <input type="hidden" name="id" value="${visit.visitId}">
                            <button type="submit" class="action-button btn-danger">Delete</button>
                        </form>
                    </c:if>
                    <c:if test="${visit.visited && visit.rating == 0}">
                        <button onclick="showReviewForm(${visit.visitId})" class="action-button btn-warning">Add Review</button>
                    </c:if>
                    <a href="/Atlas-Way/tourist/attraction?id=${visit.attractionId}" class="action-button btn-primary">View Attraction</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>

<div id="review-form" style="display:none; margin-top: 20px; background: #f9f9f9; padding: 15px; border-radius: 5px;">
    <h3>Submit Review</h3>
    <form action="/Atlas-Way/tourist/visit/review" method="post">
        <input type="hidden" id="visitId" name="visitId" value="">
        <div style="margin-bottom: 10px;">
            <label for="rating">Rating (1-5):</label>
            <select id="rating" name="rating" required style="padding: 5px;">
                <option value="1">1 - Poor</option>
                <option value="2">2 - Fair</option>
                <option value="3">3 - Good</option>
                <option value="4">4 - Very Good</option>
                <option value="5">5 - Excellent</option>
            </select>
        </div>
        <div style="margin-bottom: 10px;">
            <label for="review">Review:</label>
            <textarea id="review" name="review" rows="4" style="width: 100%; padding: 5px;" required></textarea>
        </div>
        <button type="submit" class="action-button btn-success" style="padding: 8px 15px;">Submit Review</button>
        <button type="button" onclick="hideReviewForm()" class="action-button btn-danger" style="padding: 8px 15px;">Cancel</button>
    </form>
</div>

<p style="margin-top: 20px;">
    <a href="/Atlas-Way/tourist/visit/add" class="action-button btn-primary" style="padding: 10px 15px;">Plan a New Visit</a>
    <a href="/Atlas-Way/tourist/dashboard" style="margin-left: 10px;">Back to Dashboard</a>
</p>

<script>
    function showReviewForm(visitId) {
        document.getElementById('visitId').value = visitId;
        document.getElementById('review-form').style.display = 'block';
    }

    function hideReviewForm() {
        document.getElementById('review-form').style.display = 'none';
    }
</script>
</body>
</html>
