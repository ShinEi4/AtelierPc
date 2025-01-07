<!-- display_clients.jsp -->
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Liste des Modeles Composants</title>
</head>
<body>
    <h1>Liste des modeles composants</h1>
    <table border="1">
        <tr>
            <th>ID Modele</th>
            <th>ID Reparation</th>
        </tr>
        <c:forEach var="modele_composant" items="${modele_composants}">
            <tr>
                <td>${modele_composant.id_modele}</td>
                <td>${modele_composant.composant}</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
