<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Liste des Ordinateurs avec Problèmes d'Écran</title>
</head>
<body>
    <h1>Ordinateurs avec Problèmes d'Écran</h1> 
    <table border="1">
        <thead>
            <tr>
                <th>ID Ordinateur</th>
                <th>Numéro de Série</th>
                <th>Nom du Client</th>
                <th>Modèle</th>
                <th>Description du Problème</th>
            </tr>
        </thead>
        <tbody>
            <%
                // Récupérer la liste des ordinateurs avec problèmes d'écrans depuis le servlet
                List<OrdinateurProblemeDTO> ordinateursProblemes = (List<OrdinateurProblemeDTO>) request.getAttribute("ordinateursProblemes");

                if (ordinateursProblemes != null && !ordinateursProblemes.isEmpty()) {
                    for (OrdinateurProblemeDTO ordinateur : ordinateursProblemes) {
            %>
                        <tr>
                            <td><%= ordinateur.getIdOrdinateur() %></td>
                            <td><%= ordinateur.getIdSerie() %></td>
                            <td><%= ordinateur.getClientNom() %></td>
                            <td><%= ordinateur.getModeleNom() %></td>
                            <td><%= ordinateur.getDescriptionProbleme() %></td>
                        </tr>
            <%
                    }
                } else {
            %>
                    <tr>
                        <td colspan="5">Aucun ordinateur avec des problèmes d'écran trouvé.</td>
                    </tr>
            <%
                }
            %>
        </tbody>
    </table>
</body>
</html>
