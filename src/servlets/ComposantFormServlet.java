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

@WebServlet("/composantform")
public class ComposantFormServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection connexion = null;
        try {
            connexion = Connexion.getConnexion();
            // Récupérer tous les TypeComposant pour les afficher dans le select
            List<TypeComposant> typesComposants = TypeComposant.getAll(connexion);
            request.setAttribute("typescomposants", typesComposants);

            request.getRequestDispatcher("composant_form.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Erreur lors de la récupération des types de composants : " + e.getMessage());
            request.getRequestDispatcher("composant_form.jsp").forward(request, response);
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
        String idTypeStr = request.getParameter("typecomposant");
        Connection connexion = null;

        try {
            // Validation des champs
            if (nom == null || nom.trim().isEmpty() || idTypeStr == null || idTypeStr.trim().isEmpty()) {
                // Utiliser SweetAlert pour l'erreur
                String script = "<script>swal('Erreur!', 'Tous les champs sont obligatoires.', {icon: 'error', buttons: {confirm: {className: 'btn btn-danger'}}});</script>";
                request.setAttribute("sweetAlertScript", script);
                doGet(request, response);
                return;
            }

            connexion = Connexion.getConnexion();
            int idTypeComposant = Integer.parseInt(idTypeStr);

            // Récupérer le type composant
            TypeComposant typeComposant = TypeComposant.getById(connexion, idTypeComposant);
            if (typeComposant == null) {
                throw new Exception("Type de composant invalide");
            }

            // Créer et sauvegarder le composant
            Composant composant = new Composant(nom, typeComposant);
            composant.save(connexion);

            // SweetAlert pour le succès
            String script = "<script>swal('Succès!', 'Composant ajouté avec succès!', {icon: 'success', buttons: {confirm: {className: 'btn btn-success'}}});</script>";
            request.setAttribute("sweetAlertScript", script);
            
            doGet(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            // SweetAlert pour l'erreur
            String script = "<script>swal('Erreur!', 'Erreur lors de l'ajout du composant : " + e.getMessage() + "', {icon: 'error', buttons: {confirm: {className: 'btn btn-danger'}}});</script>";
            request.setAttribute("sweetAlertScript", script);
            doGet(request, response);
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
