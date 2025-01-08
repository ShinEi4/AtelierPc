<!-- display_clients.jsp -->
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Liste des marques</title>
</head>
<body>
    <h1>Liste des Marques</h1>
    <table border="1">
        <tr>
            <th>ID</th>
            <th>Nom</th>
        </tr>
        <c:forEach var="marque" items="${marques}">
            <tr>
                <td>${marque.id_client}</td>
                <td>${marque.nom}</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
