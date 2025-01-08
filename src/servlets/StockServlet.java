package servlets;

import models.Stock;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

import java.io.IOException;
import java.util.List;

@WebServlet("/stocks")
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
