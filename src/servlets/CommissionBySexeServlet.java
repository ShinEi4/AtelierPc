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

@WebServlet("/commissions-by-sexe")
public class CommissionBySexeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (Connection connexion = Connexion.getConnexion()) {
            // Récupérer les dates du formulaire
            String dateDebutStr = request.getParameter("dateDebut");
            String dateFinStr = request.getParameter("dateFin");
            
            // Convertir les dates si elles ne sont pas nulles
            LocalDateTime dateDebut = null;
            LocalDateTime dateFin = null;
            
            if (dateDebutStr != null && !dateDebutStr.isEmpty()) {
                dateDebut = LocalDate.parse(dateDebutStr).atTime(LocalTime.MIN);
            }
            if (dateFinStr != null && !dateFinStr.isEmpty()) {
                dateFin = LocalDate.parse(dateFinStr).atTime(LocalTime.MAX);
            }

            // Récupérer les commissions par sexe
            List<Technicien> techniciens = Technicien.getCommissionsBySexe(connexion, dateDebut, dateFin);
            
            // Stocker les résultats et les dates pour l'affichage
            request.setAttribute("techniciens", techniciens);
            if (dateDebut != null) request.setAttribute("dateDebut", dateDebutStr);
            if (dateFin != null) request.setAttribute("dateFin", dateFinStr);
            
            request.getRequestDispatcher("commission_by_sexe.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Erreur lors de la récupération des commissions : " + e.getMessage());
            request.getRequestDispatcher("commission_by_sexe.jsp").forward(request, response);
        }
    }
} 