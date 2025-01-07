package models;

import utils.Connexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Modele {
    private int idModele;
    private String nom;
    private Marque marque;
    private List<Composant> composants = new ArrayList<>(); // Liste des composants associés au modèle

    // Constructeur vide
    public Modele() {
    }

    // Constructeur avec id
    public Modele(int idModele, String nom, Marque marque) {
        this.idModele = idModele;
        this.nom = nom;
        this.marque = marque;
    }

    // Constructeur sans id
    public Modele(String nom, Marque marque) {
        this.nom = nom;
        this.marque = marque;
    }

    // Getters et setters
    public int getIdModele() {
        return idModele;
    }

    public void setIdModele(int idModele) {
        this.idModele = idModele;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Marque getMarque() {
        return marque;
    }

    public void setMarque(Marque marque) {
        this.marque = marque;
    }

    public List<Composant> getComposants() {
        return composants;
    }

    public void setComposants(List<Composant> composants) {
        this.composants = composants;
    }

    // Fonction save (Insertion dans la base)
    public void save(Connection connexion) throws SQLException, ClassNotFoundException {
        if (connexion == null) {
            connexion = Connexion.getConnexion();
        }

        String sql = "INSERT INTO modele (nom, id_marque) VALUES (?, ?) RETURNING id_modele";
        try (PreparedStatement stmt = connexion.prepareStatement(sql)) {
            stmt.setString(1, this.nom);
            stmt.setInt(2, this.marque.getIdMarque());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    this.idModele = rs.getInt("id_modele");
                    connexion.commit();
                }
            }
        }

        // Insertion des composants associés à ce modèle dans la table Modele_composant
        if (this.composants != null && !this.composants.isEmpty()) {
            for (Composant composant : composants) {
                String insertComposantSql = "INSERT INTO modele_composant (id_modele, id_composant) VALUES (?, ?)";
                try (PreparedStatement stmtComposant = connexion.prepareStatement(insertComposantSql)) {
                    stmtComposant.setInt(1, this.idModele);
                    stmtComposant.setInt(2, composant.getIdComposant());
                    stmtComposant.executeUpdate();
                }
                connexion.commit();
            }
        }
        connexion.close();
    }

    public void ajouterComposant(Composant composant, Connection connexion) throws SQLException, ClassNotFoundException {
        if (connexion == null) {
            connexion = Connexion.getConnexion();
        }

        // Insertion du composant dans la table Modele_composant
        String sql = "INSERT INTO modele_composant (id_modele, id_composant) VALUES (?, ?)";
        try (PreparedStatement stmt = connexion.prepareStatement(sql)) {
            stmt.setInt(1, this.idModele);
            stmt.setInt(2, composant.getIdComposant());
            stmt.executeUpdate();
        }

        // Ajout du composant à la liste interne pour une gestion locale
        if (!this.composants.contains(composant)) {
            this.composants.add(composant);
            connexion.commit();
            connexion.close();
        }
    }

    // Fonction getAll (Récupération de tous les modèles)
    public static List<Modele> getAll(Connection connexion) throws Exception {
        if (connexion == null) {
            connexion = Connexion.getConnexion();
        }

        List<Modele> modeles = new ArrayList<>();
        String sql = "SELECT * FROM modele";
        try (PreparedStatement stmt = connexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Marque marque = Marque.getById(connexion, rs.getInt("id_marque"));
                Modele modele = new Modele(
                        rs.getInt("id_modele"),
                        rs.getString("nom"),
                        marque
                );

                // Récupération des composants associés à ce modèle
                List<Composant> composants = getComposantsByModeleId(connexion, modele.getIdModele());
                modele.setComposants(composants);

                modeles.add(modele);
            }
        }
        return modeles;
    }
    public static Modele getById(Connection connexion,int id) throws Exception {
        if (connexion == null) {
            connexion = Connexion.getConnexion();
        }

        String sql = "SELECT * FROM modele where id_modele = ?";
        try (PreparedStatement stmt = connexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Marque marque = Marque.getById(connexion, rs.getInt("id_marque"));
                Modele modele = new Modele(
                        rs.getInt("id_modele"),
                        rs.getString("nom"),
                        marque
                );

                // Récupération des composants associés à ce modèle
                List<Composant> composants = getComposantsByModeleId(connexion, modele.getIdModele());
                modele.setComposants(composants);

               return modele;
            }
        }
        return null;
    }

    // Fonction pour récupérer les composants associés à un modèle
    public static List<Composant> getComposantsByModeleId(Connection connexion, int idModele) throws Exception {
        List<Composant> composants = new ArrayList<>();
        String sql = "SELECT * FROM modele_composant WHERE id_modele = ?";
        try (PreparedStatement stmt = connexion.prepareStatement(sql)) {
            stmt.setInt(1, idModele);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Composant composant = Composant.getById(connexion, rs.getInt("id_composant"));
                    composants.add(composant);
                }
            }
        }
        return composants;
    }
}
