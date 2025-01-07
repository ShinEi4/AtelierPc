package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connexion {
    public static Connection getConnexion() throws ClassNotFoundException, SQLException{
        final String USER="postgres";
        final String PWD="postgres1234";
        final String DATABASE="atelier";
        final String HOST="localhost";
        Class.forName("org.postgresql.Driver");
        Connection connex=DriverManager.getConnection("jdbc:postgresql://"+HOST+"/"+DATABASE, USER, PWD);
        connex.setAutoCommit(false);
        return connex;
    }
}
