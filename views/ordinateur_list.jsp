<!-- display_clients.jsp -->
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Liste des Ordinateurs</title>
</head>
<body>
    <h1>Liste des Ordinateurs</h1>
    <table border="1">
        <tr>
            <th>ID Ordinateur</th>
            <th>ID Serie</th>
            <th>ID Modele</th>
            <th>ID Client</th>
        </tr>
        <c:forEach var="ordinateur" items="${ordinateurs}">
            <tr>
                <td>${ordinateur.id_ordinateur}</td>
                <td>${ordinateur.id_serie}</td>
                <td>${ordinateur.id_modele}</td>
                <td>${ordinateur.client}</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
