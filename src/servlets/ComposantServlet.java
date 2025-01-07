package servlets;



import models.Composant;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class ComposantServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Composant> composants = Composant.getAll(null); // Connexion à fournir
            request.setAttribute("composants", composants);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Erreur lors de la récupération des composants.");
        }

        request.getRequestDispatcher("/WEB-INF/composant_list.jsp").forward(request, response);
    }
}
