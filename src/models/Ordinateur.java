package models;

import utils.Connexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Ordinateur {
    private int idOrdinateur;
    private String idSerie;
    private Modele modele;
    private Client client;

    // Constructeur vide
    public Ordinateur() {
    }

    // Constructeur avec id
    public Ordinateur(int idOrdinateur, String idSerie, Modele modele, Client client) {
        this.idOrdinateur = idOrdinateur;
        this.idSerie = idSerie;
        this.modele = modele;
        this.client = client;
    }

    // Constructeur sans id
    public Ordinateur(String idSerie, Modele modele, Client client) {
        this.idSerie = idSerie;
        this.modele = modele;
        this.client = client;
    }

    // Getters et setters
    public int getIdOrdinateur() {
        return idOrdinateur;
    }

    public void setIdOrdinateur(int idOrdinateur) {
        this.idOrdinateur = idOrdinateur;
    }

    public String getIdSerie() {
        return idSerie;
    }

    public void setIdSerie(String idSerie) {
        this.idSerie = idSerie;
    }

    public Modele getModele() {
        return modele;
    }

    public void setModele(Modele modele) {
        this.modele = modele;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    // Fonction save (Insertion dans la base)
    public void save(Connection connexion) throws SQLException, ClassNotFoundException {
        if (connexion == null) {
            connexion = Connexion.getConnexion();
        }

        String sql = "INSERT INTO ordinateur (id_serie, id_modele, id_client) VALUES (?, ?, ?) RETURNING id_ordinateur";
        try (PreparedStatement stmt = connexion.prepareStatement(sql)) {
            stmt.setString(1, this.idSerie);
            stmt.setInt(2, this.modele.getIdModele());
            stmt.setInt(3, this.client.getIdClient());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    this.idOrdinateur = rs.getInt("id_ordinateur");
                    connexion.commit();
                    connexion.close();
                }
            }
        }
    }

    // Fonction getAll (Récupération de tous les ordinateurs)
    public static List<Ordinateur> getAll(Connection connexion) throws Exception {
        if (connexion == null) {
            connexion = Connexion.getConnexion();
        }

        List<Ordinateur> ordinateurs = new ArrayList<>();
        String sql = "SELECT * FROM ordinateur";
        try (PreparedStatement stmt = connexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Modele modele = Modele.getById(connexion, rs.getInt("id_modele"));
                Client client = Client.getById(connexion, rs.getInt("id_client"));
                Ordinateur ordinateur = new Ordinateur(
                        rs.getInt("id_ordinateur"),
                        rs.getString("id_serie"),
                        modele,
                        client
                );
                ordinateurs.add(ordinateur);
            }
        }
        return ordinateurs;
    }

    // Fonction getById (Récupération d'un ordinateur par son id)
    public static Ordinateur getById(Connection connexion, int idOrdinateur) throws Exception {
        if (connexion == null) {
            connexion = Connexion.getConnexion();
        }

        String sql = "SELECT * FROM ordinateur WHERE id_ordinateur = ?";
        try (PreparedStatement stmt = connexion.prepareStatement(sql)) {
            stmt.setInt(1, idOrdinateur);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Modele modele = Modele.getById(connexion, rs.getInt("id_modele"));
                    Client client = Client.getById(connexion, rs.getInt("id_client"));
                    return new Ordinateur(
                            rs.getInt("id_ordinateur"),
                            rs.getString("id_serie"),
                            modele,
                            client
                    );
                }
            }
        }
        return null; // Si aucun ordinateur n'est trouvé avec l'id donné
    }
}
