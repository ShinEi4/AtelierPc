package servlets;


import models.TypeComposant;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class TypeComposantServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<TypeComposant> tc = TypeComposant.getAll(null); // Connexion à fournir
            request.setAttribute("typescomposants", tc);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Erreur lors de la récupération des types de composants.");
        }
        request.getRequestDispatcher("/WEB-INF/type_composant_list.jsp").forward(request, response);
    }
}
