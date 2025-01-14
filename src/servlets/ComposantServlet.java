package servlets;



import models.Composant;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

import java.io.IOException;
import java.util.List;

@WebServlet("/composants")
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

        request.getRequestDispatcher("composant_list.jsp").forward(request, response);
    }
}
