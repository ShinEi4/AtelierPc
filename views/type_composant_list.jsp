<!-- display_clients.jsp -->
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Liste des Types Composants</title>
</head>
<body>
    <h1>Liste des Types Composants</h1>
    <table border="1">
        <tr>
            <th>ID Type Composant</th>
            <th>Nom Type</th>
        </tr>
        <c:forEach var="type_composant" items="${type_composants}">
            <tr>
                <td>${type_composant.id_type_composant}</td>
                <td>${type_composant.nom_type}</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
