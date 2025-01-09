package servlets;

import models.CategorieModele;
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
import java.util.ArrayList;
import java.util.List;
import models.CategorieModele;

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
            request.getRequestDispatcher("/WEB-INF/formModele.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Erreur lors de la préparation des données : " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
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
        String idCategorie= request.getParameter("idCategorieModele");
        String[] composantsIds = request.getParameterValues("composants");

        String message;
        Connection connexion = null;

        try {
            // Validation des champs obligatoires
            if (nomModele == null || nomModele.trim().isEmpty() || idMarqueStr == null || idMarqueStr.isEmpty()  || idCategorie == null || idCategorie.isEmpty()) {
                message = "Le nom du modèle , la marque et la categorie sont obligatoires.";
                request.setAttribute("errorMessage", message);
                request.getRequestDispatcher("/WEB-INF/modele_form.jsp").forward(request, response);
                return;
            }

            int idMarque = Integer.parseInt(idMarqueStr);
            connexion = Connexion.getConnexion();

            // Récupération de la marque associée
            Marque marque = Marque.getById(connexion, idMarque);
            CategorieModele cm = CategorieModele.getById(connexion, Integer.parseInt(idCategorie));

            if (marque == null) {
                message = "La marque sélectionnée n'existe pas.";
                request.setAttribute("errorMessage", message);
                request.getRequestDispatcher("/WEB-INF/modele_form.jsp").forward(request, response);
                return;
            }

            // Création du modèle
            Modele modele = new Modele(nomModele, marque,cm);

            // Ajout des composants sélectionnés
            if (composantsIds != null) {
                List<Composant> composants = new ArrayList<>();
                for (String idComposantStr : composantsIds) {
                    int idComposant = Integer.parseInt(idComposantStr);
                    Composant composant = Composant.getById(connexion, idComposant);
                    if (composant != null) {
                        composants.add(composant);
                    }
                }
                modele.setComposants(composants);
            }

            // Sauvegarde du modèle dans la base de données
            modele.save(connexion);

            message = "Modèle ajouté avec succès !";
            request.setAttribute("successMessage", message);

            // Redirection pour éviter une double soumission
            response.sendRedirect(request.getContextPath() + "/modeleform");

        } catch (Exception e) {
            e.printStackTrace();
            message = "Erreur lors de l'ajout du modèle : " + e.getMessage();
            request.setAttribute("errorMessage", message);
            request.getRequestDispatcher("/WEB-INF/formModele.jsp").forward(request, response);
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
