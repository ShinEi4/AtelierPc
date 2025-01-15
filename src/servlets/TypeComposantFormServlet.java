package servlets;

import models.TypeComposant;
import utils.Connexion;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/typecomposantform")
public class TypeComposantFormServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("type_composant_form.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nomType = request.getParameter("nomType");
        String message;

        // Validation des champs
        if (nomType == null || nomType.isEmpty()) {
            message = "Le champ nomType est obligatoire.";
            request.setAttribute("errorMessage", message);
            request.getRequestDispatcher("type_composant_form.jsp").forward(request, response);
            return;
        }

        Connection connexion = null;

        try {
            connexion = Connexion.getConnexion();
            TypeComposant typeComposant = new TypeComposant(nomType);
            typeComposant.save(connexion);

            message = "Type de composant ajouté avec succès !";
            request.setAttribute("successMessage", message);
            request.getRequestDispatcher("/type_composant_form.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            message = "Erreur lors de l'ajout du type de composant : " + e.getMessage();
            request.setAttribute("errorMessage", message);
            request.getRequestDispatcher("/type_composant_list.jsp").forward(request, response);
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
