package sample.model.dao;

import sample.utils.DatabaseConnection;
import sample.model.Lavoratore;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class LavoratoreDaoImpl implements LavoratoreDao {

    @Override
    public void insertLavoratore(Lavoratore lavoratore){
        // Connessione al database
        DatabaseConnection connetNow = new DatabaseConnection();
        Connection connectDB = connetNow.openConnection();


        try{
            /*INSERIMENTO DATI LAVORATORE*/
            String query = "INSERT INTO LAVORATORE(idLavoratore, nome, cognome, mail, telefono, dataNascita, " +
                    "luogoNascita, via, numeroCivico, paese, nazionalità, patente, automunito, comune1, " +
                    "comune2, datainiziodisponibilità_comune1, dataFineDisponibilità_comune1,  " +
                    "dataInizioDisponibilità_comune2, dataFineDisponibilità_comune2, nomeEmergenza, cognomeEmergenza, mailEmergenza," +
                    " telefonoEmergenza) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connectDB.prepareStatement(query);


            statement.setString(1, lavoratore.getIdLavoratore());
            statement.setString(2, lavoratore.getNome());
            statement.setString(3, lavoratore.getCognome());
            statement.setString(4, lavoratore.getMail());
            statement.setString(5, lavoratore.getTelefono());
            statement.setString(6, lavoratore.getDataNascita());
            statement.setString(7, lavoratore.getLuogoNascita());
            statement.setString(8, lavoratore.getVia());
            statement.setString(9, lavoratore.getNumeroCivico());
            statement.setString(10, lavoratore.getPaese());
            statement.setString(11, lavoratore.getNazionalità());
            statement.setString(12, lavoratore.getPatente());
            statement.setString(13, lavoratore.getAutomunito());
            statement.setString(14, lavoratore.getComune1());
            statement.setString(15, lavoratore.getComune2());
            statement.setString(16, lavoratore.getDataInizioDisponibilità_comune1());
            statement.setString(17, lavoratore.getDataFineDisponibilità_comune1());
            statement.setString(18, lavoratore.getDataInizioDisponibilità_comune2());
            statement.setString(19, lavoratore.getDataFineDisponibilità_comune2());
            statement.setString(20, lavoratore.getNomeEmergenza());
            statement.setString(21, lavoratore.getCognomeEmergenza());
            statement.setString(22, lavoratore.getMailEmergenza());
            statement.setString(23, lavoratore.getTelEmergenza());

            // Controllo
            int affectedRows = statement.executeUpdate();
            if(affectedRows > 0 ){
                System.out.println("Table LAVORATORE successfully updated");
            } else
                System.out.println("Update of LAVORATORE badly failed");


            /*INSERIMENTO LINGUE CONOSCIUTE DEL LAVORATORE*/
            for (int i = 0; i < lavoratore.getLingue().size(); i++){
                String query2 = "INSERT INTO LINGUECONOSCIUTE (idLavoratore, lingua) VALUES (?, ?)";
                PreparedStatement statement2 = connectDB.prepareStatement(query2);

                String lingua;
                lingua = lavoratore.getLingue().get(i);

                statement2.setString(1,lavoratore.getIdLavoratore());
                statement2.setString(2,lingua);

                // Controllo
                int affectedRows2 = statement2.executeUpdate();
                if( affectedRows2 > 0){
                    System.out.println("Table LINGUECONOSCIUTE successfully updated");
                } else
                    System.out.println("Update of LINGUECONOSCIUTE badly failed");
            }


            /*INSERIMENTO ESPERIENZE FATTE DEL LAVORATORE*/
            for (int i = 0; i < lavoratore.getEsperienze().size(); i++){
                String query3 = "INSERT INTO ESPERIENZEPRECEDENTI (idLavoratore, esperienza) VALUES (?, ?)";
                PreparedStatement statement3 = connectDB.prepareStatement(query3);

                String esperienza;
                esperienza = lavoratore.getEsperienze().get(i);

                statement3.setString(1, lavoratore.getIdLavoratore());
                statement3.setString(2,esperienza);

                // Controllo
                int affectedRows3 = statement3.executeUpdate();
                if( affectedRows3 > 0){
                    System.out.println("Table ESPERIENZEPRECEDENTI successfully updated");
                } else
                    System.out.println("Update of ESPERIENZEPRECEDENTI badly failed");
            }
        }
        catch (SQLException e) {
            Logger log = Logger.getLogger(LavoratoreDaoImpl.class.getName());
            log.log(Level.SEVERE, e.getMessage(), e);
        }
    }



    @Override
    public void updateLavoratore (Lavoratore lavoratore){
        //Connessione al database
        DatabaseConnection connetNow = new DatabaseConnection();
        Connection connectDB = connetNow.openConnection();

        String query =
                "UPDATE lavoratore SET nome = ? , cognome = ?, mail = ?, telefono = ?, dataNascita = ?, " +
                        "luogoNascita = ?, via = ?, numeroCivico = ?, paese = ?, nazionalità = ?, patente = ?, automunito = ?, comune1 = ?, " +
                        "comune2 = ?, datainiziodisponibilità_comune1 = ?, dataFineDisponibilità_comune1 = ?,  " +
                        "dataInizioDisponibilità_comune2 = ?, dataFineDisponibilità_comune2 = ?, nomeEmergenza = ?,cognomeEmergenza = ?, mailEmergenza = ?," +
                        " telefonoEmergenza = ? WHERE idlavoratore = ? ";

        try{
            PreparedStatement statement = connectDB.prepareStatement(query);

            statement.setString(1, lavoratore.getNome());
            statement.setString(2, lavoratore.getCognome());
            statement.setString(3, lavoratore.getMail());
            statement.setString(4, lavoratore.getTelefono());
            statement.setString(5, lavoratore.getDataNascita());
            statement.setString(6, lavoratore.getLuogoNascita());
            statement.setString(7, lavoratore.getVia());
            statement.setString(8, lavoratore.getNumeroCivico());
            statement.setString(9, lavoratore.getPaese());
            statement.setString(10, lavoratore.getNazionalità());
            statement.setString(11, lavoratore.getPatente());
            statement.setString(12, lavoratore.getAutomunito());
            statement.setString(13, lavoratore.getComune1());
            statement.setString(14, lavoratore.getComune2());
            statement.setString(15, lavoratore.getDataInizioDisponibilità_comune1());
            statement.setString(16, lavoratore.getDataFineDisponibilità_comune1());
            statement.setString(17, lavoratore.getDataInizioDisponibilità_comune2());
            statement.setString(18, lavoratore.getDataFineDisponibilità_comune2());
            statement.setString(19, lavoratore.getNomeEmergenza());
            statement.setString(20, lavoratore.getCognomeEmergenza());
            statement.setString(21, lavoratore.getMailEmergenza());
            statement.setString(22, lavoratore.getTelEmergenza());
            statement.setString(23, lavoratore.getIdLavoratore());

            int affectedRows = statement.executeUpdate();
            if(affectedRows > 0){
                System.out.println("Table LAVORTORE successfully updated");
            } else
                System.out.println("Update of LAVORATORE badly failed");


            /* PRIMA ELIMINO LE LINGUE e le ESPERRIENZE PASSATE */
            String lingueElim = "DELETE FROM lingueconosciute WHERE idlavoratore = ?";
            String esperienzeElim = "DELETE FROM esperienzeprecedenti WHERE idlavoratore = ?";

            PreparedStatement statementLingue = connectDB.prepareStatement(lingueElim);
            PreparedStatement statementEsperienze = connectDB.prepareStatement(esperienzeElim);

            statementLingue.setString(1, lavoratore.getIdLavoratore());
            statementEsperienze.setString(1, lavoratore.getIdLavoratore());


            int affectedRowsLingue = statementLingue.executeUpdate();
            int affectedRowsEsperienze = statementEsperienze.executeUpdate();


            /*INSERIMENTO LINGUE CONOSCIUTE DEL LAVORATORE*/
            for (int i = 0; i < lavoratore.getLingue().size(); i++){
                String query2 = "INSERT INTO LINGUECONOSCIUTE (idLavoratore, lingua) VALUES (?, ?)";
                PreparedStatement statement2 = connectDB.prepareStatement(query2);

                statement2.setString(1,lavoratore.getIdLavoratore());
                statement2.setString(2,lavoratore.getLingue().get(i));

                // Controllo
                int affectedRows2 = statement2.executeUpdate();
                if( affectedRows2 > 0){
                    System.out.println("Table LINGUECONOSCIUTE successfully updated");
                } else
                    System.out.println("Update of LINGUECONOSCIUTE badly failed");
            }


            /*INSERIMENTO ESPERIENZE FATTE DEL LAVORATORE*/
            for (int i = 0; i < lavoratore.getEsperienze().size(); i++){
                String query3 = "INSERT INTO ESPERIENZEPRECEDENTI (idLavoratore, esperienza) VALUES (?, ?)";
                PreparedStatement statement3 = connectDB.prepareStatement(query3);

                statement3.setString(1, lavoratore.getIdLavoratore());
                statement3.setString(2,lavoratore.getEsperienze().get(i));

                // Controllo
                int affectedRows3 = statement3.executeUpdate();
                if( affectedRows3 > 0){
                    System.out.println("Table ESPERIENZEPRECEDENTI successfully updated");
                } else
                    System.out.println("Update of ESPERIENZEPRECEDENTI badly failed");
            }
        }
        catch (SQLException e) {
            Logger log = Logger.getLogger(LavoratoreDaoImpl.class.getName());
            log.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    @Override
    public void deleteLavoratore(String id) {
        // Connessione al database
        DatabaseConnection connetNow = new DatabaseConnection();
        Connection connectDB = connetNow.openConnection();


        String lingue = "DELETE FROM lingueconosciute WHERE idlavoratore = ?";
        String esperienze = "DELETE FROM esperienzeprecedenti WHERE idlavoratore = ?";
        String lavoro =  "DELETE FROM lavoro WHERE idlavoratore = ?";
        String lavoratore = "DELETE FROM lavoratore WHERE idlavoratore = ?";

        try{
            PreparedStatement statement1 = connectDB.prepareStatement(lingue);
            PreparedStatement statement2 = connectDB.prepareStatement(esperienze);
            PreparedStatement statement3 = connectDB.prepareStatement(lavoro);
            PreparedStatement statement4 = connectDB.prepareStatement(lavoratore);

            statement1.setString(1, id);
            statement2.setString(1, id);
            statement3.setString(1, id);
            statement4.setString(1, id);

            // CONTROLLO
            int affectedRows1 = statement1.executeUpdate();
            int affectedRows2 = statement2.executeUpdate();
            int affectedRows3 = statement3.executeUpdate();
            int affectedRows4 = statement4.executeUpdate();


            if (affectedRows1 > 0) {
                System.out.println("Table LINGUECONOSCIUTE successfully updated");
            } else
                System.out.println("Update of LINGUECONOSCIUTE badly failed");
            if (affectedRows2 > 0) {
                System.out.println("Table ESPERIENZEPRECEDENTI successfully updated");
            } else
                System.out.println("Update of ESPERIENZEPRECEDENTI badly failed");
            if (affectedRows3 > 0) {
                System.out.println("Table LAVORO successfully updated");
            } else
                System.out.println("Update of LAVORO badly failed");
            if (affectedRows4 > 0) {
                System.out.println("Table LAVORATORE successfully updated");
            } else
                System.out.println("Update of LAVORATORE badly failed");


        } catch (SQLException e) {
            Logger log = Logger.getLogger(LavoratoreDaoImpl.class.getName());
            log.log(Level.SEVERE, e.getMessage(), e);
        }

    }

    @Override
    public Lavoratore getLavoratore(String idLavoratore) {
        // Oggetti e variabili
        Lavoratore lavoratore= new Lavoratore();
        List<String> esperienzePassate = new ArrayList<>();
        List<String> lingueConosciute = new ArrayList<>();

        // Connessione al database
        DatabaseConnection connetNow = new DatabaseConnection();
        Connection connectDB = connetNow.openConnection();

        String queryLavoratore = "SELECT * FROM LAVORATORE WHERE idlavoratore = ?";
        String queryEsperienze = "SELECT * FROM ESPERIENZEPRECEDENTI WHERE idlavoratore = ?";
        String queryLingue = "SELECT * FROM LINGUECONOSCIUTE WHERE idlavoratore = ? ";

        try{
            PreparedStatement stat1 = connectDB.prepareStatement(queryLavoratore);
            PreparedStatement stat2 = connectDB.prepareStatement(queryEsperienze);
            PreparedStatement stat3 = connectDB.prepareStatement(queryLingue);

            stat1.setString(1, idLavoratore);
            stat2.setString(1, idLavoratore);
            stat3.setString(1, idLavoratore);


            ResultSet rs1 = stat1.executeQuery();
            ResultSet rs2 = stat2.executeQuery();
            ResultSet rs3 = stat3.executeQuery();


            while(rs1.next()){
                lavoratore.setIdLavoratore(rs1.getString("idLavoratore"));
                lavoratore.setNome(rs1.getString("nome"));
                lavoratore.setCognome(rs1.getString("cognome"));
                lavoratore.setMail(rs1.getString("mail"));
                lavoratore.setTelefono(rs1.getString("telefono"));
                lavoratore.setDataNascita(rs1.getString("dataNascita"));
                lavoratore.setLuogoNascita(rs1.getString("luogoNascita"));
                lavoratore.setVia(rs1.getString("via"));
                lavoratore.setNumeroCivico(rs1.getString("numeroCivico"));
                lavoratore.setPaese(rs1.getString("paese"));
                lavoratore.setNazionalità(rs1.getString("nazionalità"));
                lavoratore.setPatente(rs1.getString("patente"));
                lavoratore.setAutomunito(rs1.getString("automunito"));
                lavoratore.setComune1(rs1.getString("comune1"));
                lavoratore.setComune2(rs1.getString("comune2"));
                lavoratore.setDataInizioDisponibilità_comune1(rs1.getString("dataInizioDisponibilità_comune1"));
                lavoratore.setDataFineDisponibilità_comune1(rs1.getString("dataFineDisponibilità_comune1"));
                lavoratore.setDataInizioDisponibilità_comune2(rs1.getString("dataInizioDisponibilità_comune2"));
                lavoratore.setDataFineDisponibilità_comune2(rs1.getString("dataFineDisponibilità_comune2"));
                lavoratore.setNomeEmergenza(rs1.getString("nomeEmergenza"));
                lavoratore.setCognomeEmergenza(rs1.getString("cognomeEmergenza"));
                lavoratore.setMailEmergenza(rs1.getString("mailEmergenza"));
                lavoratore.setTelEmergenza(rs1.getString("telefonoEmergenza"));



            }
            while(rs2.next()){
                esperienzePassate.add(rs2.getString("esperienza"));
            }
            lavoratore.setEsperienze(esperienzePassate);

            while(rs3.next()){
                lingueConosciute.add(rs3.getString("lingua"));
            }
            lavoratore.setLingue(lingueConosciute);



        } catch (SQLException e) {
            Logger log = Logger.getLogger(LavoratoreDaoImpl.class.getName());
            log.log(Level.SEVERE, e.getMessage(), e);
        }

        return lavoratore;
    }


    @Override
    public List<Lavoratore> getAllLavoratori(){
        List<Lavoratore> lavoratori= new ArrayList<>();

        // Connessione al database
        DatabaseConnection connetNow = new DatabaseConnection();
        Connection connectDB = connetNow.openConnection();

        String query = "SELECT idLavoratore, nome, cognome FROM LAVORATORE ORDER BY cognome, nome ASC";

        try{
            ResultSet rs = connectDB.createStatement().executeQuery(query);

            while(rs.next()){
                lavoratori.add(new Lavoratore(rs.getString("idLavoratore"), rs.getString("nome"), rs.getString("cognome")));
            }

        } catch (SQLException e) {
            Logger log = Logger.getLogger(LavoratoreDaoImpl.class.getName());
            log.log(Level.SEVERE, e.getMessage(), e);
        }

        return lavoratori;
    }

    @Override
    public List<String> getAllLuoghiResidenza(){
        List<String> luoghi= new ArrayList<>();

        // Connessione al database
        DatabaseConnection connetNow = new DatabaseConnection();
        Connection connectDB = connetNow.openConnection();

        String query = "SELECT DISTINCT paese FROM LAVORATORE ORDER BY paese ASC";

        try{
            ResultSet rs = connectDB.createStatement().executeQuery(query);

            while(rs.next()){
                luoghi.add(rs.getString("paese"));
            }

        } catch (SQLException e) {
            Logger log = Logger.getLogger(LavoratoreDaoImpl.class.getName());
            log.log(Level.SEVERE, e.getMessage(), e);
        }

        return luoghi;
    }



    public List<String> getAllDateInizio (){
        List<String> dateInizio= new ArrayList<>();

        // Connessione al database
        DatabaseConnection connetNow = new DatabaseConnection();
        Connection connectDB = connetNow.openConnection();

        String query =  "SELECT DISTINCT datainiziodisponibilità_comune1 AS dateinizio FROM lavoratore\n" +
                        "UNION\n" +
                        "SELECT DISTINCT datainiziodisponibilità_comune2 AS dateinizio FROM lavoratore;";

        try{
            ResultSet rs = connectDB.createStatement().executeQuery(query);

            while(rs.next()){
                dateInizio.add(rs.getString("dateinizio"));
            }


        } catch (SQLException e) {
            Logger log = Logger.getLogger(LavoratoreDaoImpl.class.getName());
            log.log(Level.SEVERE, e.getMessage(), e);
        }

        return dateInizio;

    }

    public List<String> getAllDateFine (){
        List<String> dateFine= new ArrayList<>();

        // Connessione al database
        DatabaseConnection connetNow = new DatabaseConnection();
        Connection connectDB = connetNow.openConnection();

        String query =  "SELECT DISTINCT datafinedisponibilità_comune1 AS datefine FROM lavoratore\n" +
                        "UNION\n" +
                        "SELECT DISTINCT datafinedisponibilità_comune2 AS datefine FROM lavoratore";

        try{
            ResultSet rs = connectDB.createStatement().executeQuery(query);

            while(rs.next()){
                dateFine.add(rs.getString("datefine"));
            }


        } catch (SQLException e) {
            Logger log = Logger.getLogger(LavoratoreDaoImpl.class.getName());
            log.log(Level.SEVERE, e.getMessage(), e);
        }

        return dateFine;

    }


}




