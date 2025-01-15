package servlets;

import models.Technicien;
import utils.Connexion;


import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/technicienform")
public class TechnicienFormServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/technicien_form.jsp").forward(request, response);
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

            request.getRequestDispatcher("/technicien_form.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Erreur lors de l'ajout du technicien : " + e.getMessage());
            request.getRequestDispatcher("/technicien_form.jsp").forward(request, response);
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
