package com.app.atlasway.controllers;
import com.app.atlasway.models.User;
import com.app.atlasway.models.Visit;
import com.app.atlasway.service.AttractionService;
import com.app.atlasway.service.VisitService;
import com.app.atlasway.models.Attraction;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@WebServlet("/tourist/*")
public class TouristServlet extends HttpServlet{
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
        // Check if user is logged in and is a tourist
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null ||
                !((User) session.getAttribute("user")).getUserType().equals(User.UserType.TOURIST)) {
            response.sendRedirect(request.getContextPath() + "/auth/login");
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
                    showAttractions(request, response);
                    break;
                case "/attraction":
                    viewAttraction(request, response);
                    break;
                case "/visits":
                    showVisits(request, response);
                    break;
                case "/visit/add":
                    showAddVisitForm(request, response);
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
        // Check if user is logged in and is a tourist
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null ||
                !((User) session.getAttribute("user")).getUserType().equals(User.UserType.TOURIST)) {
            response.sendRedirect(request.getContextPath() + "/auth/login");
            return;
        }

        String pathInfo = request.getPathInfo();
        if (pathInfo == null) {
            pathInfo = "/";
        }

        try {
            switch (pathInfo) {
                case "/visit/save":
                    saveVisit(request, response);
                    break;
                case "/visit/mark-visited":
                    markVisitAsCompleted(request, response);
                    break;
                case "/visit/delete":
                    deleteVisit(request, response);
                    break;
                case "/visit/review":
                    submitReview(request, response);
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
        int touristId = user.getUserId();

        try {
            List<Visit> plannedVisits = visitService.getPlannedVisitsByTouristId(touristId);
            List<Visit> completedVisits = visitService.getCompletedVisitsByTouristId(touristId);

            request.setAttribute("plannedVisitsCount", plannedVisits.size());
            request.setAttribute("completedVisitsCount", completedVisits.size());

            // Get upcoming visits (planned visits with nearest date)
            plannedVisits.sort((v1, v2) -> v1.getVisitDate().compareTo(v2.getVisitDate()));
            List<Visit> upcomingVisits = plannedVisits.size() > 3 ? plannedVisits.subList(0, 3) : plannedVisits;

            // Get recent visits (completed visits with most recent date)
            completedVisits.sort((v1, v2) -> v2.getVisitDate().compareTo(v1.getVisitDate()));
            List<Visit> recentVisits = completedVisits.size() > 3 ? completedVisits.subList(0, 3) : completedVisits;

            request.setAttribute("upcomingVisits", upcomingVisits);
            request.setAttribute("recentVisits", recentVisits);

            request.getRequestDispatcher("/views/tourist/dashboard.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "Error loading dashboard: " + e.getMessage());
            request.getRequestDispatcher("/views/error.jsp").forward(request, response);
        }
    }

    private void viewAttraction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int attractionId = Integer.parseInt(request.getParameter("id"));

        try {
            Optional<Attraction> attractionOpt = attractionService.getAttractionById(attractionId);

            if (attractionOpt.isPresent()) {
                request.setAttribute("attraction", attractionOpt.get());
                request.getRequestDispatcher("/views/tourist/attraction-view.jsp").forward(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            request.setAttribute("error", "Error loading attraction: " + e.getMessage());
            request.getRequestDispatcher("/views/error.jsp").forward(request, response);
        }
    }

    private void showVisits(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        int touristId = user.getUserId();

        try {
            String filter = request.getParameter("filter");
            List<Visit> visits;

            if ("planned".equals(filter)) {
                visits = visitService.getPlannedVisitsByTouristId(touristId);
                request.setAttribute("filterType", "planned");
            } else if ("completed".equals(filter)) {
                visits = visitService.getCompletedVisitsByTouristId(touristId);
                request.setAttribute("filterType", "completed");
            } else {
                visits = visitService.getVisitsByTouristId(touristId);
                request.setAttribute("filterType", "all");
            }

            request.setAttribute("visits", visits);
            request.getRequestDispatcher("/views/tourist/visits.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "Error loading visits: " + e.getMessage());
            request.getRequestDispatcher("/views/error.jsp").forward(request, response);
        }
    }

    private void showAddVisitForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String attractionIdParam = request.getParameter("attractionId");

        try {
            if (attractionIdParam != null && !attractionIdParam.isEmpty()) {
                int attractionId = Integer.parseInt(attractionIdParam);
                Optional<Attraction> attractionOpt = attractionService.getAttractionById(attractionId);

                if (attractionOpt.isPresent()) {
                    request.setAttribute("attraction", attractionOpt.get());
                }
            } else {
                // If no attraction selected, show all attractions to choose from
                List<Attraction> attractions = attractionService.getAllAttractions();
                request.setAttribute("attractions", attractions);
            }

            request.getRequestDispatcher("/views/tourist/visit-form.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "Error loading form: " + e.getMessage());
            request.getRequestDispatcher("/views/error.jsp").forward(request, response);
        }
    }

    private void saveVisit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        int touristId = user.getUserId();

        int attractionId = Integer.parseInt(request.getParameter("attractionId"));
        String visitDateStr = request.getParameter("visitDate");

        try {
            LocalDate visitDate = LocalDate.parse(visitDateStr);

            Visit visit = new Visit(touristId, attractionId, visitDate);
            visitService.createVisit(visit);

            response.sendRedirect(request.getContextPath() + "/tourist/visits?filter=planned&success=Visit planned successfully");
        } catch (Exception e) {
            request.setAttribute("error", "Error saving visit: " + e.getMessage());
            request.getRequestDispatcher("/views/error.jsp").forward(request, response);
        }
    }

    private void markVisitAsCompleted(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int visitId = Integer.parseInt(request.getParameter("id"));
        User user = (User) request.getSession().getAttribute("user");
        int touristId = user.getUserId();

        try {
            Optional<Visit> visitOpt = visitService.getVisitById(visitId);

            if (visitOpt.isPresent()) {
                Visit visit = visitOpt.get();

                // Ensure the tourist owns this visit
                if (visit.getTouristId() != touristId) {
                    request.setAttribute("error", "You do not have permission to update this visit");
                    request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
                    return;
                }

                boolean updated = visitService.markVisitAsCompleted(visitId);

                if (updated) {
                    response.sendRedirect(request.getContextPath() + "/tourist/visits?filter=completed&success=Visit marked as completed");
                } else {
                    request.setAttribute("error", "Failed to update visit");
                    request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
                }
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            request.setAttribute("error", "Error updating visit: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }

    private void deleteVisit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int visitId = Integer.parseInt(request.getParameter("id"));
        User user = (User) request.getSession().getAttribute("user");
        int touristId = user.getUserId();

        try {
            Optional<Visit> visitOpt = visitService.getVisitById(visitId);

            if (visitOpt.isPresent()) {
                Visit visit = visitOpt.get();

                // Ensure the tourist owns this visit
                if (visit.getTouristId() != touristId) {
                    request.setAttribute("error", "You do not have permission to delete this visit");
                    request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
                    return;
                }

                boolean deleted = visitService.deleteVisit(visitId);

                if (deleted) {
                    response.sendRedirect(request.getContextPath() + "/tourist/visits?success=Visit deleted successfully");
                } else {
                    request.setAttribute("error", "Failed to delete visit");
                    request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
                }
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            request.setAttribute("error", "Error deleting visit: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }

    private void submitReview(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int visitId = Integer.parseInt(request.getParameter("visitId"));
        User user = (User) request.getSession().getAttribute("user");
        int touristId = user.getUserId();

        int rating = Integer.parseInt(request.getParameter("rating"));
        String review = request.getParameter("review");

        try {
            Optional<Visit> visitOpt = visitService.getVisitById(visitId);

            if (visitOpt.isPresent()) {
                Visit visit = visitOpt.get();

                // Ensure the tourist owns this visit
                if (visit.getTouristId() != touristId) {
                    request.setAttribute("error", "You do not have permission to review this visit");
                    request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
                    return;
                }

                // Ensure visit is marked as completed
                if (!visit.isVisited()) {
                    request.setAttribute("error", "You can only review completed visits");
                    request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
                    return;
                }

                visit.setRating(rating);
                visit.setReview(review);

                visitService.updateVisit(visit);

                response.sendRedirect(request.getContextPath() + "/tourist/visits?filter=completed&success=Review submitted successfully");
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            request.setAttribute("error", "Error submitting review: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }

    private void showAttractions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String searchQuery = request.getParameter("search");
            List<Attraction> attractions;

            if (searchQuery != null && !searchQuery.trim().isEmpty()) {
                attractions = attractionService.searchAttractions(searchQuery);
                request.setAttribute("searchQuery", searchQuery);
            } else {
                attractions = attractionService.getAllAttractions();
            }

            request.setAttribute("attractions", attractions);
            request.getRequestDispatcher("/WEB-INF/views/tourist/attractions.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "Error loading attractions: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }
}
