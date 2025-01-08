
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Liste des Composants</title>
</head>
<body>
    <h1>Liste des </h1>
    <table border="1">
        <tr>
            <th>ID Composant</th>
            <th>Nom</th>
            <th>Prix</th>
            <th>ID Type Composant</th>
        </tr>
        <c:forEach var="composant" items="${composants}">
            <tr>
                <td>${composant.id_composant}</td>
                <td>${composant.nom}</td>
                <td>${composant.prix}</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
