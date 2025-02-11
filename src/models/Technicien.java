package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import utils.Connexion;

public class Technicien {
    private int idTechnicien;
    private String nom;
    private double totalCommission;
    private Sexe sexe;

    // Constructeur vide
    public Technicien() {
    }

    // Constructeur avec id
    public Technicien(int idTechnicien, String nom, Sexe sexe) {
        this.idTechnicien = idTechnicien;
        this.nom = nom;
        this.sexe = sexe;
    }

    // Constructeur sans id
    public Technicien(String nom, Sexe sexe) {
        this.nom = nom;
        this.sexe = sexe;
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

    public double getTotalCommission() {
        return totalCommission;
    }

    public void setTotalCommission(double totalCommission) {
        this.totalCommission = totalCommission;
    }

    public Sexe getSexe() {
        return sexe;
    }

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

    // Fonction save (Insertion dans la base)
    public void save(Connection connexion) throws SQLException, ClassNotFoundException {
        if (connexion == null) {
            connexion = Connexion.getConnexion();
        }

        String sql = "INSERT INTO technicien (nom, id_sexe) VALUES (?, ?) RETURNING id_technicien";
        try (PreparedStatement stmt = connexion.prepareStatement(sql)) {
            stmt.setString(1, this.nom);
            stmt.setInt(2, this.sexe.getIdSexe());
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
        String sql = "SELECT * FROM technicien ";
        try (PreparedStatement stmt = connexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Sexe sexe = Sexe.getById(connexion,rs.getInt("id_sexe"));
                Technicien technicien = new Technicien(
                    rs.getInt("id_technicien"),
                    rs.getString("nom"),
                    sexe
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
                            rs.getString("nom"),
                            Sexe.getById(connexion,rs.getInt("id_sexe"))
                    );
                }
            }
        }
        return null; // Si aucun technicien n'est trouvé avec l'id donné
    }

    public static List<Technicien> getAllWithCommission(Connection connexion, 
            LocalDateTime dateDebut, LocalDateTime dateFin) throws Exception {
        if (connexion == null) {
            connexion = Connexion.getConnexion();
        }

        List<Technicien> techniciens = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
            "SELECT t.*, COALESCE(SUM(vc.commission), 0) as total_commission " +
            "FROM technicien t " +
            "LEFT JOIN v_commission vc ON t.id_technicien = vc.id_technicien "
        );

        // Gestion des dates conditionnelles
        if (dateDebut != null || dateFin != null) {
            sql.append("WHERE ");
            if (dateDebut != null && dateFin != null) {
                sql.append("DATE(vc.date_debut) BETWEEN DATE(?) AND DATE(?) ");
            } else if (dateDebut != null) {
                sql.append("DATE(vc.date_debut) >= DATE(?) ");
            } else {
                sql.append("DATE(vc.date_debut) <= DATE(?) ");
            }
        }
        
        sql.append("GROUP BY t.id_technicien, t.nom, t.id_sexe");

        try (PreparedStatement stmt = connexion.prepareStatement(sql.toString())) {
            // Paramétrage des dates
            if (dateDebut != null && dateFin != null) {
                stmt.setTimestamp(1, Timestamp.valueOf(dateDebut));
                stmt.setTimestamp(2, Timestamp.valueOf(dateFin));
            } else if (dateDebut != null) {
                stmt.setTimestamp(1, Timestamp.valueOf(dateDebut));
            } else if (dateFin != null) {
                stmt.setTimestamp(1, Timestamp.valueOf(dateFin));
            }
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Technicien tech = new Technicien(
                        rs.getInt("id_technicien"),
                        rs.getString("nom"),
                        Sexe.getById(connexion,rs.getInt("id_sexe"))
                    );
                    tech.setTotalCommission(rs.getDouble("total_commission"));
                    techniciens.add(tech);
                }
            }
        }
        return techniciens;
    }

    public static List<Technicien> getCommissionsBySexe(Connection connexion, 
            LocalDateTime dateDebut, LocalDateTime dateFin) throws Exception {
        if (connexion == null) {
            connexion = Connexion.getConnexion();
        }

        // D'abord, récupérer tous les sexes
        List<Sexe> allSexes = Sexe.getAll(connexion);
        List<Technicien> techniciens = new ArrayList<>();

        // Pour chaque sexe, calculer la commission totale
        for (Sexe sexe : allSexes) {
            StringBuilder sql = new StringBuilder(
                "SELECT COALESCE(SUM(vc.commission), 0) as total_commission " +
                "FROM v_commission vc " +
                "JOIN technicien t ON vc.id_technicien = t.id_technicien " +
                "WHERE t.id_sexe = ? "
            );

            if (dateDebut != null || dateFin != null) {
                if (dateDebut != null && dateFin != null) {
                    sql.append("AND DATE(vc.date_debut) BETWEEN DATE(?) AND DATE(?) ");
                } else if (dateDebut != null) {
                    sql.append("AND DATE(vc.date_debut) >= DATE(?) ");
                } else {
                    sql.append("AND DATE(vc.date_debut) <= DATE(?) ");
                }
            }

            try (PreparedStatement stmt = connexion.prepareStatement(sql.toString())) {
                stmt.setInt(1, sexe.getIdSexe());
                
                int paramIndex = 2;
                if (dateDebut != null && dateFin != null) {
                    stmt.setTimestamp(paramIndex++, Timestamp.valueOf(dateDebut));
                    stmt.setTimestamp(paramIndex, Timestamp.valueOf(dateFin));
                } else if (dateDebut != null || dateFin != null) {
                    stmt.setTimestamp(paramIndex, Timestamp.valueOf(dateDebut != null ? dateDebut : dateFin));
                }
                
                try (ResultSet rs = stmt.executeQuery()) {
                    Technicien tech = new Technicien();
                    tech.setSexe(sexe);
                    // Si le ResultSet a une ligne, on prend la valeur, sinon 0
                    if (rs.next()) {
                        tech.setTotalCommission(rs.getDouble("total_commission"));
                    } else {
                        tech.setTotalCommission(0.0);
                    }
                    techniciens.add(tech);
                }
            }
        }
        return techniciens;
    }
}
