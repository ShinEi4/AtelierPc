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
        // Récupérer les données pour les sélecteurs (techniciens, ordinateurs, composants)
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
            
            // Rediriger vers la page du formulaire de réparation
            RequestDispatcher dispatcher = request.getRequestDispatcher("reparation_form.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur de connexion à la base de données");
        }
    }

    // Traite l'envoi du formulaire de réparation
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Récupérer les paramètres du formulaire
        String dateDebutParam = request.getParameter("dateDebut");
        String dateFinParam = request.getParameter("dateFin");
        String descri = request.getParameter("descri");
        int technicienId = Integer.parseInt(request.getParameter("technicien"));
        int ordinateurId = Integer.parseInt(request.getParameter("ordinateur"));
        double prixMainDoeuvre = Double.parseDouble(request.getParameter("prixMainDoeuvre"));

        // Récupérer la liste des composants sélectionnés
        String[] composantsIds = request.getParameterValues("composants");

        Timestamp dateDebut = Timestamp.valueOf(dateDebutParam);
        Timestamp dateFin = Timestamp.valueOf(dateFinParam);
        
        // Créer l'objet Reparation
        

        try (Connection connexion = Connexion.getConnexion()) {
            Technicien technicien = Technicien.getById(null, technicienId); // Vous pouvez récupérer plus d'informations selon votre modèle
            Ordinateur ordinateur = Ordinateur.getById(null, ordinateurId); // Idem pour ordinateur
            Reparation reparation = new Reparation(dateDebut, dateFin, descri, technicien, ordinateur, prixMainDoeuvre);
            // Sauvegarder la réparation dans la base
            reparation.save(connexion);

            // Rediriger vers une page de confirmation ou la liste des réparations
            response.sendRedirect(request.getContextPath() + "/reparations");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur lors de l'enregistrement de la réparation");
        }
    }
}
