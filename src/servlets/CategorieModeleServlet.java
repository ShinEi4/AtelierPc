package servlets;



import models.CategorieModele;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

import java.io.IOException;
import java.util.List;

@WebServlet("/categoriesmodeles")
public class CategorieModeleServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<CategorieModele> categories = CategorieModele.getAll(null); // Connexion doit être fournie
            request.setAttribute("categoriesmodeles", categories);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Erreur lors de la récupération des marques.");
        }

        // Rediriger vers la page de liste des marques
        request.getRequestDispatcher("categorie_list.jsp").forward(request, response);
    }
}
