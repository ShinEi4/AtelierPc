package servlets;



import models.Reparation;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class ReparationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Reparation> reparations = Reparation.getAll(null); // Connexion à fournir
            request.setAttribute("reparations", reparations);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Erreur lors de la récupération des réparations.");
        }

        request.getRequestDispatcher("/WEB-INF/reparation_list.jsp").forward(request, response);
    }
}
