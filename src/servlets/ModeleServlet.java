package servlets;

import models.Modele;
import utils.Connexion;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/modeles")
public class ModeleServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection connexion = null;

        try {
            // Récupérer une connexion
            connexion = Connexion.getConnexion();

            // Récupérer la liste des modèles
            List<Modele> modeles = Modele.getAll(connexion);

            // Ajouter la liste des modèles à la requête
            request.setAttribute("modeles", modeles);

            // Forward vers la vue (ex. une JSP pour afficher les modèles)
            RequestDispatcher dispatcher = request.getRequestDispatcher("modele_list.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            // Gestion des erreurs
            e.printStackTrace();
            request.setAttribute("error", "Erreur lors de la récupération des modèles : " + e.getMessage());

            // Redirection vers une page d'erreur ou un message d'erreur dans la vue
            RequestDispatcher dispatcher = request.getRequestDispatcher("modele_list.jsp");
            dispatcher.forward(request, response);

        } finally {
            // Assurer la fermeture de la connexion
            if (connexion != null) {
                try {
                    connexion.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
