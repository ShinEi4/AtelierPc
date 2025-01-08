<!-- display_clients.jsp -->
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Liste des modeles</title>
</head>
<body>
    <h1>Liste des modeles</h1>
    <table border="1">
        <tr>
            <th>ID</th>
            <th>Nom</th>
            <th>ID Marque</th>
        </tr>
        <c:forEach var="marque" items="${marques}">
            <tr>
                <td>${marque.id_client}</td>
                <td>${marque.nom}</td>
                <td>${marque.id_marque}</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
