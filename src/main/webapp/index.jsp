<%--
  Created by IntelliJ IDEA.
  User: chael
  Date: 4/18/25
  Time: 8:06 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>AtlasWay - Explore The World</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            line-height: 1.6;
            margin: 0;
            padding: 0;
            color: #333;
        }

        .container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 20px;
        }

        header {
            background-color: #2c3e50;
            color: white;
            padding: 20px 0;
            text-align: center;
        }

        h1 {
            font-size: 2.5em;
            margin-bottom: 0;
        }

        .tagline {
            font-style: italic;
            margin-top: 5px;
        }

        .cta-section {
            background-color: #f9f9f9;
            padding: 40px 0;
            text-align: center;
        }

        .btn {
            display: inline-block;
            background-color: #3498db;
            color: white;
            padding: 12px 24px;
            margin: 10px;
            text-decoration: none;
            border-radius: 4px;
            font-weight: bold;
            transition: background-color 0.3s;
        }

        .btn:hover {
            background-color: #2980b9;
        }

        .btn.secondary {
            background-color: #2ecc71;
        }

        .btn.secondary:hover {
            background-color: #27ae60;
        }

        .features {
            display: flex;
            flex-wrap: wrap;
            justify-content: space-between;
            margin: 40px 0;
        }

        .feature {
            flex-basis: 30%;
            margin-bottom: 30px;
            padding: 20px;
            border-radius: 8px;
            background-color: #f5f5f5;
            text-align: center;
        }

        .feature h3 {
            color: #2c3e50;
        }

        footer {
            background-color: #2c3e50;
            color: white;
            text-align: center;
            padding: 20px 0;
            margin-top: 40px;
        }

        @media (max-width: 768px) {
            .feature {
                flex-basis: 100%;
            }
        }
    </style>
</head>
<body>
<header>
    <div class="container">
        <h1>AtlasWay</h1>
        <p class="tagline">Discover and organize your perfect travel itinerary</p>
    </div>
</header>

<section class="cta-section">
    <div class="container">
        <h2>Start Your Journey Today!</h2>
        <p>Join thousands of travelers who use AtlasWay to plan and track their visits to attractions worldwide.</p>
        <a href="/Atlas-Way/auth/login" class="btn">Login</a>
        <a href="/Atlas-Way/auth/register" class="btn secondary">Register</a>
    </div>
</section>

<section class="container">
    <h2>Why Choose AtlasWay?</h2>
    <div class="features">
        <div class="feature">
            <h3>For Tourists</h3>
            <p>Discover new attractions, plan your visits, and keep track of places you've been.</p>
        </div>
        <div class="feature">
            <h3>For Attraction Managers</h3>
            <p>List your attractions, monitor visits, and get valuable feedback from tourists.</p>
        </div>
        <div class="feature">
            <h3>Easy to Use</h3>
            <p>Simple interface that makes planning and managing travel experiences effortless.</p>
        </div>
    </div>
</section>

<footer>
    <div class="container">
        <p>&copy; 2025 AtlasWay - Your guide to the world's attractions</p>
    </div>
</footer>
</body>
</html>
