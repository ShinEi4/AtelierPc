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

            // Récupération des categories disponibles
            List<CategorieModele> categoriesmodeles = CategorieModele.getAll(connexion);
            request.setAttribute("categoriesmodeles", categoriesmodeles);

            // Redirection vers le formulaire JSP
            request.getRequestDispatcher("modele_form.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Erreur lors de la préparation des données : " + e.getMessage());
            request.getRequestDispatcher("modele_form.jsp").forward(request, response);
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
        String nomModele = request.getParameter("nom");
        String idMarqueStr = request.getParameter("marque");
        String idCategorie= request.getParameter("categorie");

        String message;
        Connection connexion = null;

        try {
            // Validation des champs obligatoires
            if (nomModele == null || nomModele.trim().isEmpty() || idMarqueStr == null || idMarqueStr.isEmpty()  || idCategorie == null || idCategorie.isEmpty()) {
                message = "Le nom du modèle , la marque et la categorie sont obligatoires.";
                request.setAttribute("errorMessage", message);
                request.getRequestDispatcher("modele_form.jsp").forward(request, response);
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
                request.getRequestDispatcher("modele_form.jsp").forward(request, response);
                return;
            }

            // Création du modèle
            Modele modele = new Modele(nomModele, marque,cm);

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
            request.getRequestDispatcher("modele_form.jsp").forward(request, response);
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
