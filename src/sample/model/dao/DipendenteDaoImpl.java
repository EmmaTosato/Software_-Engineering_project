package sample.model.dao;

import sample.utils.DatabaseConnection;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DipendenteDaoImpl implements DipendenteDao{

    public static int checkCredenziali(String username, String passw){

        // Connessione al database
        DatabaseConnection connetNow = new DatabaseConnection();
        Connection connectDB = connetNow.openConnection();

        String query = "SELECT * FROM credenziali WHERE username = ? AND pass = ?";

        try{
            PreparedStatement statement = connectDB.prepareStatement(query);

            statement.setString(1, username);
            statement.setString(2, passw);


            ResultSet result = statement.executeQuery();


            // Se username e password sono ok
            while(result.next()){
                return 1;
            }
        }
        catch (SQLException e) {
            Logger log = Logger.getLogger(DipendenteDaoImpl.class.getName());
            log.log(Level.SEVERE, e.getMessage(), e);
        }

        return 0;
    }
}
