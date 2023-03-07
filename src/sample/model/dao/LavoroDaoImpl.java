package sample.model.dao;

import sample.model.Lavoro;
import sample.utils.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LavoroDaoImpl implements  LavoroDao {

    public void insertLavoro(Lavoro lavoro) {
        // CONNESSIONE AL DATABASE
        DatabaseConnection connecNow = new DatabaseConnection();
        Connection connectDB = connecNow.openConnection();

        try {
            // INSERIMENTO DEI DATI LAVORO
            String query = "INSERT INTO LAVORO(nomelavoro, idlavoratore, nomeAzienda, luogoLavoro, retribuzioneLorda, retribuzioneGiornaliera, mansione_1, mansione_2, dataInizio, dataFine) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connectDB.prepareStatement(query);

            statement.setString(1, lavoro.getNomeLavoro());
            statement.setString(2, lavoro.getIdLavoratore());
            statement.setString(3, lavoro.getNomeAzienda());
            statement.setString(4, lavoro.getLuogoLavoro());
            statement.setInt(5, lavoro.getRetribuzioneLorda());
            statement.setInt(6, lavoro.getRetribuzioneGiornaliera());
            statement.setString(7, lavoro.getMansione_1());
            statement.setString(8, lavoro.getMansione_2());
            statement.setString(9, lavoro.getDataInizio());
            statement.setString(10, lavoro.getDataFine());

            // CONTROLLO
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Table LAVORO successfully updated");
            } else
                System.out.println("Update of LAVORO badly failed");
        } catch (SQLException e) {
            Logger log = Logger.getLogger(LavoroDaoImpl.class.getName());
            log.log(Level.SEVERE, e.getMessage(), e);
        }
    }




    @Override
    public void updateLavoro (Lavoro lavoro){
        //Connessione al database
        DatabaseConnection connetNow = new DatabaseConnection();
        Connection connectDB = connetNow.openConnection();

        String query =
                "UPDATE lavoro SET nomelavoro = ? , nomeAzienda = ?, luogoLavoro = ?, retribuzioneLorda = ?, retribuzioneGiornaliera = ?," +
                " mansione_1 = ?, mansione_2 = ?, dataInizio = ?, dataFine = ? " +
                "WHERE chiaveSec = ? ";

        try{
            PreparedStatement statement = connectDB.prepareStatement(query);

            statement.setString(1, lavoro.getNomeLavoro());
            statement.setString(2, lavoro.getNomeAzienda());
            statement.setString(3, lavoro.getLuogoLavoro());
            statement.setInt(4, lavoro.getRetribuzioneLorda());
            statement.setInt(5, lavoro.getRetribuzioneGiornaliera());
            statement.setString(6, lavoro.getMansione_1());
            statement.setString(7, lavoro.getMansione_2());
            statement.setString(8, lavoro.getDataInizio());
            statement.setString(9, lavoro.getDataFine());
            statement.setInt(10, lavoro.getChiave());

            int affectedRows = statement.executeUpdate();

            if(affectedRows > 0){
                System.out.println("Table LAVORO successfully updated");
            } else
                System.out.println("Update of LAVORO badly failed");
        }
        catch (SQLException e) {
            Logger log = Logger.getLogger(LavoroDao.class.getName());
            log.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    @Override
    public void deleteLavoro(int id) {
        // Connessione al database
        DatabaseConnection connetNow = new DatabaseConnection();
        Connection connectDB = connetNow.openConnection();

        String query = "DELETE FROM lavoro WHERE chiavesec = ?";

        try{
            PreparedStatement statement = connectDB.prepareStatement(query);
            statement.setInt(1, id);

            // CONTROLLO
            int affectedRows = statement.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Table LAVORO successfully updated");
            } else
                System.out.println("Update of LAVORO badly failed");


        } catch (SQLException e) {
            Logger log = Logger.getLogger(LavoroDaoImpl.class.getName());
            log.log(Level.SEVERE, e.getMessage(), e);
        }

    }


    // Ottengo tuti i lavori che corrispondono ad un certo id, quindi ad un certo lavoratore
    @Override
    public List<Lavoro> getLavori (String idLavoratore){
        List<Lavoro> lavori = new ArrayList<>();

        // Connessione al database
        DatabaseConnection connetNow = new DatabaseConnection();
        Connection connectDB = connetNow.openConnection();

        String query = "SELECT * FROM lavoro WHERE idlavoratore = ?";

        try{
            PreparedStatement stat = connectDB.prepareStatement(query);
            stat.setString(1, idLavoratore);
            ResultSet rs1 = stat.executeQuery();


            while(rs1.next()){
                lavori.add(new Lavoro(rs1.getInt("chiaveSec"), rs1.getString("nomeLavoro"), rs1.getString("idLavoratore"),
                        rs1.getString("nomeAzienda"), rs1.getString("luogoLavoro"), rs1.getInt("retribuzioneGiornaliera"),
                        rs1.getInt("retribuzioneLorda"), rs1.getString("mansione_1"),  rs1.getString("mansione_2"),
                        rs1.getString("dataInizio"), rs1.getString("dataFine")));


            }
        } catch (SQLException e) {
            Logger log = Logger.getLogger(LavoroDaoImpl.class.getName());
            log.log(Level.SEVERE, e.getMessage(), e);
        }

        return lavori;
    }


    // Ottengo tutti i lavori fatti da un determinato lavoratore
    @Override
    public List<Lavoro> getAllLavori (String id) {
        List<Lavoro> lavori = new ArrayList<>();

        //CONNESSIONE AL DATABASE
        DatabaseConnection connetNow = new DatabaseConnection();
        Connection connectDB = connetNow.openConnection();

        String query = "SELECT * FROM lavoro WHERE idLavoratore = ? ORDER BY nomelavoro, idlavoratore";

        try{
            PreparedStatement statement = connectDB.prepareStatement(query);
            statement.setString(1, id);
            ResultSet rs = statement.executeQuery();

            while(rs.next()){
                lavori.add(new Lavoro(rs.getInt("chiaveSec"), rs.getString("nomeLavoro"), rs.getString("idLavoratore"), rs.getString("nomeAzienda"), rs.getString("luogoLavoro"),
                        rs.getInt("retribuzioneLorda"), rs.getInt("retribuzioneGiornaliera"), rs.getString("mansione_1"), rs.getString("mansione_2"),
                        rs.getString("datainizio"), rs.getString("datafine")));
            }
        } catch (SQLException e){
            Logger log = Logger.getLogger(LavoroDaoImpl.class.getName());
            log.log(Level.SEVERE, e.getMessage(), e);
        }

        return lavori;
    };

    @Override
    public List<String> getAllMansioni (){
        List<String> mansioni= new ArrayList<>();

        // Connessione al database
        DatabaseConnection connetNow = new DatabaseConnection();
        Connection connectDB = connetNow.openConnection();

        String query =  "SELECT DISTINCT mansione_1 AS mansioni FROM lavoro\n" +
                        "UNION\n" +
                        "SELECT DISTINCT  mansione_2 AS mansioni FROM lavoro";


        try{
            PreparedStatement statement = connectDB.prepareStatement(query);
            ResultSet rs = statement.executeQuery();

            while(rs.next()){
                mansioni.add(rs.getString("mansioni"));
            }


        } catch (SQLException e) {
            Logger log = Logger.getLogger(LavoroDaoImpl.class.getName());
            log.log(Level.SEVERE, e.getMessage(), e);
        }

        Collections.sort(mansioni, String.CASE_INSENSITIVE_ORDER);
        return mansioni;

    }




}
