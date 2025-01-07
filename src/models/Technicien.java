package models;

import utils.Connexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Technicien {
    private int idTechnicien;
    private String nom;

    // Constructeur vide
    public Technicien() {
    }

    // Constructeur avec id
    public Technicien(int idTechnicien, String nom) {
        this.idTechnicien = idTechnicien;
        this.nom = nom;
    }

    // Constructeur sans id
    public Technicien(String nom) {
        this.nom = nom;
    }

    // Getters et setters
    public int getIdTechnicien() {
        return idTechnicien;
    }

    public void setIdTechnicien(int idTechnicien) {
        this.idTechnicien = idTechnicien;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    // Fonction save (Insertion dans la base)
    public void save(Connection connexion) throws SQLException, ClassNotFoundException {
        if (connexion == null) {
            connexion = Connexion.getConnexion();
        }

        String sql = "INSERT INTO technicien (nom) VALUES (?) RETURNING id_technicien";
        try (PreparedStatement stmt = connexion.prepareStatement(sql)) {
            stmt.setString(1, this.nom);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    this.idTechnicien = rs.getInt("id_technicien");
                    connexion.commit();
                    connexion.close();
                }
            }
        }
    }

    // Fonction getAll (Récupération de tous les techniciens)
    public static List<Technicien> getAll(Connection connexion) throws SQLException, ClassNotFoundException {
        if (connexion == null) {
            connexion = Connexion.getConnexion();
        }

        List<Technicien> techniciens = new ArrayList<>();
        String sql = "SELECT * FROM technicien";
        try (PreparedStatement stmt = connexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Technicien technicien = new Technicien(
                        rs.getInt("id_technicien"),
                        rs.getString("nom")
                );
                techniciens.add(technicien);
            }
        }
        return techniciens;
    }

    // Fonction getById (Récupération d'un technicien par son id)
    public static Technicien getById(Connection connexion, int idTechnicien) throws SQLException, ClassNotFoundException {
        if (connexion == null) {
            connexion = Connexion.getConnexion();
        }

        String sql = "SELECT * FROM technicien WHERE id_technicien = ?";
        try (PreparedStatement stmt = connexion.prepareStatement(sql)) {
            stmt.setInt(1, idTechnicien);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Technicien(
                            rs.getInt("id_technicien"),
                            rs.getString("nom")
                    );
                }
            }
        }
        return null; // Si aucun technicien n'est trouvé avec l'id donné
    }
}
