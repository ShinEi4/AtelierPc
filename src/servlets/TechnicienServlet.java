package servlets;



import models.Technicien;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

import java.io.IOException;
import java.util.List;

@WebServlet("/techniciens")
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
