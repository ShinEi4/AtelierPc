package servlets;

import models.Stock;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class StockServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Stock> stocks = Stock.getAll(null); // Connexion à fournir
            request.setAttribute("stocks", stocks);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Erreur lors de la récupération des mouvements de stocks.");
        }
        request.getRequestDispatcher("/WEB-INF/stock_list.jsp").forward(request, response);
    }
}
