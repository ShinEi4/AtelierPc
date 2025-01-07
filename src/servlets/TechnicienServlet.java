package servlets;



import models.Technicien;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class TechnicienServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Technicien> techniciens = Technicien.getAll(null); // Connexion à fournir
            request.setAttribute("techniciens", techniciens);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Erreur lors de la récupération des techniciens.");
        }
        request.getRequestDispatcher("/WEB-INF/technicien_list.jsp").forward(request, response);
    }
}
