package servlets;



import models.Marque;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

import java.io.IOException;
import java.util.List;

@WebServlet("/marques")
public class MarqueServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Marque> marques = Marque.getAll(null); // Connexion doit être fournie
            request.setAttribute("marques", marques);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Erreur lors de la récupération des marques.");
        }

        // Rediriger vers la page de liste des marques
        request.getRequestDispatcher("/WEB-INF/marque_list.jsp").forward(request, response);
    }
}
