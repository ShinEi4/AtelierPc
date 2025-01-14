package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import utils.Connexion;

public class TypeComposant {
    private int idTypeComposant;
    private String nomType;

    // Constructeur vide
    public TypeComposant() {
    }

    // Constructeur avec id
    public TypeComposant(int idTypeComposant, String nomType) {
        this.idTypeComposant = idTypeComposant;
        this.nomType = nomType;
    }

    // Constructeur sans id
    public TypeComposant(String nomType) {
        this.nomType = nomType;
    }

    // Getters et setters
    public int getIdTypeComposant() {
        return idTypeComposant;
    }

    public void setIdTypeComposant(int idTypeComposant) {
        this.idTypeComposant = idTypeComposant;
    }

    public String getNomType() {
        return nomType;
    }

    public void setNomType(String nomType) {
        this.nomType = nomType;
    }

    // Fonction save (Insertion dans la base)
    public void save(Connection connexion) throws Exception {
        if (connexion == null) {
            connexion=Connexion.getConnexion();
        }
        String sql = "INSERT INTO type_composant (nom_type) VALUES (?) RETURNING id_type_composant";
        try (PreparedStatement stmt = connexion.prepareStatement(sql)) {
            stmt.setString(1, this.nomType);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    this.idTypeComposant = rs.getInt("id_type_composant");
                    connexion.commit();
                    connexion.close();
                }
            }
        }
    }

    // Fonction getAll (Récupération de tous les types de composants)
    public static List<TypeComposant> getAll(Connection connexion) throws Exception {
        if (connexion == null) {
            connexion=Connexion.getConnexion();
        }
        List<TypeComposant> types = new ArrayList<>();
        String sql = "SELECT * FROM type_composant";
        try (PreparedStatement stmt = connexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                TypeComposant type = new TypeComposant(
                        rs.getInt("id_type_composant"),
                        rs.getString("nom_type")
                );
                types.add(type);
            }
        }
        return types;
    }

    // Fonction getById (Récupération d'un type de composant par son id)
    public static TypeComposant getById(Connection connexion, int idTypeComposant) throws Exception {
        if (connexion == null) {
            connexion=Connexion.getConnexion();
        }
        String sql = "SELECT * FROM type_composant WHERE id_type_composant = ?";
        try (PreparedStatement stmt = connexion.prepareStatement(sql)) {
            stmt.setInt(1, idTypeComposant);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new TypeComposant(
                            rs.getInt("id_type_composant"),
                            rs.getString("nom_type")
                    );
                }
            }
        }
        return null; // Si aucun type de composant n'est trouvé avec l'id donné
    }
}
