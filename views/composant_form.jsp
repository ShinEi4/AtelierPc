<!-- insert_composant.jsp -->
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Ajouter un Composant</title>
</head>
<body>
    <h1>Ajouter un Composant</h1>
    <form action="composants" method="post">
        <label for="nom">Nom:</label>
        <input type="text" id="nom" name="nom" required><br>

        <label for="num">Prix:</label>
        <input type="text" id="num" name="num" required><br>

        <button type="submit">Ajouter</button>
    </form>
</body>
</html>
