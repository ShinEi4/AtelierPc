package servlets;

import models.Composant;
import models.Stock;
import utils.Connexion;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;

@WebServlet("/stockform")
public class StockFormServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection connexion = null;
        try {
            connexion = Connexion.getConnexion();

            // Récupération des données du formulaire
            int entree = Integer.parseInt(request.getParameter("entree"));
            int sortie = Integer.parseInt(request.getParameter("sortie"));
            Timestamp dateMvt = Timestamp.valueOf(request.getParameter("dateMvt"));
            int idComposant = Integer.parseInt(request.getParameter("idComposant"));

            // Récupérer le composant correspondant
            Composant composant = Composant.getById(connexion, idComposant);
            if (composant == null) {
                throw new IllegalArgumentException("Composant introuvable.");
            }

            // Créer un nouvel objet Stock
            Stock stock = new Stock(entree, sortie, dateMvt, composant);
            stock.save(connexion);

            // Redirection après succès
            response.sendRedirect(request.getContextPath() + "/stockForm");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Erreur lors de l'ajout du mouvement de stock : " + e.getMessage());
            request.getRequestDispatcher("/formStock.jsp").forward(request, response);
        } finally {
            if (connexion != null) {
                try {
                    connexion.close();
                } catch (Exception ignored) {
                }
            }
        }
    }
}
