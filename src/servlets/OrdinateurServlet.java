package servlets;

import models.Ordinateur;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class OrdinateurServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Ordinateur> ordinateurs = Ordinateur.getAll(null); // Connexion à fournir
            request.setAttribute("ordinateurs", ordinateurs);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Erreur lors de la récupération des ordinateurs.");
        }
        request.getRequestDispatcher("/WEB-INF/ordinateur_list.jsp").forward(request, response);
    }
}
