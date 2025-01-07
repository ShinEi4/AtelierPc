package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import utils.Connexion;

public class Marque {
    private int idMarque;
    private String nomMarque;

    // Constructeur vide
    public Marque() {
    }

    // Constructeur avec id
    public Marque(int idMarque, String nomMarque) {
        this.idMarque = idMarque;
        this.nomMarque = nomMarque;
    }

    // Constructeur sans id
    public Marque(String nomMarque) {
        this.nomMarque = nomMarque;
    }

    // Getters et setters
    public int getIdMarque() {
        return idMarque;
    }

    public void setIdMarque(int idMarque) {
        this.idMarque = idMarque;
    }

    public String getNomMarque() {
        return nomMarque;
    }

    public void setNomMarque(String nomMarque) {
        this.nomMarque = nomMarque;
    }

    // Fonction save (Insertion dans la base)
    public void save(Connection connexion) throws Exception {
        if (connexion == null) {
            connexion=Connexion.getConnexion();
        }
        String sql;
            sql = "INSERT INTO marque (nom_marque) VALUES (?) RETURNING id_marque";
            try (PreparedStatement stmt = connexion.prepareStatement(sql)) {
                stmt.setString(1, this.nomMarque);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        this.idMarque = rs.getInt("id_marque");
                        connexion.commit();
                        connexion.close();
                    }
                }
            }
    }

    // Fonction getAll (Récupération de toutes les marques)
    public static List<Marque> getAll(Connection connexion) throws Exception {
        List<Marque> marques = new ArrayList<>();
        if (connexion == null) {
            connexion=Connexion.getConnexion();
        }
        String sql = "SELECT * FROM marque";
        try (PreparedStatement stmt = connexion.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Marque marque = new Marque(
                        rs.getInt("id_marque"),
                        rs.getString("nom_marque")
                );
                marques.add(marque);
            }
        }
        return marques;
    }

    public static List<Marque> getById(Connection connexion) throws Exception {
        List<Marque> marques = new ArrayList<>();
        if (connexion == null) {
            connexion=Connexion.getConnexion();
        }
        String sql = "SELECT * FROM marque";
        try (PreparedStatement stmt = connexion.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Marque marque = new Marque(
                        rs.getInt("id_marque"),
                        rs.getString("nom_marque")
                );
                marques.add(marque);
            }
        }
        return marques;
    }

    // Fonction getById (Récupération d'une marque par son id)
    public static Marque getById(Connection connexion, int idMarque) throws Exception {
        if (connexion == null) {
            connexion=Connexion.getConnexion();
        }
        String sql = "SELECT * FROM marque WHERE id_marque = ?";
        try (PreparedStatement stmt = connexion.prepareStatement(sql)) {
            stmt.setInt(1, idMarque);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Marque(
                            rs.getInt("id_marque"),
                            rs.getString("nom_marque")
                    );
                }
            }
        }
        return null; 
    }
}
