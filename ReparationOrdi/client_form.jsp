<!-- insert_client.jsp -->
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Ajouter un Client</title>
</head>
<body>
    <h1>Ajouter un Client</h1>
    <form action="clients" method="post">
        <label for="nom">Nom:</label>
        <input type="text" id="nom" name="nom" required><br>

        <label for="num">Numéro:</label>
        <input type="text" id="num" name="num" required><br>

        <button type="submit">Ajouter</button>
    </form>
</body>
</html>
