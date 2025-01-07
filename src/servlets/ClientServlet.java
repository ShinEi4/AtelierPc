package servlets;



import models.Client;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class ClientServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Récupérer tous les clients
            List<Client> clients = Client.getAll(null); // Connexion à fournir
            request.setAttribute("clients", clients);  // Passer les clients à la vue JSP
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Erreur lors de la récupération des clients.");
        }

        // Rediriger vers la vue de la liste des clients
        request.getRequestDispatcher("/WEB-INF/client_list.jsp").forward(request, response);
    }
}
