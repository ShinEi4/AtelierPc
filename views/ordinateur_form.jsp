<!-- insert_client.jsp -->
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Ajouter un Ordinateur</title>
</head>
<body>
    <h1>Ajouter un Ordinateur</h1>
    <form action="marques" method="post">
        <label for="nom">Nom:</label>
        <input type="text" id="nom" name="nom" required><br>

        <button type="submit">Ajouter</button>
    </form>
</body>
</html>
