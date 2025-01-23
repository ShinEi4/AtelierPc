package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import utils.Connexion;

public class Commission {
    private int idCommission;
    private double pourcentage;
    private Timestamp dateModification;

    // Constructeurs
    public Commission() {}
    
    public Commission(double pourcentage) {
        this.pourcentage = pourcentage;
    }

    // Getters et setters
    // ...

    public void save(Connection connexion) throws Exception {
        if (connexion == null) {
            connexion = Connexion.getConnexion();
        }

        String sql = "INSERT INTO commission (pourcentage) VALUES (?) " +
                    "ON CONFLICT (id_commission) DO UPDATE SET " +
                    "pourcentage = EXCLUDED.pourcentage, " +
                    "date_modification = CURRENT_TIMESTAMP " +
                    "RETURNING id_commission";
                    
        try (PreparedStatement stmt = connexion.prepareStatement(sql)) {
            stmt.setDouble(1, this.pourcentage);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    this.idCommission = rs.getInt("id_commission");
                }
            }
            connexion.commit();
        }
    }

    public static double getCurrentPourcentage(Connection connexion) throws Exception {
        if (connexion == null) {
            connexion = Connexion.getConnexion();
        }

        String sql = "SELECT pourcentage FROM commission ORDER BY date_modification DESC LIMIT 1";
        try (PreparedStatement stmt = connexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getDouble("pourcentage");
            }
        }
        return 0.0;
    }
} 