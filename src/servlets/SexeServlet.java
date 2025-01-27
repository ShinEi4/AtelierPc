package servlets;

import models.Sexe;
import utils.Connexion;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/sexes")
public class SexeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (Connection connexion = Connexion.getConnexion()) {
            List<Sexe> sexes = Sexe.getAll(connexion);
            request.setAttribute("sexes", sexes);
            request.getRequestDispatcher("sexe_list.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Erreur lors de la récupération des sexes : " + e.getMessage());
            request.getRequestDispatcher("sexe_list.jsp").forward(request, response);
        }
    }
} 