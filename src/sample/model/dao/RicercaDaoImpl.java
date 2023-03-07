package sample.model.dao;

import sample.model.Ricerca;
import sample.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RicercaDaoImpl implements RicercaDao {


    /* SE E' SELEZIONATO SOLO UN PARAMETRO */
    @Override
    public List<String> ricercaSemplice(Ricerca search) {
        // Istanzio elenco
        List<String> elencoId = new ArrayList<>();

        // Connessione al database
        DatabaseConnection connetNow = new DatabaseConnection();
        Connection connectDB = connetNow.openConnection();

        // Prendo il tipo del parametro
        String tipo = search.getTipoParametro().get(0);

        try {
            switch (tipo) {
                case "Lingua":
                    String lingua = search.getRicercaLingue().get(0);
                    String query = "SELECT DISTINCT lingueconosciute.idLavoratore FROM lavoratore, lingueconosciute INNER JOIN lavoratore l on lingueconosciute.idLavoratore = l.idLavoratore WHERE lingua = ? ORDER BY idLavoratore ASC";

                    PreparedStatement statement = connectDB.prepareStatement(query);
                    statement.setString(1, lingua);
                    ResultSet res = statement.executeQuery();

                    while (res.next()) {
                        elencoId.add(new String(res.getString("idLavoratore")));
                    }

                    return elencoId;

                case "Mansione":
                    // "Mi devo prendere gli id dei lavoratori che hanno svolto una determinata mansione"
                    String mansione = search.getRicercaMansioni().get(0);
                    String query2 =
                            "SELECT DISTINCT idlavoratore\n" +
                                    "FROM lavoro\n" +
                                    "WHERE mansione_1 = ? OR mansione_2 = ?\n" +
                                    "ORDER BY idlavoratore ASC";

                    PreparedStatement statement2 = connectDB.prepareStatement(query2);
                    statement2.setString(1, mansione);
                    statement2.setString(2, mansione);
                    ResultSet res2 = statement2.executeQuery();

                    while (res2.next()) {
                        elencoId.add(new String(res2.getString("idLavoratore")));
                    }

                    return elencoId;

                case "LuogoRes":
                    String luogoRes = search.getRicercaLuoghiResidenza().get(0);
                    String query3 = "SELECT DISTINCT idLavoratore FROM lavoratore WHERE paese = ?";

                    PreparedStatement statement3 = connectDB.prepareStatement(query3);
                    statement3.setString(1, luogoRes);
                    ResultSet res3 = statement3.executeQuery();

                    while (res3.next()) {
                        elencoId.add(new String(res3.getString("idLavoratore")));
                    }

                    return elencoId;

                case "Automunito":
                    String automunito = search.getRicercaAutomuniti().get(0);
                    String query4 = "SELECT DISTINCT idLavoratore FROM lavoratore WHERE automunito = ?";

                    PreparedStatement statement4 = connectDB.prepareStatement(query4);
                    statement4.setString(1, automunito);
                    ResultSet res4 = statement4.executeQuery();

                    while (res4.next()) {
                        elencoId.add(new String(res4.getString("idLavoratore")));
                    }

                    return elencoId;


                case "Patente":
                    String patente = search.getRicercaPatenti().get(0);
                    String query5 = "SELECT DISTINCT idLavoratore FROM lavoratore  WHERE patente = ?";

                    PreparedStatement statement5 = connectDB.prepareStatement(query5);
                    statement5.setString(1, patente);
                    ResultSet res5 = statement5.executeQuery();

                    while (res5.next()) {
                        elencoId.add(new String(res5.getString("idLavoratore")));
                    }

                    return elencoId;

                case "Periodo":
                    String periodoInizio = search.getRicercaPeriodoInizio().get(0);
                    String periodoFine = search.getRicercaPeriodoFine().get(0);

                    String query6 = "SELECT DISTINCT idlavoratore\n" +
                            "FROM lavoratore\n" +
                            "WHERE datainiziodisponibilità_comune1 = ?\n" +
                            "AND datafinedisponibilità_comune1 = ?\n" +
                            "OR\n" +
                            "datainiziodisponibilità_comune2 = ?\n" +
                            "AND datafinedisponibilità_comune2 = ?";

                    PreparedStatement statement6 = connectDB.prepareStatement(query6);
                    statement6.setString(1, periodoInizio);
                    statement6.setString(2, periodoFine);
                    statement6.setString(3, periodoInizio);
                    statement6.setString(4, periodoFine);
                    ResultSet res6 = statement6.executeQuery();

                    while (res6.next()) {
                        elencoId.add(new String(res6.getString("idLavoratore")));
                    }

                    return elencoId;
            }
        } catch (SQLException e) {
            Logger log = Logger.getLogger(RicercaDaoImpl.class.getName());
            log.log(Level.SEVERE, e.getMessage(), e);
        }


        return null;
    }

    /* PIU' PARAMETRI in AND*/
    @Override
    public List<String> ricercaComplessaAND(Ricerca search) {
        // Istanzio elenco e query
        List<String> elencoId = new ArrayList<>();
        String query = new String();

        // Connessione al database
        DatabaseConnection connetNow = new DatabaseConnection();
        Connection connectDB = connetNow.openConnection();

        // Prendo i tipi dei parametri
        String tipo1 = search.getTipoParametro().get(0);
        String tipo2 = search.getTipoParametro().get(2);
        String value1 = new String();
        String value2 = new String();
        String value3 = new String();
        String value4 = new String();


        /* PERIODI */
        if ((tipo1.equals("Periodo") && tipo2.equals("Periodo")) || tipo1.equals("Periodo") || tipo2.equals("Periodo")) {
            if (tipo1.equals("Periodo") && tipo2.equals("Periodo")) {
                value1 = search.getRicercaPeriodoInizio().get(0);       // 1° Data inizio
                value2 = search.getRicercaPeriodoFine().get(0);         // 1° Data fine
                value3 = search.getRicercaPeriodoInizio().get(1);       // 2° Data inizio
                value4 = search.getRicercaPeriodoFine().get(1);         // 2° Data fine

                query = "SELECT DISTINCT idlavoratore\n" +
                        "FROM lavoratore\n" +
                        "WHERE  datainiziodisponibilità_comune1 = ?\n" +
                        "AND  datafinedisponibilità_comune1 = ?\n" +
                        "OR\n" +
                        "    datainiziodisponibilità_comune2 = ?\n" +
                        "AND datafinedisponibilità_comune2 = ?\n" +
                        "INTERSECT\n" +
                        "SELECT DISTINCT idlavoratore\n" +
                        "FROM lavoratore\n" +
                        "WHERE  datainiziodisponibilità_comune1 = ?\n" +
                        "AND  datafinedisponibilità_comune1 = ?\n" +
                        "OR\n" +
                        "datainiziodisponibilità_comune2 = ?\n" +
                        "AND datafinedisponibilità_comune2 = ?\n";

                try {
                    PreparedStatement statement = connectDB.prepareStatement(query);
                    statement.setString(1, value1);
                    statement.setString(2, value2);
                    statement.setString(3, value1);
                    statement.setString(4, value2);
                    statement.setString(5, value3);
                    statement.setString(6, value4);
                    statement.setString(7, value3);
                    statement.setString(8, value4);

                    ResultSet res = statement.executeQuery();

                    while (res.next()) {
                        elencoId.add(new String(res.getString("idLavoratore")));
                    }

                    return elencoId;

                } catch (SQLException e) {
                    Logger log = Logger.getLogger(RicercaDaoImpl.class.getName());
                    log.log(Level.SEVERE, e.getMessage(), e);
                }

            } else if ((tipo1.equals("Mansione") && tipo2.equals("Periodo")) || (tipo1.equals("Periodo") && tipo2.equals("Mansione"))) {
                value1 = search.getRicercaMansioni().get(0);            // Mansione
                value2 = search.getRicercaPeriodoInizio().get(0);       // Data inizo
                value3 = search.getRicercaPeriodoFine().get(0);         // Data fine

                query = "SELECT DISTINCT lavoratore.idlavoratore\n" +
                        "FROM lavoratore\n" +
                        "JOIN lavoro on lavoratore.idlavoratore = lavoro.idlavoratore\n" +
                        "WHERE  (mansione_1 = ? OR mansione_2 = ?)\n" +
                        "AND ((datainiziodisponibilità_comune1 = ?\n" +
                        "AND datafinedisponibilità_comune1 = ?)\n" +
                        "OR\n" +
                        "(datainiziodisponibilità_comune2 = ?\n" +
                        "AND datafinedisponibilità_comune2 = ?))";

                try {
                    PreparedStatement statement = connectDB.prepareStatement(query);
                    statement.setString(1, value1);
                    statement.setString(2, value1);
                    statement.setString(3, value2);
                    statement.setString(4, value3);
                    statement.setString(5, value2);
                    statement.setString(6, value3);


                    ResultSet res = statement.executeQuery();

                    while (res.next()) {
                        elencoId.add(new String(res.getString("idLavoratore")));
                    }

                    return elencoId;

                } catch (SQLException e) {
                    Logger log = Logger.getLogger(RicercaDaoImpl.class.getName());
                    log.log(Level.SEVERE, e.getMessage(), e);
                }

            } else if ((tipo1.equals("Lavoratore") && tipo2.equals("Periodo")) || (tipo1.equals("Periodo") && tipo2.equals("Lavoratore"))) {
                value1 = search.getRicercaLavoratori().get(0);
                value2 = search.getRicercaPeriodoInizio().get(0);
                value3 = search.getRicercaPeriodoFine().get(0);

                query = "SELECT DISTINCT idlavoratore\n" +
                        "FROM lavoratore\n" +
                        "WHERE idlavoratore = ?\n" +
                        "AND ((datainiziodisponibilità_comune1 = ?\n" +
                        "AND datafinedisponibilità_comune1 = ?)\n" +
                        "OR\n" +
                        "(datainiziodisponibilità_comune2 = ?\n" +
                        "AND datafinedisponibilità_comune2 = ?))";


            } else if ((tipo1.equals("Lingua") && tipo2.equals("Periodo")) || (tipo1.equals("Periodo") && tipo2.equals("Lingua"))) {
                value1 = search.getRicercaLingue().get(0);
                value2 = search.getRicercaPeriodoInizio().get(0);
                value3 = search.getRicercaPeriodoFine().get(0);

                query = "SELECT DISTINCT lingueconosciute.idlavoratore\n" +
                        "FROM lingueconosciute\n" +
                        "JOIN lavoratore on lingueconosciute.idlavoratore = lavoratore.idlavoratore\n" +
                        "WHERE lingua = ?\n" +
                        "AND ((datainiziodisponibilità_comune1 = ?\n" +
                        "AND datafinedisponibilità_comune1 = ?)\n" +
                        "OR\n" +
                        "(datainiziodisponibilità_comune2 = ?\n" +
                        "AND datafinedisponibilità_comune2 = ?))";


            } else if ((tipo1.equals("LuogoRes") && tipo2.equals("Periodo")) || (tipo1.equals("Periodo") && tipo2.equals("LuogoRes"))) {
                value1 = search.getRicercaLuoghiResidenza().get(0);
                value2 = search.getRicercaPeriodoInizio().get(0);
                value3 = search.getRicercaPeriodoFine().get(0);

                query = "SELECT DISTINCT idlavoratore\n" +
                        "FROM lavoratore\n" +
                        "WHERE  paese = ?\n" +
                        "AND ((datainiziodisponibilità_comune1 = ?\n" +
                        "AND datafinedisponibilità_comune1 = ?)\n" +
                        "OR\n" +
                        "(datainiziodisponibilità_comune2 = ?\n" +
                        "AND datafinedisponibilità_comune2 = ?));";

            } else if ((tipo1.equals("Patente") && tipo2.equals("Periodo")) || (tipo1.equals("Periodo") && tipo2.equals("Patente"))) {
                value1 = search.getRicercaPatenti().get(0);
                value2 = search.getRicercaPeriodoInizio().get(0);
                value3 = search.getRicercaPeriodoFine().get(0);

                query = "SELECT DISTINCT idlavoratore\n" +
                        "FROM lavoratore\n" +
                        "WHERE  patente = ?\n" +
                        "AND ((datainiziodisponibilità_comune1 = ?\n" +
                        "AND datafinedisponibilità_comune1 = ?)\n" +
                        "OR\n" +
                        "(datainiziodisponibilità_comune2 = ?\n" +
                        "AND datafinedisponibilità_comune2 = ?))";
            } else if ((tipo1.equals("Automunito") && tipo2.equals("Periodo")) || (tipo1.equals("Periodo") && tipo2.equals("Automunito"))) {
                value1 = search.getRicercaAutomuniti().get(0);
                value2 = search.getRicercaPeriodoInizio().get(0);
                value3 = search.getRicercaPeriodoFine().get(0);

                query = "SELECT DISTINCT idlavoratore\n" +
                        "FROM lavoratore\n" +
                        "WHERE  automunito = ?\n" +
                        "AND ((datainiziodisponibilità_comune1 = ?\n" +
                        "AND datafinedisponibilità_comune1 = ?)\n" +
                        "OR\n" +
                        "(datainiziodisponibilità_comune2 = ?\n" +
                        "AND datafinedisponibilità_comune2 = ?));";

            }

            /* Qui eseguo effettivamente la query*/
            try {
                PreparedStatement statement = connectDB.prepareStatement(query);
                statement.setString(1, value1);
                statement.setString(2, value2);
                statement.setString(3, value3);
                statement.setString(4, value2);
                statement.setString(5, value3);

                ResultSet res = statement.executeQuery();

                while (res.next()) {
                    elencoId.add(new String(res.getString("idLavoratore")));
                }

                return elencoId;

            } catch (SQLException e) {
                Logger log = Logger.getLogger(RicercaDaoImpl.class.getName());
                log.log(Level.SEVERE, e.getMessage(), e);
            }
        }
        /* MANSIONI (non mansione and periodo) */
        if ((tipo1.equals("Mansione") && tipo2.equals("Mansione")) || tipo1.equals("Mansione") || tipo2.equals("Mansione")) {
            if (tipo1.equals("Mansione") && tipo2.equals("Mansione")) {
                value1 = search.getRicercaMansioni().get(0);
                value2 = search.getRicercaMansioni().get(1);

                query = "SELECT DISTINCT L1.idlavoratore\n" +
                        "FROM lavoro AS L1\n" +
                        " JOIN lavoro AS L2 ON L1.idlavoratore = L2.idlavoratore\n" +
                        "WHERE (L1.mansione_1 = ? OR L1.mansione_2 = ?)\n" +
                        "  AND (L2.mansione_1 = ? OR L2.mansione_2 = ?)\n" +
                        "ORDER BY L1.idlavoratore";

                /* Qui eseguo effettivamente la query*/
                try {
                    PreparedStatement statement = connectDB.prepareStatement(query);
                    statement.setString(1, value1);
                    statement.setString(2, value1);
                    statement.setString(3, value2);
                    statement.setString(4, value2);
                    ResultSet res = statement.executeQuery();

                    while (res.next()) {
                        elencoId.add(new String(res.getString("idLavoratore")));
                    }

                    return elencoId;

                } catch (SQLException e) {
                    Logger log = Logger.getLogger(RicercaDaoImpl.class.getName());
                    log.log(Level.SEVERE, e.getMessage(), e);
                }
            } else if ((tipo1.equals("Lavoratore") && tipo2.equals("Mansione")) || (tipo1.equals("Mansione") && tipo2.equals("Lavoratore"))) {
                value1 = search.getRicercaLavoratori().get(0);
                value2 = search.getRicercaMansioni().get(0);

                query = "SELECT DISTINCT lavoratore.idlavoratore\n" +
                        "FROM lavoratore\n" +
                        "JOIN lavoro on lavoratore.idlavoratore = lavoro.idlavoratore\n" +
                        "WHERE lavoratore.idlavoratore = ?\n" +
                        "AND (mansione_1 = ? OR mansione_2 = ?)\n" +
                        "ORDER BY idlavoratore ASC;";

            } else if ((tipo1.equals("Lingua") && tipo2.equals("Mansione")) || (tipo1.equals("Mansione") && tipo2.equals("Lingua"))) {
                value1 = search.getRicercaLingue().get(0);
                value2 = search.getRicercaMansioni().get(0);

                query = "SELECT DISTINCT lingueconosciute.idlavoratore\n" +
                        "FROM lingueconosciute\n" +
                        "JOIN lavoro on lingueconosciute.idlavoratore = lavoro.idlavoratore\n" +
                        "WHERE lingua = ?\n" +
                        "AND (mansione_1 = ? OR mansione_2 = ?)\n" +
                        "ORDER BY idlavoratore ASC;";

            } else if ((tipo1.equals("Mansione") && tipo2.equals("LuogoRes")) || (tipo1.equals("LuogoRes") && tipo2.equals("Mansione"))) {
                value1 = search.getRicercaLuoghiResidenza().get(0);
                value2 = search.getRicercaMansioni().get(0);

                query = "SELECT DISTINCT lavoratore.idlavoratore\n" +
                        "FROM lavoratore\n" +
                        "JOIN lavoro on lavoratore.idlavoratore = lavoro.idlavoratore\n" +
                        "WHERE paese = ?\n" +
                        "  AND (mansione_1 = ? OR mansione_2 = ?)\n" +
                        "ORDER BY idlavoratore ASC;";

            } else if ((tipo1.equals("Mansione") && tipo2.equals("Patente")) || (tipo1.equals("Patente") && tipo2.equals("Mansione"))) {
                value1 = search.getRicercaPatenti().get(0);
                value2 = search.getRicercaMansioni().get(0);

                query = "SELECT DISTINCT lavoratore.idlavoratore\n" +
                        "FROM lavoratore\n" +
                        "JOIN lavoro on lavoratore.idlavoratore = lavoro.idlavoratore\n" +
                        "WHERE patente = ?\n" +
                        "  AND (mansione_1 = ? OR mansione_2 = ?)\n" +
                        "ORDER BY idlavoratore ASC;";

            } else if ((tipo1.equals("Mansione") && tipo2.equals("Automunito")) || (tipo1.equals("Automunito") && tipo2.equals("Mansione"))) {
                value1 = search.getRicercaAutomuniti().get(0);
                value2 = search.getRicercaMansioni().get(0);

                query = "SELECT DISTINCT lavoratore.idlavoratore\n" +
                        "FROM lavoratore\n" +
                        "JOIN lavoro on lavoratore.idlavoratore = lavoro.idlavoratore\n" +
                        "WHERE automunito = ?\n" +
                        "  AND (mansione_1 = ? OR mansione_2 = ?)\n" +
                        "ORDER BY idlavoratore ASC;";
            }

            /* Qui eseguo effettivamente la query*/
            try {
                PreparedStatement statement = connectDB.prepareStatement(query);
                statement.setString(1, value1);
                statement.setString(2, value2);
                statement.setString(3, value2);
                ResultSet res = statement.executeQuery();

                while (res.next()) {
                    elencoId.add(new String(res.getString("idLavoratore")));
                }

                return elencoId;

            } catch (SQLException e) {
                Logger log = Logger.getLogger(RicercaDaoImpl.class.getName());
                log.log(Level.SEVERE, e.getMessage(), e);
            }

        }
        /* LAVORATORE - LINGUA  - LUOGO DI RESIDENZA - PATENTE - AUTOMUNITO */
        else {
            /* Doppi parametri */
            if (tipo1.equals("Lingua") && tipo2.equals("Lingua")) {
                value1 = search.getRicercaLingue().get(0);
                value2 = search.getRicercaLingue().get(1);

                query = "SELECT DISTINCT L1.idlavoratore\n" +
                        "FROM lingueconosciute AS L1\n" +
                        "JOIN lingueconosciute AS L2 ON L1.idlavoratore = L2.idlavoratore\n" +
                        "JOIN lavoratore on L1.idlavoratore = lavoratore.idlavoratore\n" +
                        "WHERE L1.lingua = ? \n" +
                        "    AND L2.lingua = ? \n" +
                        "ORDER BY idlavoratore ASC;";
            }
            /* Lavoratore */
            else if ((tipo1.equals("Lavoratore") && tipo2.equals("Lingua")) || (tipo1.equals("Lingua") && tipo2.equals("Lavoratore"))) {
                value1 = search.getRicercaLavoratori().get(0);
                value2 = search.getRicercaLingue().get(0);

                query = "SELECT DISTINCT lingueconosciute.idlavoratore\n" +
                        "FROM lingueconosciute\n" +
                        "JOIN lavoratore on lingueconosciute.idlavoratore = lavoratore.idlavoratore\n" +
                        "WHERE lavoratore.idlavoratore = ?\n" +
                        "AND lingua = ?\n" +
                        "ORDER BY idlavoratore ASC;\n";

            } else if ((tipo1.equals("Lavoratore") && tipo2.equals("LuogoRes")) || (tipo1.equals("LuogoRes") && tipo2.equals("Lavoratore"))) {
                value1 = search.getRicercaLavoratori().get(0);
                value2 = search.getRicercaLuoghiResidenza().get(0);

                query = "SELECT DISTINCT idlavoratore\n" +
                        "FROM lavoratore\n" +
                        "WHERE idlavoratore = ?\n" +
                        "AND paese = ?\n" +
                        "ORDER BY idlavoratore ASC;\n";

            } else if ((tipo1.equals("Lavoratore") && tipo2.equals("Patente")) || (tipo1.equals("Patente") && tipo2.equals("Lavoratore"))) {
                value1 = search.getRicercaLavoratori().get(0);
                value2 = search.getRicercaPatenti().get(0);

                query = "SELECT DISTINCT idlavoratore\n" +
                        "FROM lavoratore\n" +
                        "WHERE idlavoratore = ?\n" +
                        "AND patente = ?\n" +
                        "ORDER BY idlavoratore ASC;\n";

            } else if ((tipo1.equals("Lavoratore") && tipo2.equals("Automunito")) || (tipo1.equals("Automunito") && tipo2.equals("Lavoratore"))) {
                value1 = search.getRicercaLavoratori().get(0);
                value2 = search.getRicercaAutomuniti().get(0);

                query = "SELECT DISTINCT idlavoratore\n" +
                        "FROM lavoratore\n" +
                        "WHERE idlavoratore = ?\n" +
                        "AND automunito = ?\n" +
                        "ORDER BY idlavoratore ASC;\n";

            }
            /* Lingua */
            else if ((tipo1.equals("Lingua") && tipo2.equals("LuogoRes")) || tipo1.equals("LuogoRes") && tipo2.equals("Lingua")) {
                value1 = search.getRicercaLingue().get(0);
                value2 = search.getRicercaLuoghiResidenza().get(0);

                query = "SELECT DISTINCT lingueconosciute.idlavoratore\n" +
                        "FROM lingueconosciute\n" +
                        "JOIN lavoratore on lingueconosciute.idlavoratore = lavoratore.idlavoratore\n" +
                        "WHERE lingua = ? \n" +
                        "  AND lavoratore.paese = ? \n" +
                        "ORDER BY idlavoratore ASC;";

            } else if ((tipo1.equals("Lingua") && tipo2.equals("Patente")) || (tipo1.equals("Patente") && tipo2.equals("Lingua"))) {
                value1 = search.getRicercaLingue().get(0);
                value2 = search.getRicercaPatenti().get(0);

                query = "SELECT DISTINCT lingueconosciute.idlavoratore\n" +
                        "FROM lingueconosciute\n" +
                        " JOIN lavoratore on lingueconosciute.idlavoratore = lavoratore.idlavoratore\n" +
                        "WHERE lingua = ?\n" +
                        "  AND lavoratore.patente = ? \n" +
                        "ORDER BY idlavoratore ASC;";

            } else if ((tipo1.equals("Lingua") && tipo2.equals("Automunito")) || (tipo1.equals("Automunito") && tipo2.equals("Lingua"))) {
                value1 = search.getRicercaLingue().get(0);
                value2 = search.getRicercaAutomuniti().get(0);

                query = "SELECT DISTINCT lingueconosciute.idlavoratore\n" +
                        "FROM lingueconosciute\n" +
                        " JOIN lavoratore on lingueconosciute.idlavoratore = lavoratore.idlavoratore\n" +
                        "WHERE lingua = ? \n" +
                        "  AND lavoratore.automunito = ? \n" +
                        "ORDER BY idlavoratore ASC;";

            }
            /* Luogo residenza */
            else if ((tipo1.equals("LuogoRes") && tipo2.equals("Patente")) || (tipo1.equals("Patente") && tipo2.equals("LuogoRes"))) {
                value1 = search.getRicercaLuoghiResidenza().get(0);
                value2 = search.getRicercaPatenti().get(0);

                query = "SELECT DISTINCT idlavoratore\n" +
                        "FROM lavoratore\n" +
                        "WHERE  paese = ?\n" +
                        "AND  patente = ?\n" +
                        "ORDER BY idlavoratore ASC;";

            } else if ((tipo1.equals("LuogoRes") && tipo2.equals("Automunito")) || (tipo1.equals("Automunito") && tipo2.equals("LuogoRes"))) {
                value1 = search.getRicercaLuoghiResidenza().get(0);
                value2 = search.getRicercaAutomuniti().get(0);

                query = "SELECT DISTINCT idlavoratore\n" +
                        "FROM lavoratore\n" +
                        "WHERE  paese = ?\n" +
                        "AND  automunito = ?\n" +
                        "ORDER BY idlavoratore ASC;";

            }
            /* Patente */
            else if ((tipo1.equals("Patente") && tipo2.equals("Automunito")) || (tipo1.equals("Automunito") && tipo2.equals("Patente"))) {
                value1 = search.getRicercaPatenti().get(0);
                value2 = search.getRicercaAutomuniti().get(0);

                query = "SELECT DISTINCT idlavoratore\n" +
                        "FROM lavoratore\n" +
                        "WHERE  patente = ?\n" +
                        "AND  automunito = ?\n" +
                        "ORDER BY idlavoratore ASC;";

            }

            /* Qui eseguo effettivamente la query*/
            try {
                PreparedStatement statement = connectDB.prepareStatement(query);
                statement.setString(1, value1);
                statement.setString(2, value2);
                ResultSet res = statement.executeQuery();

                while (res.next()) {
                    elencoId.add(new String(res.getString("idLavoratore")));
                }

                return elencoId;

            } catch (SQLException e) {
                Logger log = Logger.getLogger(RicercaDaoImpl.class.getName());
                log.log(Level.SEVERE, e.getMessage(), e);
            }
        }

        return null;

    }

    /* PIU' PARAMETRI in OR*/
    @Override
    public List<String> ricercaComplessaOR(Ricerca search) {
        // Istanzio elenco e query
        List<String> elencoId = new ArrayList<>();
        String query = new String();

        // Connessione al database
        DatabaseConnection connetNow = new DatabaseConnection();
        Connection connectDB = connetNow.openConnection();

        // Prendo i tipi dei parametri
        String tipo1 = search.getTipoParametro().get(0);
        String booleano = search.getTipoParametro().get(1);
        String tipo2 = search.getTipoParametro().get(2);
        String value1 = new String();
        String value2 = new String();
        String value3 = new String();
        String value4 = new String();

        /* PERIODI */
        if ((tipo1.equals("Periodo") && tipo2.equals("Periodo")) || tipo1.equals("Periodo") || tipo2.equals("Periodo")) {
            if (tipo1.equals("Periodo") && tipo2.equals("Periodo")) {
                value1 = search.getRicercaPeriodoInizio().get(0);
                value2 = search.getRicercaPeriodoFine().get(0);
                value3 = search.getRicercaPeriodoInizio().get(1);
                value4 = search.getRicercaPeriodoFine().get(1);

                query = "SELECT DISTINCT idlavoratore\n" +
                        "FROM lavoratore\n" +
                        "WHERE  (datainiziodisponibilità_comune1 = ?\n" +
                        "AND  datafinedisponibilità_comune1 = ?\n" +
                        "OR\n" +
                        "datainiziodisponibilità_comune2 = ?\n" +
                        "AND datafinedisponibilità_comune2 = ?)\n" +
                        "                OR\n" +
                        "(datainiziodisponibilità_comune1 = ?\n" +
                        "AND  datafinedisponibilità_comune1 = ?\n" +
                        "OR\n" +
                        "datainiziodisponibilità_comune2 = ?\n" +
                        "AND datafinedisponibilità_comune2 = ?);";


                try {
                    PreparedStatement statement = connectDB.prepareStatement(query);
                    statement.setString(1, value1);
                    statement.setString(2, value2);
                    statement.setString(3, value1);
                    statement.setString(4, value2);
                    statement.setString(5, value3);
                    statement.setString(6, value4);
                    statement.setString(7, value3);
                    statement.setString(8, value4);

                    ResultSet res = statement.executeQuery();

                    while (res.next()) {
                        elencoId.add(new String(res.getString("idLavoratore")));
                    }

                    return elencoId;

                } catch (SQLException e) {
                    Logger log = Logger.getLogger(RicercaDaoImpl.class.getName());
                    log.log(Level.SEVERE, e.getMessage(), e);
                }
            } else if ((tipo1.equals("Mansione") && tipo2.equals("Periodo")) || (tipo1.equals("Periodo") && tipo2.equals("Mansione"))) {
                value1 = search.getRicercaMansioni().get(0);
                value2 = search.getRicercaPeriodoInizio().get(0);
                value3 = search.getRicercaPeriodoFine().get(0);


                query = "SELECT DISTINCT lavoratore.idlavoratore\n" +
                        "FROM lavoratore\n LEFT JOIN lavoro on lavoratore.idlavoratore = lavoro.idlavoratore\n" +
                        "WHERE  (mansione_1 = ? OR mansione_2 = ?)\n" +
                        "OR \n" +
                        "(datainiziodisponibilità_comune1 = ?\n" +
                        "AND datafinedisponibilità_comune1 = ?\n" +
                        "OR\n" +
                        "datainiziodisponibilità_comune2 = ?\n" +
                        "AND datafinedisponibilità_comune2 = ?);";


                try {
                    PreparedStatement statement = connectDB.prepareStatement(query);
                    statement.setString(1, value1);
                    statement.setString(2, value1);
                    statement.setString(3, value2);
                    statement.setString(4, value3);
                    statement.setString(5, value2);
                    statement.setString(6, value3);


                    ResultSet res = statement.executeQuery();

                    while (res.next()) {
                        elencoId.add(new String(res.getString("idLavoratore")));
                    }

                    return elencoId;

                } catch (SQLException e) {
                    Logger log = Logger.getLogger(RicercaDaoImpl.class.getName());
                    log.log(Level.SEVERE, e.getMessage(), e);
                }
            } else if ((tipo1.equals("Lavoratore") && tipo2.equals("Periodo")) || (tipo1.equals("Periodo") && tipo2.equals("Lavoratore"))) {
                value1 = search.getRicercaLavoratori().get(0);
                value2 = search.getRicercaPeriodoInizio().get(0);
                value3 = search.getRicercaPeriodoFine().get(0);

                query = "SELECT DISTINCT idlavoratore\n" +
                        "FROM lavoratore\n" +
                        "WHERE  idlavoratore = ?\n" +
                        "OR \n" +
                        "((datainiziodisponibilità_comune1 = ?\n" +
                        "AND datafinedisponibilità_comune1 = ?)\n" +
                        "OR\n" +
                        "(datainiziodisponibilità_comune2 = ?\n" +
                        "AND datafinedisponibilità_comune2 = ?))";


            } else if ((tipo1.equals("Lingua") && tipo2.equals("Periodo")) || (tipo1.equals("Periodo") && tipo2.equals("Lingua"))) {
                value1 = search.getRicercaLingue().get(0);
                value2 = search.getRicercaPeriodoInizio().get(0);
                value3 = search.getRicercaPeriodoFine().get(0);

                query = "SELECT DISTINCT lingueconosciute.idlavoratore\n" +
                        "FROM lingueconosciute\n" +
                        "JOIN lavoratore on lingueconosciute.idlavoratore = lavoratore.idlavoratore\n" +
                        "WHERE lingua = ?\n" +
                        "OR\n" +
                        "(datainiziodisponibilità_comune1 = ?\n" +
                        "AND datafinedisponibilità_comune1 = ?\n" +
                        "OR\n" +
                        "datainiziodisponibilità_comune2 = ?\n" +
                        "AND datafinedisponibilità_comune2 = ?);";


            } else if ((tipo1.equals("LuogoRes") && tipo2.equals("Periodo")) || (tipo1.equals("Periodo") && tipo2.equals("LuogoRes"))) {
                value1 = search.getRicercaLuoghiResidenza().get(0);
                value2 = search.getRicercaPeriodoInizio().get(0);
                value3 = search.getRicercaPeriodoFine().get(0);

                query = "SELECT DISTINCT idlavoratore\n" +
                        "FROM lavoratore\n" +
                        "WHERE  paese = ?\n" +
                        "OR \n" +
                        "((datainiziodisponibilità_comune1 = ?\n" +
                        "AND datafinedisponibilità_comune1 = ?)\n" +
                        "OR\n" +
                        "(datainiziodisponibilità_comune2 = ?\n" +
                        "AND datafinedisponibilità_comune2 = ?))";

            } else if ((tipo1.equals("Patente") && tipo2.equals("Periodo")) || (tipo1.equals("Periodo") && tipo2.equals("Patente"))) {
                value1 = search.getRicercaPatenti().get(0);
                value2 = search.getRicercaPeriodoInizio().get(0);
                value3 = search.getRicercaPeriodoFine().get(0);

                query = "SELECT DISTINCT idlavoratore\n" +
                        "FROM lavoratore\n" +
                        "WHERE  patente = ?\n" +
                        "OR \n" +
                        "((datainiziodisponibilità_comune1 = ?\n" +
                        "AND datafinedisponibilità_comune1 = ?)\n" +
                        "OR\n" +
                        "(datainiziodisponibilità_comune2 = ?\n" +
                        "AND datafinedisponibilità_comune2 = ?))";

            } else if ((tipo1.equals("Automunito") && tipo2.equals("Periodo")) || (tipo1.equals("Periodo") && tipo2.equals("Automunito"))) {
                value1 = search.getRicercaAutomuniti().get(0);
                value2 = search.getRicercaPeriodoInizio().get(0);
                value3 = search.getRicercaPeriodoFine().get(0);

                query = "SELECT DISTINCT idlavoratore\n" +
                        "FROM lavoratore\n" +
                        "WHERE  automunito = ?\n" +
                        "OR \n" +
                        "((datainiziodisponibilità_comune1 = ?\n" +
                        "AND datafinedisponibilità_comune1 = ?)\n" +
                        "OR\n" +
                        "(datainiziodisponibilità_comune2 = ?\n" +
                        "AND datafinedisponibilità_comune2 = ?))";

            }

            try {
                PreparedStatement statement = connectDB.prepareStatement(query);
                statement.setString(1, value1);
                statement.setString(2, value2);
                statement.setString(3, value3);
                statement.setString(4, value2);
                statement.setString(5, value3);
                ResultSet res = statement.executeQuery();

                while (res.next()) {
                    elencoId.add(new String(res.getString("idLavoratore")));
                }

                return elencoId;

            } catch (SQLException e) {
                Logger log = Logger.getLogger(RicercaDaoImpl.class.getName());
                log.log(Level.SEVERE, e.getMessage(), e);
            }
        }
        /* MANSIONI (non mansione OR periodo) */
        else if ((tipo1.equals("Mansione") && tipo2.equals("Mansione")) || tipo1.equals("Mansione") || tipo2.equals("Mansione")) {
            if (tipo1.equals("Mansione") && tipo2.equals("Mansione")) {
                value1 = search.getRicercaMansioni().get(0);
                value2 = search.getRicercaMansioni().get(1);

                query = "SELECT DISTINCT idlavoratore\n" +
                        "FROM lavoro\n" +
                        "WHERE (lavoro.mansione_1 = ? OR lavoro.mansione_2 = ?)\n" +
                        "OR (lavoro.mansione_1 = ? OR lavoro.mansione_2 = ?)\n" +
                        "ORDER BY idlavoratore ;";

                try {
                    PreparedStatement statement = connectDB.prepareStatement(query);
                    statement.setString(1, value1);
                    statement.setString(2, value1);
                    statement.setString(3, value2);
                    statement.setString(4, value2);
                    ResultSet res = statement.executeQuery();

                    while (res.next()) {
                        elencoId.add(new String(res.getString("idLavoratore")));
                    }

                    return elencoId;

                } catch (SQLException e) {
                    Logger log = Logger.getLogger(RicercaDaoImpl.class.getName());
                    log.log(Level.SEVERE, e.getMessage(), e);
                }
            } else if ((tipo1.equals("Lavoratore") && tipo2.equals("Mansione")) || (tipo1.equals("Mansione") && tipo2.equals("Lavoratore"))) {
                value1 = search.getRicercaLavoratori().get(0);
                value2 = search.getRicercaMansioni().get(0);

                query = "SELECT DISTINCT lavoratore.idlavoratore\n" +
                        "FROM lavoratore LEFT JOIN lavoro on lavoratore.idlavoratore = lavoro.idlavoratore\n" +
                        "WHERE lavoratore.idlavoratore = ?\n" +
                        "OR (mansione_1 = ? OR mansione_2 = ?)\n" +
                        "ORDER BY lavoratore.idlavoratore ASC;";
            } else if ((tipo1.equals("Lingua") && tipo2.equals("Mansione")) || (tipo1.equals("Mansione") && tipo2.equals("Lingua"))) {
                value1 = search.getRicercaLingue().get(0);
                value2 = search.getRicercaMansioni().get(0);

                query = "SELECT DISTINCT lingueconosciute.idlavoratore\n" +
                        "FROM lingueconosciute\n LEFT JOIN lavoro on lingueconosciute.idlavoratore = lavoro.idlavoratore\n" +
                        "WHERE lingua = ?\n" +
                        "OR (mansione_1 = ? OR mansione_2 = ?)\n" +
                        "ORDER BY lingueconosciute.idlavoratore ASC";

            } else if ((tipo1.equals("Mansione") && tipo2.equals("LuogoRes")) || (tipo1.equals("LuogoRes") && tipo2.equals("Mansione"))) {
                value1 = search.getRicercaLuoghiResidenza().get(0);
                value2 = search.getRicercaMansioni().get(0);

                query = "SELECT DISTINCT lavoratore.idlavoratore\n" +
                        "FROM lavoratore LEFT JOIN lavoro on lavoratore.idlavoratore = lavoro.idlavoratore\n" +
                        "WHERE paese = ?\n" +
                        "OR (mansione_1 = ? OR mansione_2 = ?)\n" +
                        "ORDER BY lavoratore.idlavoratore ASC;";

            } else if ((tipo1.equals("Mansione") && tipo2.equals("Patente")) || (tipo1.equals("Patente") && tipo2.equals("Mansione"))) {
                value1 = search.getRicercaPatenti().get(0);
                value2 = search.getRicercaMansioni().get(0);

                query = "SELECT DISTINCT lavoratore.idlavoratore\n" +
                        "FROM lavoratore LEFT JOIN lavoro on lavoratore.idlavoratore = lavoro.idlavoratore\n" +
                        "WHERE patente = ?\n" +
                        "OR (mansione_1 = ? OR mansione_2 = ?)\n" +
                        "ORDER BY lavoratore.idlavoratore ASC;";


            } else if ((tipo1.equals("Mansione") && tipo2.equals("Automunito")) || (tipo1.equals("Automunito") && tipo2.equals("Mansione"))) {
                value1 = search.getRicercaAutomuniti().get(0);
                value2 = search.getRicercaMansioni().get(0);

                query = "SELECT DISTINCT lavoratore.idlavoratore\n" +
                        "FROM lavoratore LEFT JOIN lavoro on lavoratore.idlavoratore = lavoro.idlavoratore\n" +
                        "WHERE automunito = ?\n" +
                        "OR (mansione_1 = ? OR mansione_2 = ?)\n" +
                        "ORDER BY lavoratore.idlavoratore ASC;";

            }

            try {
                PreparedStatement statement = connectDB.prepareStatement(query);
                statement.setString(1, value1);
                statement.setString(2, value2);
                statement.setString(3, value2);
                ResultSet res = statement.executeQuery();

                while (res.next()) {
                    elencoId.add(new String(res.getString("idLavoratore")));
                }

                return elencoId;

            } catch (SQLException e) {
                Logger log = Logger.getLogger(RicercaDaoImpl.class.getName());
                log.log(Level.SEVERE, e.getMessage(), e);
            }
        }
        /* LAVORATORE - LINGUA  - LUOGO DI RESIDENZA - PATENTE - AUTOMUNITO*/
        else {
            // Doppi
            if (tipo1.equals("Lavoratore") && tipo2.equals("Lavoratore")) {
                value1 = search.getRicercaLavoratori().get(0);
                value2 = search.getRicercaLavoratori().get(1);

                query = "SELECT DISTINCT idlavoratore\n" +
                        "FROM lavoratore\n" +
                        "WHERE idlavoratore = ? \n" +
                        "  OR idlavoratore = ? \n" +
                        "ORDER BY idlavoratore ASC;\n";

            } else if (tipo1.equals("Lingua") && tipo2.equals("Lingua")) {
                value1 = search.getRicercaLingue().get(0);
                value2 = search.getRicercaLingue().get(1);

                query = "SELECT DISTINCT lingueconosciute.idlavoratore\n" +
                        "FROM lingueconosciute\n" +
                        "JOIN lavoratore on lingueconosciute.idlavoratore = lavoratore.idlavoratore\n" +
                        "WHERE lingua = ?\n" +
                        "  OR lingua = ?\n" +
                        "ORDER BY idlavoratore ASC;\n";
            } else if (tipo1.equals("LuogoRes") && tipo2.equals("LuogoRes")) {
                value1 = search.getRicercaLuoghiResidenza().get(0);
                value2 = search.getRicercaLuoghiResidenza().get(1);

                query = "SELECT DISTINCT idlavoratore\n" +
                        "FROM lavoratore\n" +
                        "WHERE paese = ? \n" +
                        "  OR paese = ? \n" +
                        "ORDER BY idlavoratore ASC;\n";
            } else if (tipo1.equals("Patente") && tipo2.equals("Patente")) {
                value1 = search.getRicercaPatenti().get(0);
                value2 = search.getRicercaPatenti().get(1);

                query = "SELECT DISTINCT idlavoratore\n" +
                        "FROM lavoratore\n" +
                        "WHERE patente = ? \n" +
                        "  OR patente = ? \n" +
                        "ORDER BY idlavoratore ASC;\n";
            } else if (tipo1.equals("Automunito") && tipo2.equals("Automunito")) {
                value1 = search.getRicercaAutomuniti().get(0);
                value2 = search.getRicercaAutomuniti().get(1);

                query = "SELECT DISTINCT idlavoratore\n" +
                        "FROM lavoratore\n" +
                        "WHERE automunito = ? \n" +
                        "  OR automunito = ? \n" +
                        "ORDER BY idlavoratore ASC;\n";
            }
            // Lavoratore
            else if ((tipo1.equals("Lavoratore") && tipo2.equals("Lingua")) || (tipo1.equals("Lingua") && tipo2.equals("Lavoratore"))) {
                value1 = search.getRicercaLavoratori().get(0);
                value2 = search.getRicercaLingue().get(0);

                query = "SELECT DISTINCT lingueconosciute.idlavoratore\n" +
                        "FROM lingueconosciute\n" +
                        "JOIN lavoratore on lingueconosciute.idlavoratore = lavoratore.idlavoratore\n" +
                        "WHERE lavoratore.idlavoratore = ?\n" +
                        "  OR lingua = ?\n" +
                        "ORDER BY idlavoratore ASC;\n";

            } else if ((tipo1.equals("Lavoratore") && tipo2.equals("LuogoRes")) || (tipo1.equals("LuogoRes") && tipo2.equals("Lavoratore"))) {
                value1 = search.getRicercaLavoratori().get(0);
                value2 = search.getRicercaLuoghiResidenza().get(0);

                query = "SELECT DISTINCT idlavoratore\n" +
                        "FROM lavoratore\n" +
                        "WHERE idlavoratore = ?\n" +
                        "   OR paese = ?\n" +
                        "ORDER BY idlavoratore ASC;\n";

            } else if ((tipo1.equals("Lavoratore") && tipo2.equals("Patente")) || (tipo1.equals("Patente") && tipo2.equals("Lavoratore"))) {
                value1 = search.getRicercaLavoratori().get(0);
                value2 = search.getRicercaPatenti().get(0);

                query = "SELECT DISTINCT idlavoratore\n" +
                        "FROM lavoratore\n" +
                        "WHERE idlavoratore = ?\n" +
                        "   OR patente = ?\n" +
                        "ORDER BY idlavoratore ASC;\n";

            } else if ((tipo1.equals("Lavoratore") && tipo2.equals("Automunito")) || (tipo1.equals("Automunito") && tipo2.equals("Lavoratore"))) {
                value1 = search.getRicercaLavoratori().get(0);
                value2 = search.getRicercaAutomuniti().get(0);

                query = "SELECT DISTINCT idlavoratore\n" +
                        "FROM lavoratore\n" +
                        "WHERE idlavoratore = ?\n" +
                        "   OR automunito = ?\n" +
                        "ORDER BY idlavoratore ASC;\n";

            }
            // Lingua
            else if ((tipo1.equals("Lingua") && tipo2.equals("LuogoRes")) || tipo1.equals("LuogoRes") && tipo2.equals("Lingua")) {
                value1 = search.getRicercaLingue().get(0);
                value2 = search.getRicercaLuoghiResidenza().get(0);

                query = "SELECT DISTINCT lingueconosciute.idlavoratore\n" +
                        "FROM lingueconosciute\n" +
                        "JOIN lavoratore on lingueconosciute.idlavoratore = lavoratore.idlavoratore\n" +
                        "WHERE lingua = ? \n" +
                        "  OR lavoratore.paese = ? \n" +
                        "ORDER BY idlavoratore ASC;";


            } else if ((tipo1.equals("Lingua") && tipo2.equals("Patente")) || (tipo1.equals("Patente") && tipo2.equals("Lingua"))) {
                value1 = search.getRicercaLingue().get(0);
                value2 = search.getRicercaPatenti().get(0);

                query = "SELECT DISTINCT lingueconosciute.idlavoratore\n" +
                        "FROM lingueconosciute\n" +
                        "JOIN lavoratore on lingueconosciute.idlavoratore = lavoratore.idlavoratore\n" +
                        "WHERE lingua = ? \n" +
                        "  OR lavoratore.patente = ? \n" +
                        "ORDER BY idlavoratore ASC;";

            } else if ((tipo1.equals("Lingua") && tipo2.equals("Automunito")) || (tipo1.equals("Automunito") && tipo2.equals("Lingua"))) {
                value1 = search.getRicercaLingue().get(0);
                value2 = search.getRicercaAutomuniti().get(0);

                query = "SELECT DISTINCT lingueconosciute.idlavoratore\n" +
                        "FROM lingueconosciute\n" +
                        "JOIN lavoratore on lingueconosciute.idlavoratore = lavoratore.idlavoratore\n" +
                        "WHERE lingua = ? \n" +
                        "  OR lavoratore.automunito = ? \n" +
                        "ORDER BY idlavoratore ASC;";

            }

            // Luogo residenza
            else if ((tipo1.equals("LuogoRes") && tipo2.equals("Patente")) || (tipo1.equals("Patente") && tipo2.equals("LuogoRes"))) {
                value1 = search.getRicercaLuoghiResidenza().get(0);
                value2 = search.getRicercaPatenti().get(0);

                query = "SELECT DISTINCT idlavoratore\n" +
                        "FROM lavoratore\n" +
                        "WHERE paese = ?\n" +
                        "   OR patente = 'Non automunito'\n" +
                        "ORDER BY idlavoratore ASC;";

            } else if ((tipo1.equals("LuogoRes") && tipo2.equals("Automunito")) || (tipo1.equals("Automunito") && tipo2.equals("LuogoRes"))) {
                value1 = search.getRicercaLuoghiResidenza().get(0);
                value2 = search.getRicercaAutomuniti().get(0);

                query = "SELECT DISTINCT idlavoratore\n" +
                        "FROM lavoratore\n" +
                        "WHERE paese = ?\n" +
                        "   OR automunito = 'Non automunito'\n" +
                        "ORDER BY idlavoratore ASC;";

            }

            // Patente
            else if ((tipo1.equals("Patente") && tipo2.equals("Automunito")) || (tipo1.equals("Automunito") && tipo2.equals("Patente"))) {
                value1 = search.getRicercaPatenti().get(0);
                value2 = search.getRicercaAutomuniti().get(0);

                query = "SELECT DISTINCT idlavoratore\n" +
                        "FROM lavoratore\n" +
                        "WHERE patente = ?\n" +
                        "   OR automunito = 'Non automunito'\n" +
                        "ORDER BY idlavoratore ASC;";

            }


            try {
                PreparedStatement statement = connectDB.prepareStatement(query);
                statement.setString(1, value1);
                statement.setString(2, value2);
                ResultSet res = statement.executeQuery();

                while (res.next()) {
                    elencoId.add(new String(res.getString("idLavoratore")));
                }

                return elencoId;

            } catch (SQLException e) {
                Logger log = Logger.getLogger(RicercaDaoImpl.class.getName());
                log.log(Level.SEVERE, e.getMessage(), e);
            }

        }

        return null;
    }


}
