package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import utils.Connexion;

public class CategorieModele {
    private int idCategorieModele;
    private String nomType;

    // Constructeurs
    public CategorieModele() {
    }

    public CategorieModele(int idCategorieModele, String nomType) {
        this.idCategorieModele = idCategorieModele;
        this.nomType = nomType;
    }

    public CategorieModele(String nomType) {
        this.nomType = nomType;
    }

    // Getters et setters
    public int getIdCategorieModele() {
        return idCategorieModele;
    }

    public void setIdCategorieModele(int idCategorieModele) {
        this.idCategorieModele = idCategorieModele;
    }

    public String getNomType() {
        return nomType;
    }

    public void setNomType(String nomType) {
        this.nomType = nomType;
    }

    // Méthode pour insérer une catégorie dans la base
    public void save(Connection connexion) throws SQLException, ClassNotFoundException {
        if (connexion == null) {
            connexion = Connexion.getConnexion();
        }
        String sql = "INSERT INTO categorie_modele (nom_type) VALUES (?) RETURNING id_categorie_modele";
        try (PreparedStatement stmt = connexion.prepareStatement(sql)) {
            stmt.setString(1, this.nomType);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    this.idCategorieModele = rs.getInt("id_categorie_modele");
                }
            }
        }
        connexion.commit();
        connexion.close();
    }

    // Méthode pour récupérer toutes les catégories
    public static List<CategorieModele> getAll(Connection connexion) throws SQLException, ClassNotFoundException {
        if (connexion == null) {
            connexion = Connexion.getConnexion();
        }

        List<CategorieModele> categories = new ArrayList<>();
        String sql = "SELECT * FROM categorie_modele";
        try (PreparedStatement stmt = connexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                categories.add(new CategorieModele(
                        rs.getInt("id_categorie_modele"),
                        rs.getString("nom_type")
                ));
            }
        }
        connexion.close();
        return categories;
    }

    // Méthode pour récupérer une catégorie par ID
    public static CategorieModele getById(Connection connexion, int id) throws SQLException, ClassNotFoundException {
        if (connexion == null) {
            connexion = Connexion.getConnexion();
        }

        String sql = "SELECT * FROM categorie_modele WHERE id_categorie_modele = ?";
        try (PreparedStatement stmt = connexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new CategorieModele(
                            rs.getInt("id_categorie_modele"),
                            rs.getString("nom_type")
                    );
                }
            }
        }
        connexion.close();
        return null;
    }
}
