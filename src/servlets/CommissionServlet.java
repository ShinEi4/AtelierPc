package servlets;

import models.Commission;
import utils.Connexion;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

@WebServlet("/commission")
public class CommissionServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try (Connection connexion = Connexion.getConnexion()) {
            double currentPourcentage = Commission.getCurrentPourcentage(connexion);
            request.setAttribute("currentPourcentage", currentPourcentage);
            request.getRequestDispatcher("commission_form.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Erreur lors de la récupération du pourcentage actuel : " + e.getMessage());
            request.getRequestDispatcher("commission_form.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try (Connection connexion = Connexion.getConnexion()) {
            String pourcentageStr = request.getParameter("pourcentage");
            
            if (pourcentageStr == null || pourcentageStr.trim().isEmpty()) {
                throw new IllegalArgumentException("Le pourcentage est obligatoire");
            }
            
            double pourcentage = Double.parseDouble(pourcentageStr);
            if (pourcentage < 0 || pourcentage > 100) {
                throw new IllegalArgumentException("Le pourcentage doit être entre 0 et 100");
            }
            
            Commission commission = new Commission(pourcentage);
            commission.save(connexion);
            
            // Redirection avec message de succès
            request.setAttribute("success", "Commission mise à jour avec succès");
            doGet(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Erreur lors de la mise à jour de la commission : " + e.getMessage());
            doGet(request, response);
        }
    }
} 