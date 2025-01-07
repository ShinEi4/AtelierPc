<!-- insert_client.jsp -->
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Ajouter un Stock</title>
</head>
<body>
    <h1>Ajouter un Stock</h1>
    <form action="stocks" method="post">
        <label for="entre">Entre:</label>
        <input type="number" id="entre" name="entre" required><br>

        <label for="sortie">Sortie:</label>
        <input type="number" id="sortie" name="sortie" required><br>


        <label for="date_mvt">Date de mouvement:</label>
        <input type="time" id="date_mvt" name="date_mvt" required><br>


        <button type="submit">Ajouter</button>
    </form>
</body>
</html>
