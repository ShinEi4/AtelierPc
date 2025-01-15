package servlets;

import models.Composant;
import utils.Connexion;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/composants")
public class ComposantServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (Connection connexion = Connexion.getConnexion()) {
            String action = request.getParameter("action");
            
            if ("recherche_recommandes".equals(action)) {
                String moisRecherche = request.getParameter("moisRecherche");
                List<Integer> composantsRecommandes = getComposantsRecommandesByMois(connexion, moisRecherche);
                request.setAttribute("composantsRecommandes", composantsRecommandes);
            }
            
            List<Composant> composants = Composant.getAll(connexion);
            request.setAttribute("composants", composants);
            
            request.getRequestDispatcher("composant_list.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Erreur lors de la récupération des composants.");
            request.getRequestDispatcher("composant_list.jsp").forward(request, response);
        }
    }

    private List<Integer> getComposantsRecommandesByMois(Connection connexion, String moisRecherche) throws SQLException {
        List<Integer> composantsRecommandes = new ArrayList<>();
        String sql = "SELECT id_composant FROM composant_recommande WHERE TO_CHAR(date, 'YYYY-MM') = ?";
        
        try (PreparedStatement stmt = connexion.prepareStatement(sql)) {
            stmt.setString(1, moisRecherche);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    composantsRecommandes.add(rs.getInt("id_composant"));
                }
            }
        }
        return composantsRecommandes;
    }
}
