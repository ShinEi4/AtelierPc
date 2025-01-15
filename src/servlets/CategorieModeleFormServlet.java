package servlets;

import models.CategorieModele;
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

@WebServlet("/categorieform")
public class CategorieModeleFormServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/categorie_form.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nom = request.getParameter("nom");
        String message;

        // Validation des champs
        if (nom == null || nom.isEmpty()) {
            message = "Les champs nom et numéro sont obligatoires.";
            request.setAttribute("errorMessage", message);
            request.getRequestDispatcher("/categorie_form.jsp").forward(request, response);
            return;
        }

        Connection connexion = null;

        try {
            connexion = Connexion.getConnexion();
            CategorieModele categorie = new CategorieModele(nom);
            categorie.save(connexion);

            message = "Categorie ajouté avec succès !";
            request.setAttribute("successMessage", message);
            request.getRequestDispatcher("/categorie_form.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            message = "Erreur lors de l'ajout de la categorie : " + e.getMessage();
            request.setAttribute("errorMessage", message);
            request.getRequestDispatcher("/categorie_form.jsp").forward(request, response);
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
