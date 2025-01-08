package servlets;

import models.Technicien;
import utils.Connexion;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/technicienform")
public class TechnicienFormServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection connexion = null;
        try {
            connexion = Connexion.getConnexion();

            // Récupérer les données soumises
            String nom = request.getParameter("nom");

            // Validation des données
            if (nom == null || nom.trim().isEmpty()) {
                throw new IllegalArgumentException("Le nom du technicien est obligatoire.");
            }

            // Créer un nouvel objet Technicien
            Technicien technicien = new Technicien(nom);
            technicien.save(connexion);

            // Redirection après succès
            response.sendRedirect(request.getContextPath() + "/technicienForm");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Erreur lors de l'ajout du technicien : " + e.getMessage());
            request.getRequestDispatcher("/formTechnicien.jsp").forward(request, response);
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
