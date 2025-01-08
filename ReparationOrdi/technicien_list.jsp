<!-- display_clients.jsp -->
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Liste des Techniciens</title>
</head>
<body>
    <h1>Liste des Types Techniciens</h1>
    <table border="1">
        <tr>
            <th>ID Technicien</th>
            <th>Nom </th>
        </tr>
        <c:forEach var="technicien" items="${techniciens}">
            <tr>
                <td>${technicien.id_technicien}</td>
                <td>${technicien.nom}</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
