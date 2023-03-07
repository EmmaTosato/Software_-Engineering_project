package sample.utils;


import java.sql.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IdCreation {


    public long contTuple (){
        // Connessione al database
        DatabaseConnection connetNow = new DatabaseConnection();
        Connection connectDB = connetNow.openConnection();

        List<String> elencoId = new ArrayList<>();
        long id ;
        String query= "SELECT idLavoratore FROM LAVORATORE ORDER BY idLavoratore ASC ";

        try {
            PreparedStatement statement = connectDB.prepareStatement(query);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                elencoId.add(rs.getString("idLavoratore"));
            }

        }catch (SQLException e) {
            Logger log = Logger.getLogger(IdCreation.class.getName());
            log.log(Level.SEVERE, e.getMessage(), e);
        }

        if( !elencoId.isEmpty() ){
            String stringaFin = elencoId.get(elencoId.size() - 1).replace("L", "");
            id = Long.parseLong(stringaFin) + 1;
            return id;
        }
        else {
            return 0;
        }
    }



    public String createUniqueID (){
        NumberFormat formatter = new DecimalFormat("00000");

        return "L" + formatter.format(contTuple());
    }


}
