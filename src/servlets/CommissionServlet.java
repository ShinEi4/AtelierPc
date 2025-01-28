package servlets;

import models.Commission;
import utils.Connexion;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

import java.io.IOException;
import java.sql.Connection;

@WebServlet("/commission")
public class CommissionServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try (Connection connexion = Connexion.getConnexion()) {
            Commission currentCommission = Commission.getCurrent(connexion);
            request.setAttribute("commission", currentCommission);
            request.getRequestDispatcher("commission_form.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Erreur lors de la récupération de la commission actuelle : " + e.getMessage());
            request.getRequestDispatcher("commission_form.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try (Connection connexion = Connexion.getConnexion()) {
            String pourcentageStr = request.getParameter("pourcentage");
            String prixMinStr = request.getParameter("prix_min");
            
            if (pourcentageStr == null || pourcentageStr.trim().isEmpty()) {
                throw new IllegalArgumentException("Le pourcentage est obligatoire");
            }
            if (prixMinStr == null || prixMinStr.trim().isEmpty()) {
                throw new IllegalArgumentException("Le prix minimum est obligatoire");
            }
            
            double pourcentage = Double.parseDouble(pourcentageStr);
            double prixMin = Double.parseDouble(prixMinStr);
            
            if (pourcentage < 0 || pourcentage > 100) {
                throw new IllegalArgumentException("Le pourcentage doit être entre 0 et 100");
            }
            if (prixMin < 0) {
                throw new IllegalArgumentException("Le prix minimum doit être positif");
            }
            
            Commission commission = new Commission(pourcentage, prixMin);
            commission.save(connexion);
            
            request.setAttribute("success", "Commission mise à jour avec succès");
            doGet(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Erreur lors de la mise à jour de la commission : " + e.getMessage());
            doGet(request, response);
        }
    }
} 