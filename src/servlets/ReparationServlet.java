package servlets;

import models.Reparation;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.List;

@WebServlet("/reparations")
public class ReparationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String typeComposant = request.getParameter("typeComposant");
        List<Reparation> reparations;

        try {
            // Si l'action est une recherche et qu'un paramètre typeComposant est fourni
            if ("recherche".equalsIgnoreCase(action) && typeComposant != null && !typeComposant.isEmpty()) {
                reparations = Reparation.getByComposantType(null, typeComposant); // Connexion à fournir
            } else {
                // Sinon, récupérer toutes les réparations
                reparations = Reparation.getAll(null); // Connexion à fournir
            }
            request.setAttribute("reparations", reparations);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Erreur lors de la récupération des réparations.");
        }

        // Rediriger vers la page de liste des réparations
        request.getRequestDispatcher("/WEB-INF/reparation_list.jsp").forward(request, response);
    }
}
