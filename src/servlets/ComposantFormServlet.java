package servlets;

import models.Composant;
import models.TypeComposant;
import utils.Connexion;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/ComposantFormServlet")
public class ComposantFormServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection connexion = null;
        try {
            connexion = Connexion.getConnexion();
            // Récupérer tous les TypeComposant pour les afficher dans le formulaire
            List<TypeComposant> typeComposants = TypeComposant.getAll(connexion);
            request.setAttribute("typeComposants", typeComposants);

            // Rediriger vers la page JSP du formulaire
            request.getRequestDispatcher("/formComposant.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Erreur lors de la récupération des types de composants : " + e.getMessage());
            request.getRequestDispatcher("/formComposant.jsp").forward(request, response);
        } finally {
            if (connexion != null) {
                try {
                    connexion.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nom = request.getParameter("nom");
        String prixStr = request.getParameter("prix");
        String idTypeStr = request.getParameter("idTypeComposant");

        String message;
        Connection connexion = null;

        try {
            // Validation des champs
            if (nom == null || nom.isEmpty() || prixStr == null || prixStr.isEmpty() || idTypeStr == null || idTypeStr.isEmpty()) {
                message = "Tous les champs sont obligatoires.";
                request.setAttribute("errorMessage", message);
                request.getRequestDispatcher("/formComposant.jsp").forward(request, response);
                return;
            }

            double prix = Double.parseDouble(prixStr);
            int idTypeComposant = Integer.parseInt(idTypeStr);

            connexion = Connexion.getConnexion();
            TypeComposant typeComposant = TypeComposant.getById(connexion, idTypeComposant);

            if (typeComposant == null) {
                throw new Exception("Le type de composant sélectionné n'existe pas.");
            }

            Composant composant = new Composant(nom, typeComposant, prix);
            composant.save(connexion);

            message = "Composant ajouté avec succès !";
            request.setAttribute("successMessage", message);
            request.getRequestDispatcher("/formComposant.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            message = "Erreur lors de l'ajout du composant : " + e.getMessage();
            request.setAttribute("errorMessage", message);
            request.getRequestDispatcher("/formComposant.jsp").forward(request, response);
        } finally {
            if (connexion != null) {
                try {
                    connexion.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
