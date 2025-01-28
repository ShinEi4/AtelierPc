package servlets;

import models.Reparation;
import models.Composant;
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
        
        try {
            if ("details".equals(action)) {
                int idReparation = Integer.parseInt(request.getParameter("id"));
                Reparation reparation = Reparation.getById(null, idReparation);
                List<Composant> composants = Reparation.getComposantsByReparationId(null, idReparation);
                request.setAttribute("composants", composants);
                request.setAttribute("reparation", reparation);
                request.getRequestDispatcher("reparation_details.jsp").forward(request, response);
                return;
            }

            
            String typeComposant = request.getParameter("typeComposant");
            String categorieModele = request.getParameter("categorieModele");
            
            List<TypeComposant> tc = TypeComposant.getAll(null);
            request.setAttribute("typescomposants", tc);
            List<Reparation> reparations;

            if ("recherche".equalsIgnoreCase(action) && typeComposant != null && !typeComposant.isEmpty()) {
                reparations = Reparation.getByComposantType(null, typeComposant);
            } else if ("rechercheretour".equalsIgnoreCase(action)) {
                reparations = Reparation.getRetours(null, typeComposant, categorieModele);
            } else {
                reparations = Reparation.getAll(null);
            }

            request.setAttribute("reparations", reparations);
            request.getRequestDispatcher("reparation_list.jsp").forward(request, response);
            
        } catch (Exception e) {
            System.out.println("erreur"+e.getMessage());
            try {
                List<Reparation> reparations = Reparation.getAll(null);
                request.setAttribute("reparations", reparations);
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
            request.setAttribute("error", "Erreur lors de la récupération des données: " + e.getMessage());
            request.getRequestDispatcher("reparation_list.jsp").forward(request, response);
        }
    }
}
