<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<html>
<head>
  <title>Manage Attractions - AtlasWay</title>
  <style>
    body{
      font-family: Arial, sans-serif;
      margin: 0;
      padding: 20px;
    }
    .container{
      max-width: 1000px;
      margin: 0 auto;
    }
    .header{
      display: flex;
      justify-content: space-between;
      align-items: center;
    }
    table{
      width: 100%;
      border-collapse: collapse;
      margin-top: 20px;
    }
    th, td{
      padding: 10px;
      text-align: left;
      border-bottom: 1px solid #ddd;
    }
    tr:hover{
      background-color: #f5f5f5;
    }
    .btn{
      padding: 8px 16px;
      background: #4CAF50;
      color: white;
      border: none;
      cursor: pointer;
      text-decoration: none;
      border-radius: 4px;
    }
    .btn-view{
      background: #2196F3;
    }
    .btn-edit{
      background: #FF9800;
    }
    .btn-delete{
      background: #f44336;
    }
    .success-message{
      background: #dff0d8;
      color: #3c763d;
      padding: 10px;
      border-radius: 4px;
      margin-bottom: 20px;
    }
  </style>
</head>
<body>
<div class="container">
  <div class="header">
    <h1>Manage Attractions</h1>
    <a href="/Atlas-Way/manager/attraction/add" class="btn">Add New Attraction</a>
  </div>
  <c:if test="${param.sucess != null}">
    <div class="success-message">${param.success}</div>
  </c:if>

  <table>
    <thead>
    <tr>
      <th>Name</th>
      <th>Location</th>
      <th>Category</th>
      <th>Price</th>
      <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${attractions}" var="attraction">
      <tr>
        <td>${attraction.name}</td>
        <td>${attraction.city}, ${attraction.country}</td>
        <td>${attraction.category}</td>
        <td>$${attraction.price}</td>
        <td>
          <c:url var="viewUrl" value="/manager/attraction/view">
            <c:param name="id" value="${attraction.attractionId}" />
          </c:url>
          <a href="${viewUrl}" class="btn btn-view">View</a>
          <c:url var="editUrl" value="/manager/attraction/edit">
            <c:param name="id" value="${attraction.attractionId}" />
          </c:url>
          <a href="${editUrl}" class="btn btn-edit">Edit</a>
          <form action="/Atlas-Way/manager/attraction/delete" method="post" style="display: inline;">
            <input type="hidden" name="id" value="${attraction.attractionId}">
            <button type="submit" class="btn btn-delete" onclick="return confirm('Are you sure you want to delete this attraction?')">Delete</button>
          </form>
        </td>
      </tr>
    </c:forEach>
    </tbody>
  </table>
  <div style="margin-top: 20px;">
    <a href="/Atlas-Way/manager/dashboard" class="btn">Back to Dashboard</a>
  </div>
</div>
</body>
</html>
