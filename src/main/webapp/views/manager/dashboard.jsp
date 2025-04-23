
    <%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false"%>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>

    <!doctype html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport"
              content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <title>Manager Dashboard - AtlasWay</title>
        <style>
            body{
                font-family: Arial, sans-serif;
                margin: 0;
                padding: 20px;
            }
            .dashboard{
                max-width: 1000px;
                margin: 0 auto;
            }
            .stats{
                display: flex;
                justify-content: space-between;
                margin-bottom: 20px;
            }
            .stat-card{
                background: #f5f5f5;
                border-radius: 5px;
                padding: 15px;
                width: 23%;
            }
            .stat-number{
                font-size: 24px;
                font-weight: bold;
            }
            .actions{
                margin-top: 20px;
            }
            .btn{
                padding: 8px 16px;
                background: #4caf50;
                color: white;
                border: none;
                cursor: pointer;
                text-decoration: none;
                border-radius: 4px;
            }
        </style>
    </head>
    <body>
        <div class="dashboard">
            <h1>Manager Dashboard</h1>
            <div class="stats">
                <div class="stat-card">
                    <h3>Attractions</h3>
                    <div class="stat-number"><c:out value="${attractionCount}" default="0"/></div>
                </div>
                <div class="stat-card">
                    <h3>Total Visits</h3>
                    <div class="stat-number"><c:out value="${totalVisits}" default="0"/></div>
                </div>
                <div class="stat-card">
                    <h3>Completed Visits</h3>
                    <div class="stat-number"><c:out value="${completedVisits}" default="0"/></div>
                </div>
                <div class="stat-card">
                    <h3>Pending Visits</h3>
                    <div class="stat-number"><c:out value="${pendingVisits}" default="0"/></div>
                </div>
            </div>
            <div class="actions">
                <h2>Quick Actions</h2>
                <a href="/Atlas-Way/manager/attractions" class="btn">Manage Attractions</a>
                <a href="/Atlas-Way/manager/attraction/view" class="btn">View Visits</a>
                <a href="/Atlas-Way/manager/attraction/add" class="btn">Add New Attractions</a>
            </div>
        </div>
    </body>
    </html>