package servlets;

import models.Technicien;
import utils.Connexion;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@WebServlet("/techniciens")
public class TechnicienServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (Connection connexion = Connexion.getConnexion()) {
            String action = request.getParameter("action");
            List<Technicien> techniciens;

            if ("recherche_commission".equals(action)) {
                // Récupérer les dates du formulaire
                String dateDebutStr = request.getParameter("dateDebut");
                String dateFinStr = request.getParameter("dateFin");
                
                // Convertir les dates si elles ne sont pas nulles
                LocalDateTime dateDebut = null;
                LocalDateTime dateFin = null;
                
                if (dateDebutStr != null && !dateDebutStr.isEmpty()) {
                    // Convertir la date en LocalDateTime avec l'heure à 00:00:00
                    dateDebut = LocalDate.parse(dateDebutStr)
                        .atTime(LocalTime.MIN);
                }
                if (dateFinStr != null && !dateFinStr.isEmpty()) {
                    // Convertir la date en LocalDateTime avec l'heure à 23:59:59
                    dateFin = LocalDate.parse(dateFinStr)
                        .atTime(LocalTime.MAX);
                }

                // Récupérer les techniciens avec leurs commissions
                techniciens = Technicien.getAllWithCommission(connexion, dateDebut, dateFin);
                
                // Stocker les dates pour l'affichage dans la vue
                if (dateDebut != null) {
                    request.setAttribute("dateDebut", dateDebutStr);
                }
                if (dateFin != null) {
                    request.setAttribute("dateFin", dateFinStr);
                }
            } else {
                // Liste simple des techniciens sans commission
                techniciens = Technicien.getAll(connexion);
            }

            request.setAttribute("techniciens", techniciens);
            request.getRequestDispatcher("technicien_list.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Erreur lors de la récupération des techniciens : " + e.getMessage());
            request.getRequestDispatcher("technicien_list.jsp").forward(request, response);
        }
    }
}
