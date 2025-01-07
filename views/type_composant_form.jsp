<!-- insert_client.jsp -->
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Ajouter un Type Composant</title>
</head>
<body>
    <h1>Ajouter un Type Composant</h1>
    <form action="marques" method="post">
        <label for="nom">Nom Type:</label>
        <input type="text" id="nom" name="nom" required><br>

        <button type="submit">Ajouter</button>
    </form>
</body>
</html>
