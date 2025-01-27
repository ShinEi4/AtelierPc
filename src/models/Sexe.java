package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import utils.Connexion;

public class Sexe {
    private int idSexe;
    private String nom;

    public Sexe() {}

    public Sexe(int idSexe, String nom) {
        this.idSexe = idSexe;
        this.nom = nom;
    }

    public Sexe(String nom) {
        this.nom = nom;
    }

    // Getters et setters
    public int getIdSexe() { return idSexe; }
    public void setIdSexe(int idSexe) { this.idSexe = idSexe; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    // Méthodes CRUD
    public void save(Connection connexion) throws SQLException, ClassNotFoundException {
        if (connexion == null) {
            connexion = Connexion.getConnexion();
        }

        String sql = "INSERT INTO sexe (nom) VALUES (?) RETURNING id_sexe";
        try (PreparedStatement stmt = connexion.prepareStatement(sql)) {
            stmt.setString(1, this.nom);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    this.idSexe = rs.getInt("id_sexe");
                }
            }
        }
    }

    public static List<Sexe> getAll(Connection connexion) throws SQLException, ClassNotFoundException {
        if (connexion == null) {
            connexion = Connexion.getConnexion();
        }

        List<Sexe> sexes = new ArrayList<>();
        String sql = "SELECT * FROM sexe";
        try (PreparedStatement stmt = connexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                sexes.add(new Sexe(
                    rs.getInt("id_sexe"),
                    rs.getString("nom")
                ));
            }
        }
        return sexes;
    }

    public static Sexe getById(Connection connexion, int id) throws SQLException, ClassNotFoundException {
        if (connexion == null) {
            connexion = Connexion.getConnexion();
        }

        String sql = "SELECT * FROM sexe WHERE id_sexe = ?";
        try (PreparedStatement stmt = connexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Sexe(
                        rs.getInt("id_sexe"),
                        rs.getString("nom")
                    );
                }
            }
        }
        return null;
    }
} 