package servlets;

import models.Client;
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

@WebServlet("/clientform")
public class ClientFormServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nom = request.getParameter("nom");
        String num = request.getParameter("num");
        String message;

        // Validation des champs
        if (nom == null || nom.isEmpty() || num == null || num.isEmpty()) {
            message = "Les champs nom et numéro sont obligatoires.";
            request.setAttribute("errorMessage", message);
            request.getRequestDispatcher("/formClient.jsp").forward(request, response);
            return;
        }

        Connection connexion = null;

        try {
            connexion = Connexion.getConnexion();
            Client client = new Client(nom, num);
            client.save(connexion);

            message = "Client ajouté avec succès !";
            request.setAttribute("successMessage", message);
            request.getRequestDispatcher("/formClient.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            message = "Erreur lors de l'ajout du client : " + e.getMessage();
            request.setAttribute("errorMessage", message);
            request.getRequestDispatcher("/formClient.jsp").forward(request, response);
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
