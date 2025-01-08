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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Récupérer la liste des modèles
            Connection connexion = Connexion.getConnexion();
            List<Modele> modeles = Modele.getAll(connexion);

            // Ajouter la liste des modèles à la requête
            request.setAttribute("modeles", modeles);

            // Forward vers la vue (ex. une JSP pour afficher les modèles)
            request.getRequestDispatcher("/WEB-INF/modeles.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Erreur lors de la récupération des modeles.");
        }
    }
}
