package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import utils.Connexion;

public class Composant {
    private int idComposant;
    private String nom;
    private TypeComposant typeComposant; // Association avec la classe TypeComposant
    private double prix;

    // Constructeur vide
    public Composant() {
    }

    // Constructeur avec id
    public Composant(int idComposant, String nom, TypeComposant typeComposant, double prix) {
        this.idComposant = idComposant;
        this.nom = nom;
        this.typeComposant = typeComposant;
        this.prix = prix;
    }

    // Constructeur sans id
    public Composant(String nom, TypeComposant typeComposant,double prix) {
        this.nom = nom;
        this.typeComposant = typeComposant;
        this.prix = prix;
    }

    // Getters et setters
    public int getIdComposant() {
        return idComposant;
    }

    public void setIdComposant(int idComposant) {
        this.idComposant = idComposant;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public TypeComposant getTypeComposant() {
        return typeComposant;
    }

    public void setTypeComposant(TypeComposant typeComposant) {
        this.typeComposant = typeComposant;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    // Fonction save (Insertion dans la base)
    public void save(Connection connexion) throws SQLException, ClassNotFoundException {
        if (connexion == null) {
            connexion = Connexion.getConnexion();
        }

        String sql = "INSERT INTO composant (nom, id_type_composant) VALUES (?, ?) RETURNING id_composant";
        try (PreparedStatement stmt = connexion.prepareStatement(sql)) {
            stmt.setString(1, this.nom);
            stmt.setInt(2, this.typeComposant.getIdTypeComposant());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    this.idComposant = rs.getInt("id_composant");
                    connexion.commit();
                    connexion.close();
                }
            }
        }
    }

    // Fonction getAll (Récupération de tous les composants)
    public static List<Composant> getAll(Connection connexion) throws Exception {
        if (connexion == null) {
            connexion = Connexion.getConnexion();
        }

        List<Composant> composants = new ArrayList<>();
        String sql = "SELECT * FROM composant";
        try (PreparedStatement stmt = connexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                // On récupère le TypeComposant en même temps
                TypeComposant typeComposant = TypeComposant.getById(connexion, rs.getInt("id_type_composant"));
                Composant composant = new Composant(
                        rs.getInt("id_composant"),
                        rs.getString("nom"),
                        typeComposant,
                        rs.getDouble("prix")   
                );
                composants.add(composant);
            }
        }
        return composants;
    }

    // Fonction getById (Récupération d'un composant par son id)
    public static Composant getById(Connection connexion, int idComposant) throws Exception {
        if (connexion == null) {
            connexion = Connexion.getConnexion();
        }

        String sql = "SELECT * FROM composant WHERE id_composant = ?";
        try (PreparedStatement stmt = connexion.prepareStatement(sql)) {
            stmt.setInt(1, idComposant);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    TypeComposant typeComposant = TypeComposant.getById(connexion, rs.getInt("id_type_composant"));
                    return new Composant(
                            rs.getInt("id_composant"),
                            rs.getString("nom"),
                            typeComposant,
                            rs.getDouble("prix")
                    );
                }
            }
        }
        return null; // Si aucun composant n'est trouvé avec l'id donné
    }

    
}
