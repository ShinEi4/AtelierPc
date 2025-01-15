package servlets;

import models.Ordinateur;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

import java.io.IOException;
import java.util.List;

@WebServlet("/ordinateurs")
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
        request.getRequestDispatcher("/ordinateur_list.jsp").forward(request, response);
    }
}
