<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tourist Dashboard - AtlasWay</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; padding: 20px; }
        .dashboard-stats { display: flex; gap: 20px; margin-bottom: 20px; }
        .stat-card { background: #f5f5f5; padding: 15px; border-radius: 5px; flex: 1; }
        .visit-section { margin-bottom: 30px; }
        table { width: 100%; border-collapse: collapse; }
        th, td { text-align: left; padding: 8px; border-bottom: 1px solid #ddd; }
        .action-link { text-decoration: none; padding: 5px; background: #3498db; color: white; border-radius: 3px; }
    </style>
</head>
<body>
<h1>Tourist Dashboard</h1>

<div class="dashboard-stats">
    <div class="stat-card">
        <h3>Planned Visits</h3>
        <p>${plannedVisitsCount}</p>
    </div>
    <div class="stat-card">
        <h3>Completed Visits</h3>
        <p>${completedVisitsCount}</p>
    </div>
</div>

<div class="visit-section">
    <h2>Upcoming Visits</h2>
    <c:if test="${empty upcomingVisits}">
        <p>No upcoming visits planned.</p>
    </c:if>
    <c:if test="${not empty upcomingVisits}">
        <table>
            <thead>
            <tr>
                <th>Attraction</th>
                <th>Date</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="visit" items="${upcomingVisits}">
                <tr>
                    <td>${visit.attractionName}</td>
                    <td>${visit.visitDate}</td>
                    <td>
                        <a href="/Atlas-Way/tourist/visit/mark-visited?id=${visit.visitId}" class="action-link">Mark as Visited</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
    <p><a href="/Atlas-Way/tourist/visits?filter=planned">View all planned visits</a></p>
</div>

<div class="visit-section">
    <h2>Recent Visits</h2>
    <c:if test="${empty recentVisits}">
        <p>No completed visits yet.</p>
    </c:if>
    <c:if test="${not empty recentVisits}">
        <table>
            <thead>
            <tr>
                <th>Attraction</th>
                <th>Date</th>
                <th>Rating</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="visit" items="${recentVisits}">
                <tr>
                    <td>${visit.attractionName}</td>
                    <td>${visit.visitDate}</td>
                    <td>${visit.rating > 0 ? visit.rating : 'Not rated'}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
    <p><a href="/Atlas-Way/tourist/visits?filter=completed">View all completed visits</a></p>
</div>

<p><a href="/Atlas-Way/tourist/attractions">Browse Attractions</a></p>
<p><a href="/Atlas-Way/tourist/visit/add">Plan a New Visit</a></p>
</body>
</html>
