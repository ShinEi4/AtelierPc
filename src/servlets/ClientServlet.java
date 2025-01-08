package servlets;



import models.Client;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

import java.io.IOException;
import java.util.List;

@WebServlet("/clients")
public class ClientServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Client> clients = Client.getAll(null); // Connexion à fournir
            request.setAttribute("clients", clients);  // Passer les clients à la vue JSP
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Erreur lors de la récupération des clients.");
        }
        request.getRequestDispatcher("client_list.jsp").forward(request, response);
    }
}
