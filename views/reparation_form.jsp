<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Technicien" %>
<%@ page import="model.Ordinateur" %>
<%
    // Récupérer les techniciens et ordinateurs à partir du DAO
    TechnicienDAO technicienDAO = new TechnicienDAO();
    List<Technicien> techniciens = technicienDAO.getAllTechniciens();

    OrdinateurDAO ordinateurDAO = new OrdinateurDAO();
    List<Ordinateur> ordinateurs = ordinateurDAO.getAllOrdinateurs();
%>
<html>
<head>
    <title>Ajouter une Réparation</title>
</head>
<body>
    <h1>Ajouter une Réparation</h1>
    <form action="reparations" method="post">
        <label for="date_debut">Date de début:</label>
        <input type="datetime-local" id="date_debut" name="date_debut" required><br>

        <label for="date_fin">Date de fin:</label>
        <input type="datetime-local" id="date_fin" name="date_fin" required><br>

        <label for="descri">Description:</label>
        <textarea id="descri" name="descri" required></textarea><br>

        <label for="prix_main_doeuvre">Prix main d'œuvre:</label>
        <input type="number" id="prix_main_doeuvre" name="prix_main_doeuvre" step="0.01" required><br>

        <label for="id_technicien">Technicien:</label>
        <select id="id_technicien" name="id_technicien" required>
            <% for (Technicien tech : techniciens) { %>
                <option value="<%= tech.getIdTechnicien() %>"><%= tech.getNom() %></option>
            <% } %>
        </select><br>

        <label for="id_ordinateur">Ordinateur:</label>
        <select id="id_ordinateur" name="id_ordinateur" required>
            <% for (Ordinateur ord : ordinateurs) { %>
                <option value="<%= ord.getIdOrdinateur() %>"><%= ord.getIdSerie() %></option>
            <% } %>
        </select><br>

        <button type="submit">Ajouter</button>
    </form>
</body>
</html>
