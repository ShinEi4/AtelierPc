package servlets;

import models.Client;
import models.Modele;
import models.Ordinateur;
import utils.Connexion;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/ordinateurform")
public class OrdinateurFormServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection connexion = null;
        try {
            // Récupération des modèles
            connexion = Connexion.getConnexion();
            List<Modele> modeles = Modele.getAll(connexion);
            request.setAttribute("modeles", modeles);

            // Récupération des clients
            List<Client> clients = Client.getAll(connexion);
            request.setAttribute("clients", clients);

            // Redirection vers la page JSP du formulaire
            request.getRequestDispatcher("ordinateur_form.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Erreur lors du chargement des données pour le formulaire : " + e.getMessage());
            request.getRequestDispatcher("ordinateur_form.jsp").forward(request, response);
        } finally {
            if (connexion != null) {
                try {
                    connexion.close();
                } catch (Exception ignored) {
                }
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection connexion = null;
        try {
            connexion = Connexion.getConnexion();

            // Récupération des données du formulaire
            String idSerie = request.getParameter("id_serie");
            int idModele = Integer.parseInt(request.getParameter("id_modele"));
            int idClient = Integer.parseInt(request.getParameter("id_client"));

            // Validation des champs obligatoires
            if (idSerie == null || idSerie.trim().isEmpty() || idModele <= 0 || idClient <= 0) {
                throw new IllegalArgumentException("Tous les champs obligatoires doivent être remplis.");
            }

            // Création des objets associés
            Modele modele = Modele.getById(connexion, idModele);
            Client client = Client.getById(connexion, idClient);

            if (modele == null || client == null) {
                throw new IllegalArgumentException("Modèle ou client invalide.");
            }

            // Création de l'ordinateur
            Ordinateur ordinateur = new Ordinateur(idSerie, modele, client);
            ordinateur.save(connexion);

            // Redirection après succès
            doGet(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Erreur lors de la soumission du formulaire : " + e.getMessage());
            doGet(request, response);
        } finally {
            if (connexion != null) {
                try {
                    connexion.close();
                } catch (Exception ignored) {
                }
            }
        }
    }
}
