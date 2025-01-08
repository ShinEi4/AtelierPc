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
