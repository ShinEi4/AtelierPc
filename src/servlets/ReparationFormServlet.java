package servlets;

import models.Reparation;
import models.Technicien;
import models.Ordinateur;
import models.Composant;
import utils.Connexion;

import javax.servlet.*;
import javax.servlet.http.*;

import javax.servlet.annotation.WebServlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;
import models.Composant;
import models.Ordinateur;
import models.Technicien;

@WebServlet("/reparationform")
public class ReparationFormServlet extends HttpServlet {

    // Affiche le formulaire de réparation
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (Connection connexion = Connexion.getConnexion()) {
            // Charger la liste des techniciens
            List<Technicien> techniciens = Technicien.getAll(connexion);
            request.setAttribute("techniciens", techniciens);
            
            // Charger la liste des ordinateurs
            List<Ordinateur> ordinateurs = Ordinateur.getAll(connexion);
            request.setAttribute("ordinateurs", ordinateurs);
            
            // Charger la liste des composants
            List<Composant> composants = Composant.getAll(connexion);
            request.setAttribute("composants", composants);
            
            // Charger la liste des réparations
            List<Reparation> reparations = Reparation.getAll(connexion);
            request.setAttribute("reparations", reparations);
            
            request.getRequestDispatcher("reparation_form.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
                "Erreur de connexion à la base de données: " + e.getMessage());
        }
    }

    // Traite l'envoi du formulaire de réparation
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Récupérer les paramètres du formulaire
            String dateDebutParam = request.getParameter("dateDebut");
            String dateFinParam = request.getParameter("dateFin");
            
            // Convertir le format datetime-local (2025-01-16T00:13) en format timestamp
            dateDebutParam = dateDebutParam.replace('T', ' ') + ":00";
            dateFinParam = dateFinParam.replace('T', ' ') + ":00";
            
            String descri = request.getParameter("descri");
            int technicienId = Integer.parseInt(request.getParameter("technicien"));
            int ordinateurId = Integer.parseInt(request.getParameter("ordinateur"));
            double prixMainDoeuvre = Double.parseDouble(request.getParameter("prixMainDoeuvre"));

            Timestamp dateDebut = Timestamp.valueOf(dateDebutParam);
            Timestamp dateFin = Timestamp.valueOf(dateFinParam);
            
            try (Connection connexion = Connexion.getConnexion()) {
                Technicien technicien = Technicien.getById(null, technicienId);
                Ordinateur ordinateur = Ordinateur.getById(null, ordinateurId);
                System.out.println("ordinateur: "+ordinateur.getModele().getNom());
                Reparation reparation = new Reparation(dateDebut, dateFin, descri, technicien, ordinateur, prixMainDoeuvre);
                reparation.save(connexion);
                doGet(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur lors de l'enregistrement de la réparation: " + e.getMessage());
        }
    }
}
