package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import utils.Connexion;

public class Commission {
    private int idCommission;
    private double pourcentage;
    private double prixMin;
    private Timestamp dateModification;

    // Constructeurs
    public Commission() {}
    
    public Commission(double pourcentage, double prixMin) {
        this.pourcentage = pourcentage;
        this.prixMin = prixMin;
    }

    // Getters et setters
    public int getIdCommission() { return idCommission; }
    public void setIdCommission(int idCommission) { this.idCommission = idCommission; }
    public double getPourcentage() { return pourcentage; }
    public void setPourcentage(double pourcentage) { this.pourcentage = pourcentage; }
    public double getPrixMin() { return prixMin; }
    public void setPrixMin(double prixMin) { this.prixMin = prixMin; }
    public Timestamp getDateModification() { return dateModification; }
    public void setDateModification(Timestamp dateModification) { this.dateModification = dateModification; }

    public void save(Connection connexion) throws Exception {
        if (connexion == null) {
            connexion = Connexion.getConnexion();
        }

        String sql = "INSERT INTO commission (pourcentage, prix_min) VALUES (?, ?) " +
                    "RETURNING id_commission";
                    
        try (PreparedStatement stmt = connexion.prepareStatement(sql)) {
            stmt.setDouble(1, this.pourcentage);
            stmt.setDouble(2, this.prixMin);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    this.idCommission = rs.getInt("id_commission");
                }
            }
            connexion.commit();
        }
    }

    public static Commission getCurrent(Connection connexion) throws Exception {
        if (connexion == null) {
            connexion = Connexion.getConnexion();
        }

        String sql = "SELECT * FROM commission ORDER BY date_modification DESC LIMIT 1";
        try (PreparedStatement stmt = connexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                Commission commission = new Commission();
                commission.setIdCommission(rs.getInt("id_commission"));
                commission.setPourcentage(rs.getDouble("pourcentage"));
                commission.setPrixMin(rs.getDouble("prix_min"));
                commission.setDateModification(rs.getTimestamp("date_modification"));
                return commission;
            }
        }
        return null;
    }
} 