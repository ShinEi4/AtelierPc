<!-- display_clients.jsp -->
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Liste des Reparations Composants</title>
</head>
<body>
    <h1>Liste des Reparations Composants</h1>
    <table border="1">
        <tr>
            <th>ID Composant </th>
            <th>ID Reparation </th>
        </tr>
        <c:forEach var="reparation_composant" items="${reparation_composants}">
            <tr>
                <td>${reparation_composant.id_composant}</td>
                <td>${reparation_composant.id_reparation}</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
