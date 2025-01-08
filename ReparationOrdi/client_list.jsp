<!-- display_clients.jsp -->
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Liste des Clients</title>
</head>
<body>
    <h1>Liste des Clients</h1>
    <table border="1">
        <tr>
            <th>ID</th>
            <th>Nom</th>
            <th>Numéro</th>
        </tr>
        <c:forEach var="client" items="${clients}">
            <tr>
                <td>${client.id_client}</td>
                <td>${client.nom}</td>
                <td>${client.num}</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
