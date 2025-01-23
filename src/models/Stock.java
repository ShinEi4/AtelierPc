package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import utils.Connexion;

public class Stock {
    private int idStock;
    private int entree;
    private int sortie;
    private Timestamp dateMvt; // Utilisation de java.sql.Timestamp
    private Composant composant; // Association avec la classe Composant

    // Constructeur vide
    public Stock() {
    }

    // Constructeur avec id
    public Stock(int idStock, int entree, int sortie, Timestamp dateMvt, Composant composant) {
        this.idStock = idStock;
        this.entree = entree;
        this.sortie = sortie;
        this.dateMvt = dateMvt;
        this.composant = composant;
    }

    // Constructeur sans id
    public Stock(int entree, int sortie, Timestamp dateMvt, Composant composant) {
        this.entree = entree;
        this.sortie = sortie;
        this.dateMvt = dateMvt;
        this.composant = composant;
    }

    // Getters et setters
    public int getIdStock() {
        return idStock;
    }

    public void setIdStock(int idStock) {
        this.idStock = idStock;
    }

    public int getEntree() {
        return entree;
    }

    public void setEntree(int entree) {
        this.entree = entree;
    }

    public int getSortie() {
        return sortie;
    }

    public void setSortie(int sortie) {
        this.sortie = sortie;
    }

    public Timestamp getDateMvt() {
        return dateMvt;
    }

    public void setDateMvt(Timestamp dateMvt) {
        this.dateMvt = dateMvt;
    }

    public Composant getComposant() {
        return composant;
    }

    public void setComposant(Composant composant) {
        this.composant = composant;
    }

    // Fonction save (Insertion dans la base)
    public void save(Connection connexion) throws Exception {
        if (connexion == null) {
            connexion = Connexion.getConnexion();
        }

        String sql = "INSERT INTO stock (entree, sortie, date_mvt, id_composant) VALUES (?, ?, ?, ?) RETURNING id_stock";
        try (PreparedStatement stmt = connexion.prepareStatement(sql)) {
            stmt.setInt(1, this.entree);
            stmt.setInt(2, this.sortie);
            stmt.setTimestamp(3, this.dateMvt); // Utilisation de Timestamp
            stmt.setInt(4, this.composant.getIdComposant());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    this.idStock = rs.getInt("id_stock");
                    connexion.commit();
                    connexion.close();
                }
            }
        }
    }

    // Fonction getAll (Récupération de tous les mouvements de stock)
    public static List<Stock> getAll(Connection connexion) throws Exception {
        if (connexion == null) {
            connexion = Connexion.getConnexion();
        }

        List<Stock> stocks = new ArrayList<>();
        String sql = "SELECT * FROM stock";
        try (PreparedStatement stmt = connexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Composant composant = Composant.getById(connexion, rs.getInt("id_composant"));
                Stock stock = new Stock(
                        rs.getInt("id_stock"),
                        rs.getInt("entree"),
                        rs.getInt("sortie"),
                        rs.getTimestamp("date_mvt"), // Utilisation de Timestamp
                        composant
                );
                stocks.add(stock);
            }
        }
        return stocks;
    }

    // Fonction getById (Récupération d'un mouvement de stock par son id)
    public static Stock getById(Connection connexion, int idStock) throws Exception {
        if (connexion == null) {
            connexion = Connexion.getConnexion();
        }

        String sql = "SELECT * FROM stock WHERE id_stock = ?";
        try (PreparedStatement stmt = connexion.prepareStatement(sql)) {
            stmt.setInt(1, idStock);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Composant composant = Composant.getById(connexion, rs.getInt("id_composant"));
                    return new Stock(
                            rs.getInt("id_stock"),
                            rs.getInt("entree"),
                            rs.getInt("sortie"),
                            rs.getTimestamp("date_mvt"), // Utilisation de Timestamp
                            composant
                    );
                }
            }
        }
        return null; // Si aucun mouvement de stock n'est trouvé avec l'id donné
    }
}
