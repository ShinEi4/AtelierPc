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
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nomMarque = request.getParameter("nomMarque");
        String message;
        Connection connexion = null;

        try {
            if (nomMarque == null || nomMarque.trim().isEmpty()) {
                message = "Le nom de la marque est obligatoire.";
                request.setAttribute("errorMessage", message);
                request.getRequestDispatcher("/formMarque.jsp").forward(request, response);
                return;
            }

            connexion = Connexion.getConnexion();
            Marque marque = new Marque(nomMarque);
            marque.save(connexion);

            message = "Marque ajoutée avec succès !";
            request.setAttribute("successMessage", message);
            request.getRequestDispatcher("/formMarque.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            message = "Erreur lors de l'ajout de la marque : " + e.getMessage();
            request.setAttribute("errorMessage", message);
            request.getRequestDispatcher("/formMarque.jsp").forward(request, response);
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
