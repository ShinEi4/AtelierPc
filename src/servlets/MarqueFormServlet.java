package servlets;

import models.Marque;
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

@WebServlet("/marqueform")
public class MarqueFormServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("marque_form.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nom = request.getParameter("nom");
        String message;
        Connection connexion = null;

        try {
            if (nom == null || nom.trim().isEmpty()) {
                message = "Le nom de la marque est obligatoire.";
                request.setAttribute("errorMessage", message);
                doGet(request, response);
                return;
            }

            connexion = Connexion.getConnexion();
            Marque marque = new Marque(nom);
            marque.save(connexion);

            message = "Marque ajoutée avec succès !";
            request.setAttribute("successMessage", message);
            doGet(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            message = "Erreur lors de l'ajout de la marque : " + e.getMessage();
            request.setAttribute("errorMessage", message);
            doGet(request, response);
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
