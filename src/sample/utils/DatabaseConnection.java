package sample.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    public Connection databaseLink;

    public Connection openConnection() {
        String url = "jdbc:postgresql://localhost:5433/LavoratoriStagionali";
        String user = "postgres";
        String password = "Pimpa1310";

        try {
            Class.forName("org.postgresql.Driver");
            databaseLink = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return databaseLink;
    }

    public Connection closeConnection() {
        try{
            databaseLink.close();
        } catch (SQLException sqlE){
            System.out.println("Error: " + sqlE.getMessage());
        }
        return databaseLink;
    }
}