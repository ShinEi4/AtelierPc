package servlets;

import models.Composant;
import models.Marque;
import models.Modele;
import utils.Connexion;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/modeleform")
public class ModeleFormServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection connexion = null;
        try {
            connexion = Connexion.getConnexion();

            // Récupération des marques disponibles
            List<Marque> marques = Marque.getAll(connexion);
            request.setAttribute("marques", marques);

            // Récupération des composants disponibles
            List<Composant> composants = Composant.getAll(connexion);
            request.setAttribute("composants", composants);

            // Redirection vers le formulaire JSP
            request.getRequestDispatcher("/formModele.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Erreur lors de la préparation des données : " + e.getMessage());
            request.getRequestDispatcher("/formModele.jsp").forward(request, response);
        } finally {
            if (connexion != null) {
                try {
                    connexion.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nomModele = request.getParameter("nomModele");
        String idMarqueStr = request.getParameter("idMarque");
        String[] composantsIds = request.getParameterValues("composants");

        String message;
        Connection connexion = null;

        try {
            if (nomModele == null || nomModele.trim().isEmpty() || idMarqueStr == null || idMarqueStr.isEmpty()) {
                message = "Le nom du modèle et la marque sont obligatoires.";
                request.setAttribute("errorMessage", message);
                request.getRequestDispatcher("/formModele.jsp").forward(request, response);
                return;
            }

            int idMarque = Integer.parseInt(idMarqueStr);
            connexion = Connexion.getConnexion();

            // Récupération de la marque associée
            Marque marque = Marque.getById(connexion, idMarque);

            // Création du modèle
            Modele modele = new Modele(nomModele, marque);

            // Ajout des composants sélectionnés
            if (composantsIds != null) {
                for (String idComposantStr : composantsIds) {
                    int idComposant = Integer.parseInt(idComposantStr);
                    Composant composant = Composant.getById(connexion, idComposant);
                    modele.getComposants().add(composant);
                }
            }

            // Sauvegarde du modèle dans la base de données
            modele.save(connexion);

            message = "Modèle ajouté avec succès !";
            request.setAttribute("successMessage", message);
            response.sendRedirect("/ModeleFormServlet"); // Redirection pour éviter une double soumission
        } catch (Exception e) {
            e.printStackTrace();
            message = "Erreur lors de l'ajout du modèle : " + e.getMessage();
            request.setAttribute("errorMessage", message);
            request.getRequestDispatcher("/formModele.jsp").forward(request, response);
        } finally {
            if (connexion != null) {
                try {
                    connexion.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
