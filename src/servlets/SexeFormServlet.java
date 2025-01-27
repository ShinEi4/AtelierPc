package servlets;

import models.Sexe;
import utils.Connexion;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

import java.io.IOException;
import java.sql.Connection;

@WebServlet("/sexeform")
public class SexeFormServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("sexe_form.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (Connection connexion = Connexion.getConnexion()) {
            String nom = request.getParameter("nom");
            
            Sexe sexe = new Sexe(nom);
            sexe.save(connexion);
            
            response.sendRedirect("sexes");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Erreur lors de l'enregistrement : " + e.getMessage());
            request.getRequestDispatcher("sexe_form.jsp").forward(request, response);
        }
    }
} 