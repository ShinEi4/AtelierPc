package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import utils.Connexion;

public class Reparation {
    private int idReparation;
    private Timestamp dateDebut;
    private Timestamp dateFin;
    private String descri;
    private Technicien technicien;
    private Ordinateur ordinateur;
    private double prixMainDoeuvre;
    private List<Composant> composants = new ArrayList<>(); // Liste des composants associés à la réparation

    // Constructeur vide
    public Reparation() {
    }

    // Constructeur avec id
    public Reparation(int idReparation, Timestamp dateDebut, Timestamp dateFin, String descri, Technicien technicien, Ordinateur ordinateur, double prixMainDoeuvre) {
        this.idReparation = idReparation;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.descri = descri;
        this.technicien = technicien;
        this.ordinateur = ordinateur;
        this.prixMainDoeuvre = prixMainDoeuvre;
    }

    // Constructeur sans id
    public Reparation(Timestamp dateDebut, Timestamp dateFin, String descri, Technicien technicien, Ordinateur ordinateur, double prixMainDoeuvre) {
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.descri = descri;
        this.technicien = technicien;
        this.ordinateur = ordinateur;
        this.prixMainDoeuvre = prixMainDoeuvre;
    }

    // Getters et setters
    public int getIdReparation() {
        return idReparation;
    }

    public void setIdReparation(int idReparation) {
        this.idReparation = idReparation;
    }

    public Timestamp getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Timestamp dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Timestamp getDateFin() {
        return dateFin;
    }

    public void setDateFin(Timestamp dateFin) {
        this.dateFin = dateFin;
    }

    public String getDescri() {
        return descri;
    }

    public void setDescri(String descri) {
        this.descri = descri;
    }

    public Technicien getTechnicien() {
        return technicien;
    }

    public void setTechnicien(Technicien technicien) {
        this.technicien = technicien;
    }

    public Ordinateur getOrdinateur() {
        return ordinateur;
    }

    public void setOrdinateur(Ordinateur ordinateur) {
        this.ordinateur = ordinateur;
    }

    public List<Composant> getComposants() {
        return composants;
    }

    public void setComposants(List<Composant> composants) {
        this.composants = composants;
    }

    public double getPrixMainDoeuvre() {
        return prixMainDoeuvre;
    }

    public void setPrixMainDoeuvre(double prixMainDoeuvre) {
        this.prixMainDoeuvre = prixMainDoeuvre;
    }

