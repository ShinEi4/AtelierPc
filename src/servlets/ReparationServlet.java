package servlets;

import models.Reparation;
import models.TypeComposant;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.List;

@WebServlet("/reparations")
public class ReparationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String typeComposant=null;
        String categorieModele=null;
        if (request.getParameter("typeComposant") !=null) {
            typeComposant = request.getParameter("typeComposant");
        }
        if (request.getParameter("categorieModele") !=null) {
            categorieModele = request.getParameter("categorieModele");
            
        }

        try {
            List<TypeComposant> tc = TypeComposant.getAll(null); // Connexion à fournir
            request.setAttribute("typescomposants", tc);
            List<Reparation> reparations;

            if ("recherche".equalsIgnoreCase(action) && typeComposant != null && !typeComposant.isEmpty()) {
                // Recherche par type de composant
                reparations = Reparation.getByComposantType(null, typeComposant); // Connexion à fournir
            } else if ("rechercheretour".equalsIgnoreCase(action)) {
                // Recherche des retours
                reparations = Reparation.getRetours(null,typeComposant,categorieModele); // Connexion à fournir
            } else {
                // Récupérer toutes les réparations
                reparations = Reparation.getAll(null); // Connexion à fournir
            }

            request.setAttribute("reparations", reparations);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Erreur lors de la récupération des réparations.");
        }

        // Rediriger vers la page de liste des réparations
        request.getRequestDispatcher("/WEB-INF/reparation_list.jsp").forward(request, response);
    }
}
