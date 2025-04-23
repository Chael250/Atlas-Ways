package com.app.atlasway.controllers;
import com.app.atlasway.models.Attraction;
import com.app.atlasway.models.User;
import com.app.atlasway.models.Visit;
import com.app.atlasway.service.AttractionService;
import com.app.atlasway.service.VisitService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@WebServlet("/manager/*")
public class ManagerServlet extends HttpServlet{
    private AttractionService attractionService;
    private VisitService visitService;

    @Override
    public void init() throws ServletException {
        super.init();
        attractionService = new AttractionService();
        visitService = new VisitService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check if user is logged in and is a manager
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null ||
                !((User) session.getAttribute("user")).getUserType().equals(User.UserType.MANAGER)) {
            response.sendRedirect("/Atlas-Way/auth/login");
            return;
        }

        String pathInfo = request.getPathInfo();
        if (pathInfo == null) {
            pathInfo = "/";
        }

        try {
            switch (pathInfo) {
                case "/":
                case "/dashboard":
                    showDashboard(request, response);
                    break;
                case "/attractions":
                    listAttractions(request, response);
                    break;
                case "/attraction/add":
                    showAddAttractionForm(request, response);
                    break;
                case "/attraction/edit":
                    showEditAttractionForm(request, response);
                    break;
                case "/attraction/view":
                    viewAttraction(request, response);
                    break;
                case "/visits":
                    listVisits(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    break;
            }
        } catch (Exception e) {
            throw new ServletException("Error processing request", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check if user is logged in and is a manager
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null ||
                !((User) session.getAttribute("user")).getUserType().equals(User.UserType.MANAGER)) {
            response.sendRedirect("/Atlas-Way/auth/login");
            return;
        }

        String pathInfo = request.getPathInfo();
        if (pathInfo == null) {
            pathInfo = "/";
        }

        try {
            switch (pathInfo) {
                case "/attraction/save":
                    saveAttraction(request, response);
                    break;
                case "/attraction/delete":
                    deleteAttraction(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    break;
            }
        } catch (Exception e) {
            throw new ServletException("Error processing request", e);
        }
    }

    private void showDashboard(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        int managerId = (int) request.getSession().getAttribute("userId");

        try {
            List<Attraction> attractions = attractionService.getAttractionsByManagerId(managerId);
            int attractionCount = attractions.size();

            // For each attraction, get the number of visits
            int totalVisits = 0;
            int completedVisits = 0;
            for (Attraction attraction : attractions) {
                List<Visit> visits = visitService.getVisitsByAttractionId(attraction.getAttractionId());
                totalVisits += visits.size();
                for (Visit visit : visits) {
                    if (visit.isVisited()) {
                        completedVisits++;
                    }
                }
            }

            request.setAttribute("attractionCount", attractionCount);
            request.setAttribute("totalVisits", totalVisits);
            request.setAttribute("completedVisits", completedVisits);
            request.setAttribute("pendingVisits", totalVisits - completedVisits);

            request.getRequestDispatcher("/views/manager/dashboard.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "Error loading dashboard: " + e.getMessage());
            request.getRequestDispatcher("/views/error.jsp").forward(request, response);
        }
    }

    private void listAttractions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        int managerId = user.getUserId();

        try {
            List<Attraction> attractions = attractionService.getAttractionsByManagerId(managerId);
            request.setAttribute("attractions", attractions);
            request.getRequestDispatcher("/views/manager/attractions.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "Error loading attractions: " + e.getMessage());
            request.getRequestDispatcher("/views/error.jsp").forward(request, response);
        }
    }

    private void showAddAttractionForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/views/manager/attraction-form.jsp").forward(request, response);
    }

    private void showEditAttractionForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int attractionId = Integer.parseInt(request.getParameter("id"));
        User user = (User) request.getSession().getAttribute("user");
        int managerId = user.getUserId();

        try {
            Optional<Attraction> attractionOpt = attractionService.getAttractionById(attractionId);

            if (attractionOpt.isPresent()) {
                Attraction attraction = attractionOpt.get();

                // Ensure the manager owns this attraction
                if (attraction.getManagerId() != managerId) {
                    request.setAttribute("error", "You do not have permission to edit this attraction");
                    request.getRequestDispatcher("/views/error.jsp").forward(request, response);
                    return;
                }

                request.setAttribute("attraction", attraction);
                request.getRequestDispatcher("/views/manager/attraction-form.jsp").forward(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            request.setAttribute("error", "Error loading attraction: " + e.getMessage());
            request.getRequestDispatcher("/views/error.jsp").forward(request, response);
        }
    }

    private void viewAttraction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int attractionId = Integer.parseInt(request.getParameter("id"));
        User user = (User) request.getSession().getAttribute("user");
        int managerId = user.getUserId();

        try {
            Optional<Attraction> attractionOpt = attractionService.getAttractionById(attractionId);

            if (attractionOpt.isPresent()) {
                Attraction attraction = attractionOpt.get();

                // Ensure the manager owns this attraction
                if (attraction.getManagerId() != managerId) {
                    request.setAttribute("error", "You do not have permission to view this attraction");
                    request.getRequestDispatcher("/views/error.jsp").forward(request, response);
                    return;
                }

                // Get visits for this attraction
                List<Visit> visits = visitService.getVisitsByAttractionId(attractionId);

                request.setAttribute("attraction", attraction);
                request.setAttribute("visits", visits);
                request.getRequestDispatcher("/views/manager/attraction-view.jsp").forward(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            request.setAttribute("error", "Error loading attraction: " + e.getMessage());
            request.getRequestDispatcher("/views/error.jsp").forward(request, response);
        }
    }

    private void listVisits(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        int managerId = user.getUserId();

        try {
            List<Attraction> attractions = attractionService.getAttractionsByManagerId(managerId);
            request.setAttribute("attractions", attractions);

            // If an attraction ID is provided, show visits for that attraction
            String attractionIdParam = request.getParameter("attractionId");
            if (attractionIdParam != null && !attractionIdParam.isEmpty()) {
                int attractionId = Integer.parseInt(attractionIdParam);
                // Verify manager owns this attraction
                boolean owns = false;
                for (Attraction attraction : attractions) {
                    if (attraction.getAttractionId() == attractionId) {
                        owns = true;
                        break;
                    }
                }

                if (!owns) {
                    request.setAttribute("error", "You do not have permission to view these visits");
                    request.getRequestDispatcher("/views/error.jsp").forward(request, response);
                    return;
                }

                List<Visit> visits = visitService.getVisitsByAttractionId(attractionId);
                request.setAttribute("visits", visits);
                request.setAttribute("selectedAttractionId", attractionId);
            }

            request.getRequestDispatcher("/views/manager/visits.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "Error loading visits: " + e.getMessage());
            request.getRequestDispatcher("/views/error.jsp").forward(request, response);
        }
    }

    private void saveAttraction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        int managerId = user.getUserId();

        // Get attraction data from form
        String attractionIdParam = request.getParameter("attractionId");
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String location = request.getParameter("location");
        String city = request.getParameter("city");
        String country = request.getParameter("country");
        String category = request.getParameter("category");
        String priceParam = request.getParameter("price");
        String openingHours = request.getParameter("openingHours");
        String contactInfo = request.getParameter("contactInfo");
        String imageUrl = request.getParameter("imageUrl");

        try {
            Attraction attraction = new Attraction();

            // If attractionId is present, this is an update
            if (attractionIdParam != null && !attractionIdParam.isEmpty()) {
                int attractionId = Integer.parseInt(attractionIdParam);
                Optional<Attraction> existingAttrOpt = attractionService.getAttractionById(attractionId);

                if (!existingAttrOpt.isPresent()) {
                    request.setAttribute("error", "Attraction not found");
                    request.getRequestDispatcher("/views/error.jsp").forward(request, response);
                    return;
                }

                Attraction existingAttr = existingAttrOpt.get();

                // Ensure the manager owns this attraction
                if (existingAttr.getManagerId() != managerId) {
                    request.setAttribute("error", "You do not have permission to edit this attraction");
                    request.getRequestDispatcher("/views/error.jsp").forward(request, response);
                    return;
                }

                attraction.setAttractionId(attractionId);
            }

            attraction.setManagerId(managerId);
            attraction.setName(name);
            attraction.setDescription(description);
            attraction.setLocation(location);
            attraction.setCity(city);
            attraction.setCountry(country);
            attraction.setCategory(category);
            attraction.setPrice(new BigDecimal(priceParam));
            attraction.setOpeningHours(openingHours);
            attraction.setContactInfo(contactInfo);
            attraction.setImageUrl(imageUrl);

            attractionService.createAttraction(attraction);

            // Redirect to attractions list with success message
            response.sendRedirect(request.getContextPath() + "/manager/attractions?success=Operation completed successfully");

        } catch (Exception e) {
            request.setAttribute("error", "Error saving attraction: " + e.getMessage());
            request.getRequestDispatcher("/views/error.jsp").forward(request, response);
        }
    }

    private void deleteAttraction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int attractionId = Integer.parseInt(request.getParameter("id"));
        User user = (User) request.getSession().getAttribute("user");
        int managerId = user.getUserId();

        try {
            Optional<Attraction> attractionOpt = attractionService.getAttractionById(attractionId);

            if (attractionOpt.isPresent()) {
                Attraction attraction = attractionOpt.get();

                // Ensure the manager owns this attraction
                if (attraction.getManagerId() != managerId) {
                    request.setAttribute("error", "You do not have permission to delete this attraction");
                    request.getRequestDispatcher("/views/error.jsp").forward(request, response);
                    return;
                }

                boolean deleted = attractionService.deleteAttraction(attractionId);

                if (deleted) {
                    response.sendRedirect(request.getContextPath() + "/manager/attractions?success=Attraction deleted successfully");
                } else {
                    request.setAttribute("error", "Failed to delete attraction");
                    request.getRequestDispatcher("/views/error.jsp").forward(request, response);
                }
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            request.setAttribute("error", "Error deleting attraction: " + e.getMessage());
            request.getRequestDispatcher("/views/error.jsp").forward(request, response);
        }
    }
}
