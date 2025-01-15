package servlets;

import models.Composant;
import models.Reparation;
import utils.Connexion;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

@WebServlet("/reparationcomposant")
public class ReparationComposantServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try (Connection connexion = Connexion.getConnexion()) {
            int idReparation = Integer.parseInt(request.getParameter("reparation"));
            int idComposant = Integer.parseInt(request.getParameter("composant"));
            String probleme = request.getParameter("probleme");

            // Récupérer la réparation et le composant
            Reparation reparation = Reparation.getById(connexion, idReparation);
            Composant composant = Composant.getById(connexion, idComposant);

            // Ajouter le composant à la réparation avec le problème
            reparation.ajouterComposant(composant, probleme, connexion);

            // Rediriger vers la page du formulaire avec un message de succès
            response.sendRedirect(request.getContextPath() + "/reparationform");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
                "Erreur lors de l'ajout du composant à la réparation: " + e.getMessage());
        }
    }
} 