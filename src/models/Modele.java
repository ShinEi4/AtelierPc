package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import utils.Connexion;

public class Modele {
    private int idModele;
    private String nom;
    private Marque marque;
    private CategorieModele categorieModele;
    private List<Composant> composants = new ArrayList<>(); // Liste des composants associés au modèle

    // Constructeur vide
    public Modele() {
    }

    public Modele(int idModele, String nom, Marque marque, CategorieModele categorieModele) {
        this.idModele = idModele;
        this.nom = nom;
        this.marque = marque;
        this.categorieModele = categorieModele;
    }
    
    public Modele(String nom, Marque marque, CategorieModele categorieModele) {
        this.nom = nom;
        this.marque = marque;
        this.categorieModele = categorieModele;
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

    public CategorieModele getCategorieModele() {
        return categorieModele;
    }

    public void setCategorieModele(CategorieModele categorieModele) {
        this.categorieModele = categorieModele;
    }

   // Sauvegarde du modèle dans la base
    public void save(Connection connexion) throws SQLException, ClassNotFoundException {
        if (connexion == null) {
            connexion = Connexion.getConnexion();
        }

        String sql = "INSERT INTO modele (nom, id_marque, id_categorie_modele) VALUES (?, ?, ?) RETURNING id_modele";
        try (PreparedStatement stmt = connexion.prepareStatement(sql)) {
            stmt.setString(1, this.nom);
            stmt.setInt(2, this.marque.getIdMarque());
            stmt.setInt(3, this.categorieModele.getIdCategorieModele());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    this.idModele = rs.getInt("id_modele");
                }
            }

            // Ajout des composants
            if (this.composants != null && !this.composants.isEmpty()) {
                for (Composant composant : composants) {
                    ajouterComposant(composant, connexion);
                }
            }

            connexion.commit();
        }
    }

    // Ajouter un composant au modèle
    public void ajouterComposant(Composant composant, Connection connexion) throws SQLException, ClassNotFoundException {
        if (connexion == null) {
            connexion = Connexion.getConnexion();
        }

        String sql = "INSERT INTO modele_composant (id_modele, id_composant) VALUES (?, ?)";
        try (PreparedStatement stmt = connexion.prepareStatement(sql)) {
            stmt.setInt(1, this.idModele);
            stmt.setInt(2, composant.getIdComposant());
            stmt.executeUpdate();
        }

        if (!this.composants.contains(composant)) {
            this.composants.add(composant);
        }
        connexion.commit();
    }

    // Récupérer tous les modèles
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
                CategorieModele categorie = CategorieModele.getById(connexion, rs.getInt("id_categorie_modele"));
                Modele modele = new Modele(
                        rs.getInt("id_modele"),
                        rs.getString("nom"),
                        marque,
                        categorie
                );
                modele.setComposants(getComposantsByModeleId(connexion, modele.getIdModele()));
                modeles.add(modele);
            }
        }
        return modeles;
    }

    // Récupérer un modèle par son ID
    public static Modele getById(Connection connexion, int id) throws Exception {
        if (connexion == null) {
            connexion = Connexion.getConnexion();
        }

        String sql = "SELECT * FROM modele WHERE id_modele = ?";
        try (PreparedStatement stmt = connexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Marque marque = Marque.getById(connexion, rs.getInt("id_marque"));
                    CategorieModele categorie = CategorieModele.getById(connexion, rs.getInt("id_categorie_modele"));
                    Modele modele = new Modele(
                            rs.getInt("id_modele"),
                            rs.getString("nom"),
                            marque,
                            categorie
                    );
                    modele.setComposants(getComposantsByModeleId(connexion, id));
                    return modele;
                }
            }
        }
        return null;
    }

    // Récupérer les composants d'un modèle
    public static List<Composant> getComposantsByModeleId(Connection connexion, int idModele) throws Exception {
        if (connexion == null) {
            connexion = Connexion.getConnexion();
        }

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
