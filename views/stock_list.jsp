<!-- display_clients.jsp -->
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Liste des stocks</title>
</head>
<body>
    <h1>Liste des Stocks</h1>
    <table border="1">
        <tr>
            <th>ID Stock</th>
            <th>Entree </th>
            <th>Sortie </th>
            <th>Date de Mouvement </th>
            <th>ID Composant </th>
        </tr>
        <c:forEach var="stock" items="${stocks}">
            <tr>
                <td>${stock.id_stock}</td>
                <td>${stock.entree}</td>
                <td>${stock.sortie}</td>
                <td>${stock.date_mvt}</td>
                <td>${stock.id_composant}</td>>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