    // Fonction save (Insertion dans la base)
    public void save(Connection connexion) throws Exception {
        if (connexion == null) {
            connexion = Connexion.getConnexion();
        }

        String sql = "INSERT INTO reparation (date_debut, date_fin, descri, id_technicien, id_ordinateur,prix_main_doeuvre) VALUES (?, ?, ?, ?, ?, ?) RETURNING id_reparation";
        try (PreparedStatement stmt = connexion.prepareStatement(sql)) {
            stmt.setTimestamp(1, this.dateDebut);
            stmt.setTimestamp(2, this.dateFin);
            stmt.setString(3, this.descri);
            stmt.setInt(4, this.technicien.getIdTechnicien());
            stmt.setInt(5, this.ordinateur.getIdOrdinateur());
            stmt.setDouble(6, this.prixMainDoeuvre);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    this.idReparation = rs.getInt("id_reparation");
                    connexion.commit();
                }
            }
        }
        catch (Exception e) {
            connexion.rollback();
            throw e;
        } 
        finally {
            connexion.close();
        }
    }
    
    public void ajouterComposant(Composant composant, String probleme, Connection connexion) throws Exception {
        if (connexion == null) {
            connexion = Connexion.getConnexion();
        }

        // Insertion du composant dans la table Reparation_composant
        String sql = "INSERT INTO reparation_composant (id_composant, id_reparation, probleme) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connexion.prepareStatement(sql)) {
            stmt.setInt(1, composant.getIdComposant());
            stmt.setInt(2, this.idReparation);  
            stmt.setString(3, probleme);
            stmt.executeUpdate();
        }

        // Ajout du composant à la liste interne pour une gestion locale
        if (!this.composants.contains(composant)) {
            this.composants.add(composant);
            connexion.commit();
            connexion.close();
        }
    }

    // Fonction getAll (Récupération de toutes les réparations)
    public static List<Reparation> getAll(Connection connexion) throws Exception {
        if (connexion == null) {
            connexion = Connexion.getConnexion();
        }

        List<Reparation> reparations = new ArrayList<>();
        String sql = "SELECT * FROM reparation";
        try (PreparedStatement stmt = connexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Technicien technicien = Technicien.getById(connexion, rs.getInt("id_technicien"));
                Ordinateur ordinateur = Ordinateur.getById(connexion, rs.getInt("id_ordinateur"));
                Reparation reparation = new Reparation(
                        rs.getInt("id_reparation"),
                        rs.getTimestamp("date_debut"),
                        rs.getTimestamp("date_fin"),
                        rs.getString("descri"),
                        technicien,
                        ordinateur,
                        rs.getDouble("prix_main_doeuvre")
                );

                // Récupération des composants associés à cette réparation
                List<Composant> composants = getComposantsByReparationId(connexion, reparation.getIdReparation());
                reparation.setComposants(composants);

                reparations.add(reparation);
            }
        }
        return reparations;
    }

    // Fonction pour récupérer les composants associés à une réparation
    public static List<Composant> getComposantsByReparationId(Connection connexion, int idReparation) throws Exception {
        if (connexion == null) {
            connexion = Connexion.getConnexion();
        }
        List<Composant> composants = new ArrayList<>();
        String sql = "SELECT * FROM reparation_composant WHERE id_reparation = ?";
        try (PreparedStatement stmt = connexion.prepareStatement(sql)) {
            stmt.setInt(1, idReparation);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Composant composant = Composant.getById(connexion, rs.getInt("id_composant"));
                    composants.add(composant);
                }
            }
        }
        return composants;
    }

    public static Reparation getById(Connection connexion, int id) throws Exception {
        // Vérification de la connexion et initialisation si nécessaire
        if (connexion == null) {
            connexion = Connexion.getConnexion();
        }
    
        String sql = "SELECT * FROM reparation WHERE id_reparation = ?";
        try (PreparedStatement stmt = connexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Récupérer le technicien et l'ordinateur associés
                    Technicien technicien = Technicien.getById(connexion, rs.getInt("id_technicien"));
                    Ordinateur ordinateur = Ordinateur.getById(connexion, rs.getInt("id_ordinateur"));
                    
                    // Créer un objet Reparation
                    Reparation reparation = new Reparation(
                        rs.getInt("id_reparation"),
                        rs.getTimestamp("date_debut"),
                        rs.getTimestamp("date_fin"),
                        rs.getString("descri"),
                        technicien,
                        ordinateur,
                        rs.getDouble("prix_main_doeuvre")
                    );
    
                    // Récupérer les composants associés à cette réparation
                    List<Composant> composants = getComposantsByReparationId(connexion, reparation.getIdReparation());
                    reparation.setComposants(composants);
    
                    return reparation;
                }
            }
        }
        // Retourner null si aucune réparation n'a été trouvée
        return null;
    }
    
    // Fonction pour récupérer les réparations associées à un type de composant donné
    public static List<Reparation> getByComposantType(Connection connexion, String typeComposant) throws Exception {
        // Vérification de la connexion et initialisation si nécessaire
        if (connexion == null) {
            connexion = Connexion.getConnexion();
        }

        List<Reparation> reparations = new ArrayList<>();
        String sql = "SELECT DISTINCT r.* " +
                    "FROM reparation r " +
                    "JOIN reparation_composant rc ON r.id_reparation = rc.id_reparation " +
                    "JOIN composant c ON rc.id_composant = c.id_composant " +
                    "WHERE c.type_composant = ?";

        try (PreparedStatement stmt = connexion.prepareStatement(sql)) {
            stmt.setString(1, typeComposant);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    // Récupérer les données de chaque réparation
                    Technicien technicien = Technicien.getById(connexion, rs.getInt("id_technicien"));
                    Ordinateur ordinateur = Ordinateur.getById(connexion, rs.getInt("id_ordinateur"));
                    
                    Reparation reparation = new Reparation(
                            rs.getInt("id_reparation"),
                            rs.getTimestamp("date_debut"),
                            rs.getTimestamp("date_fin"),
                            rs.getString("descri"),
                            technicien,
                            ordinateur,
                            rs.getDouble("prix_main_doeuvre")
                    );

                    // Récupérer les composants associés à cette réparation
                    List<Composant> composants = getComposantsByReparationId(connexion, reparation.getIdReparation());
                    reparation.setComposants(composants);

                    reparations.add(reparation);
                }
            }
        }
        return reparations;
    }
    
    public static List<Reparation> getRetours(Connection connexion, String typeComposant, String categorieModele) throws Exception {
        if (connexion == null) {
            connexion = Connexion.getConnexion();
        }
    
        List<Reparation> reparations = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
            "SELECT DISTINCT r.* " +
            "FROM v_retour r " +
            "JOIN reparation_composant rc ON r.id_reparation = rc.id_reparation " +
            "JOIN composant c ON rc.id_composant = c.id_composant " +
            "JOIN modele_composant mc ON c.id_composant = mc.id_composant " +
            "JOIN modele m ON mc.id_modele = m.id_modele " +
            "JOIN categorie_modele cm ON m.id_categorie_modele = cm.id_categorie_modele "
        );
    
        // Ajout des conditions dynamiques
        List<Object> params = new ArrayList<>();
        boolean whereAdded = false;
    
        if (typeComposant != null && !typeComposant.isEmpty()) {
            sql.append("WHERE c.id_type_composant = ? ");
            params.add(typeComposant);
            whereAdded = true;
        }
        if (categorieModele != null && !categorieModele.isEmpty()) {
            sql.append(whereAdded ? "AND " : "WHERE ");
            sql.append("cm.nom_type = ? ");
            params.add(categorieModele);
        }
    
        try (PreparedStatement stmt = connexion.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }
    
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Technicien technicien = Technicien.getById(connexion, rs.getInt("id_technicien"));
                    Ordinateur ordinateur = Ordinateur.getById(connexion, rs.getInt("id_ordinateur"));
    
                    Reparation reparation = new Reparation(
                        rs.getInt("id_reparation"),
                        rs.getTimestamp("date_debut"),
                        rs.getTimestamp("date_fin"),
                        rs.getString("descri"),
                        technicien,
                        ordinateur,
                        rs.getDouble("prix_main_doeuvre")
                    );
    
                    // Récupérer les composants associés à la réparation
                    List<Composant> composants = getComposantsByReparationId(connexion, reparation.getIdReparation());
                    reparation.setComposants(composants);
    
                    reparations.add(reparation);
                }
            }
        }
        return reparations;
    }
    
}
