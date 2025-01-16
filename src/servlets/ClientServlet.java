package servlets;



import models.Client;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

import java.io.IOException;
import java.util.List;
import utils.Connexion;
import java.sql.Connection;
import java.time.LocalDate;

@WebServlet("/clients")
public class ClientServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Connection connexion = Connexion.getConnexion();
            String action = request.getParameter("action");
            List<Client> clients;

            if ("recherche_date".equals(action)) {
                String dateStr = request.getParameter("dateRecherche");
                LocalDate date = dateStr != null && !dateStr.isEmpty() ? 
                    LocalDate.parse(dateStr) : LocalDate.now();
                    
                clients = Client.getClientsByReparationDate(connexion, date);
                request.setAttribute("dateRecherche", date);
            } else {
                clients = Client.getAll(connexion);
            }

            request.setAttribute("clients", clients);
            request.getRequestDispatcher("client_list.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Erreur lors de la récupération des clients : " + e.getMessage());
            request.getRequestDispatcher("client_list.jsp").forward(request, response);
        }
    }
}
