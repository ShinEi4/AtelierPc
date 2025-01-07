package servlets;



import models.Composant;
import models.TypeComposant;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;

public class ComposantFormServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String composantId = request.getParameter("idComposant");

        if (composantId != null) {
            try {
                Composant composant = Composant.getById(null,Integer.parseInt(composantId)); // Connexion à fournir
                request.setAttribute("composant", composant);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        request.getRequestDispatcher("/WEB-INF/composant_form.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nomComposant = request.getParameter("nomComposant");
        String prixComposant = request.getParameter("prixComposant");
        String typeComposant = request.getParameter("idTypeComposant");

        try {
            Composant composant;
            String composantId = request.getParameter("idComposant");

            if (composantId != null && !composantId.isEmpty()) {
                composant = Composant.getById(null,Integer.parseInt(composantId) );
                composant.setNom(nomComposant);
                composant.setPrix(Double.parseDouble(prixComposant));
                composant.setTypeComposant(TypeComposant.getById(null,(Integer.parseInt(typeComposant))));
                composant.save(null); // Connexion à fournir
            } else {
                composant = new Composant(nomComposant, TypeComposant.getById(null, Integer.parseInt(typeComposant)), Double.parseDouble(prixComposant));
                composant.save(null); // Connexion à fournir
            }

            response.sendRedirect(request.getContextPath() + "/composant");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Erreur lors de la sauvegarde du composant.");
            request.getRequestDispatcher("/WEB-INF/composant_form.jsp").forward(request, response);
        }
    }
}
