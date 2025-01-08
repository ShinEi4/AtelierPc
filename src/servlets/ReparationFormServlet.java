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
import java.util.List;

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
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/reparationForm.jsp");
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
        Timestamp dateDebut = Timestamp.valueOf(request.getParameter("dateDebut"));
        Timestamp dateFin = Timestamp.valueOf(request.getParameter("dateFin"));
        String descri = request.getParameter("descri");
        int technicienId = Integer.parseInt(request.getParameter("technicien"));
        int ordinateurId = Integer.parseInt(request.getParameter("ordinateur"));
        double prixMainDoeuvre = Double.parseDouble(request.getParameter("prixMainDoeuvre"));

        // Récupérer la liste des composants sélectionnés
        String[] composantsIds = request.getParameterValues("composants");
        
        // Créer l'objet Reparation
        Technicien technicien = new Technicien(technicienId); // Vous pouvez récupérer plus d'informations selon votre modèle
        Ordinateur ordinateur = new Ordinateur(ordinateurId); // Idem pour ordinateur
        Reparation reparation = new Reparation(dateDebut, dateFin, descri, technicien, ordinateur, prixMainDoeuvre);

        try (Connection connexion = Connexion.getConnexion()) {
            // Sauvegarder la réparation dans la base
            reparation.save(connexion);

            // Ajouter les composants sélectionnés
            if (composantsIds != null) {
                for (String composantId : composantsIds) {
                    int idComposant = Integer.parseInt(composantId);
                    Composant composant = new Composant(idComposant); // Vous pouvez ajouter plus de détails sur le composant
                    reparation.ajouterComposant(composant, connexion);
                }
            }

            // Rediriger vers une page de confirmation ou la liste des réparations
            response.sendRedirect(request.getContextPath() + "/reparations");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur lors de l'enregistrement de la réparation");
        }
    }
}
