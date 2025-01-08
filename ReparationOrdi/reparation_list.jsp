<!-- display_clients.jsp -->
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Liste des Reparations</title>
</head>
<body>
    <h1>Liste des Types Reparations</h1>
    <table border="1">
        <tr>
            <th>ID Reparation</th>
            <th>Date Debut </th>
            <th>Date Fin </th>
            <th>Description </th>
            <th>Prix main d'oeuvre </th>
            <th>ID Technicien </th>
            <th>ID Ordinateur </th>
        </tr>
        <c:forEach var="reparation" items="${reparations}">
            <tr>
                <td>${reparation.id_reparation}</td>
                <td>${reparation.date_debut}</td>
                <td>${reparation.date_fin}</td>
                <td>${reparation.descri}</td>
                <td>${reparation.prix_main_doeuvre}</td>
                <td>${reparation.id_technicien}</td>
                <td>${reparation.id_ordinateur}</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
