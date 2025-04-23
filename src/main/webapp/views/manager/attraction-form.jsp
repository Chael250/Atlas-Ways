<%--
  Created by IntelliJ IDEA.
  User: chael
  Date: 4/23/25
  Time: 1:58 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>${attraction != null ? 'Edit' : 'Add'} Attraction - AtlasWay</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; padding: 20px; }
        .container { max-width: 800px; margin: 0 auto; }
        .form-group { margin-bottom: 15px; }
        label { display: block; margin-bottom: 5px; font-weight: bold; }
        input[type="text"], input[type="number"], textarea, select {
            width: 100%;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
        }
        textarea { height: 100px; }
        .btn { padding: 10px 20px; background: #4CAF50; color: white; border: none; cursor: pointer; text-decoration: none; border-radius: 4px; }
        .btn-cancel { background: #f44336; margin-left: 10px; }
    </style>
</head>
<body>
    <div class="container">
        <h1>${attraction != null ? 'Edit' : 'Add'} Attraction</h1>
        <form action="/Atlas-Way/manager/attraction/save" method="post">
            <c:if test="${attraction != null}">
                <input type="hidden" name="attractionId" value="${attraction.attractionId}">
            </c:if>
            <div class="form-group">
                <label for="name">Name: </label>
                <input type="text" id="name" name="name" value="${attraction != null ? attraction.name : ''}" required>
            </div>
            <div class="form-group">
                <label for="description">Description:</label>
                <textarea id="description" name="description" required>${attraction != null ? attraction.description : ''}</textarea>
            </div>

            <div class="form-group">
                <label for="location">Address:</label>
                <input type="text" id="location" name="location" value="${attraction != null ? attraction.location : ''}" required>
            </div>

            <div class="form-group">
                <label for="city">City:</label>
                <input type="text" id="city" name="city" value="${attraction != null ? attraction.city : ''}" required>
            </div>

            <div class="form-group">
                <label for="country">Country:</label>
                <input type="text" id="country" name="country" value="${attraction != null ? attraction.country : ''}" required>
            </div>

            <div class="form-group">
                <label for="category">Category:</label>
                <select id="category" name="category" required>
                    <option value="">Select category</option>
                    <option value="Museum" ${attraction != null && attraction.category == 'Museum' ? 'selected' : ''}>Museum</option>
                    <option value="Historical Site" ${attraction != null && attraction.category == 'Historical Site' ? 'selected' : ''}>Historical Site</option>
                    <option value="Natural Landmark" ${attraction != null && attraction.category == 'Natural Landmark' ? 'selected' : ''}>Natural Landmark</option>
                    <option value="Park" ${attraction != null && attraction.category == 'Park' ? 'selected' : ''}>Park</option>
                    <option value="Entertainment" ${attraction != null && attraction.category == 'Entertainment' ? 'selected' : ''}>Entertainment</option>
                </select>
            </div>

            <div class="form-group">
                <label for="price">Price ($):</label>
                <input type="number" id="price" name="price" step="0.01" min="0" value="${attraction != null ? attraction.price : '0.00'}" required>
            </div>

            <div class="form-group">
                <label for="openingHours">Opening Hours:</label>
                <input type="text" id="openingHours" name="openingHours" value="${attraction != null ? attraction.openingHours : ''}" required>
            </div>

            <div class="form-group">
                <label for="contactInfo">Contact Information:</label>
                <input type="text" id="contactInfo" name="contactInfo" value="${attraction != null ? attraction.contactInfo : ''}" required>
            </div>

            <div class="form-group">
                <label for="imageUrl">Image URL:</label>
                <input type="text" id="imageUrl" name="imageUrl" value="${attraction != null ? attraction.imageUrl : ''}">
            </div>
            <div style="margin-top: 20px;">
                <button type="submit" class="btn">Save</button>
                <a href="/Atlas-Way/manager/attractions" class="btn btn-cancel">Cancel</a>
            </div>
        </form>
    </div>
</body>
</html>

