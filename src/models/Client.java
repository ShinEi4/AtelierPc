package models;

import utils.Connexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Client {
    private int idClient;
    private String nom;
    private String num;

    // Constructeur vide
    public Client() {
    }

    // Constructeur avec id
    public Client(int idClient, String nom, String num) {
        this.idClient = idClient;
        this.nom = nom;
        this.num = num;
    }

    // Constructeur sans id
    public Client(String nom, String num) {
        this.nom = nom;
        this.num = num;
    }

    // Getters et setters
    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    // Fonction save (Insertion dans la base)
    public void save(Connection connexion) throws SQLException, ClassNotFoundException {
        if (connexion == null) {
            connexion = Connexion.getConnexion();
        }

        String sql = "INSERT INTO client (nom, num) VALUES (?, ?) RETURNING id_client";
        try (PreparedStatement stmt = connexion.prepareStatement(sql)) {
            stmt.setString(1, this.nom);
            stmt.setString(2, this.num);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    this.idClient = rs.getInt("id_client");
                    connexion.commit();
                    connexion.close();
                }
            }
        }
    }

    // Fonction getAll (Récupération de tous les clients)
    public static List<Client> getAll(Connection connexion) throws SQLException, ClassNotFoundException {
        if (connexion == null) {
            connexion = Connexion.getConnexion();
        }

        List<Client> clients = new ArrayList<>();
        String sql = "SELECT * FROM client";
        try (PreparedStatement stmt = connexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Client client = new Client(
                        rs.getInt("id_client"),
                        rs.getString("nom"),
                        rs.getString("num")
                );
                clients.add(client);
            }
        }
        return clients;
    }

    // Fonction getById (Récupération d'un client par son id)
    public static Client getById(Connection connexion, int idClient) throws SQLException, ClassNotFoundException {
        if (connexion == null) {
            connexion = Connexion.getConnexion();
        }

        String sql = "SELECT * FROM client WHERE id_client = ?";
        try (PreparedStatement stmt = connexion.prepareStatement(sql)) {
            stmt.setInt(1, idClient);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Client(
                            rs.getInt("id_client"),
                            rs.getString("nom"),
                            rs.getString("num")
                    );
                }
            }
        }
        return null; // Si aucun client n'est trouvé avec l'id donné
    }
}
