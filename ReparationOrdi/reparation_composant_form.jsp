<!-- insert_client.jsp -->
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Ajouter un Reparation Composant</title>
</head>
<body>
    <h1>Ajouter un Reparation Composant</h1>
    <form action="clients" method="post">
        <label for="date_de_debut">Date de Debut :</label>
        <input type="date" id="" name="datedebut" required><br>


        <label for="date_de_debut">Date de Debut :</label>
        <input type="text" id="nom" name="nom" required><br>

        <button type="submit">Ajouter</button>
    </form>
</body>
</html>
