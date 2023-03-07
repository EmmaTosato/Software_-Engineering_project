package sample.controller;

import sample.Main;
import sample.model.dao.LavoratoreDaoImpl;
import sample.model.dao.LavoroDaoImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.model.*;
import sample.utils.DatabaseConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SceltaRicerca implements Initializable {

    @FXML
    private Button goBack;
    @FXML
    private ComboBox lavoratori;
    @FXML
    private ComboBox lingue;
    @FXML
    private ComboBox periodoInizio;
    @FXML
    private ComboBox periodoFine;
    @FXML
    private ComboBox mansioni;
    @FXML
    private ComboBox luogoResidenza;
    @FXML
    private ComboBox automunito;
    @FXML
    private ComboBox patente;
    @FXML
    private Button AND;
    @FXML
    private Button OR;
    @FXML
    private  Button ricerca;
    @FXML
    private  Button inserisciParametro;
    @FXML
    private  Button clear;

    @FXML
    private TextArea query;
    @FXML
    private  Label errInserisciParametro;
    @FXML
    private Label errDate;
    @FXML
    private Label errNumParametri;



    /* VARIABILI PRIVATE */
    private Ricerca search = new Ricerca();
    private String queryVariable = "";


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setComboBox();


    }


    public void setComboBox(){
        LavoratoreDaoImpl lavoratoreDao = new LavoratoreDaoImpl();
        LavoroDaoImpl lavoroDao = new LavoroDaoImpl();

        /* POPOLAMENTO COMBOX CON TABELLE DB VARIABILI*/
        // Lavoratori
        List<Lavoratore> listaLavoratori = lavoratoreDao.getAllLavoratori();
        for(int i = 0; i < listaLavoratori.size(); i++){
            String compl = listaLavoratori.get(i).getIdLavoratore() + " " + listaLavoratori.get(i).getCognome() + " " + listaLavoratori.get(i).getNome()  ;
            lavoratori.getItems().addAll(compl);
        };

        // Luoghi di residenza
        luogoResidenza.getItems().addAll(lavoratoreDao.getAllLuoghiResidenza());

        // Mansioni
        mansioni.getItems().addAll(lavoroDao.getAllMansioni());

        // Periodi
        periodoInizio.getItems().addAll(lavoratoreDao.getAllDateInizio());
        periodoFine.getItems().addAll(lavoratoreDao.getAllDateFine());


        /* POPOLAMENTO AUTOMUNITO */
        automunito.getItems().add("Automunito");
        automunito.getItems().add("Non automunito");


        /* POPOLAMENTO COMBOX CON TABELLE DB FISSE */
        // Connessione al database
        DatabaseConnection connetNow = new DatabaseConnection();
        Connection connectDB = connetNow.openConnection();

        // Queries
        String queryLingue = "SELECT * FROM LINGUE ORDER BY lingua ASC";
        String queryPatente = "SELECT * FROM PATENTE ";

        try {
            // Per visualizzare i dati a lista
            ResultSet rs1 = connectDB.createStatement().executeQuery(queryLingue);
            ResultSet rs2 = connectDB.createStatement().executeQuery(queryPatente);
            ObservableList data1 = FXCollections.observableArrayList();
            ObservableList data2 = FXCollections.observableArrayList();


            while(rs1.next()){
                data1.add(new String(rs1.getString("lingua")));
            }
            lingue.getItems().addAll(data1);


            while(rs2.next()){
                data2.add(new String(rs2.getString("patente")));
            }
            patente.getItems().addAll(data2);




        } catch (SQLException e) {
            Logger.getLogger(SceltaRicerca.class.getName()).log(Level.SEVERE,null, e);
            e.printStackTrace();
        }
    }


    public void getStringa(ActionEvent event)throws IOException {
        int lunghezza = search.getTipoParametro().size();

        // Altri controlli
        if( lavoratori.getSelectionModel().isEmpty() && lingue.getSelectionModel().isEmpty() && periodoInizio.getSelectionModel().isEmpty() && periodoFine.getSelectionModel().isEmpty() &&
                mansioni.getSelectionModel().isEmpty()   && luogoResidenza.getSelectionModel().isEmpty() && automunito.getSelectionModel().isEmpty() &&
                patente.getSelectionModel().isEmpty()){
            errInserisciParametro.setText("Non hai selezionato nessun parametro");
        }
        else if (lavoratori.getSelectionModel().isEmpty() && lingue.getSelectionModel().isEmpty()  && mansioni.getSelectionModel().isEmpty()  &&
                luogoResidenza.getSelectionModel().isEmpty() && automunito.getSelectionModel().isEmpty() && patente.getSelectionModel().isEmpty() &&
                ((periodoInizio.getSelectionModel().isEmpty() || periodoFine.getSelectionModel().isEmpty()))){
            errDate.setText("Devi selezionare ENTRAMBI i campi");
        }
        else if (lunghezza >= 3){
            errNumParametri.setText("Non puoi più inserire condizioni di ricerca. Premi il tasto Ricerca");
        }
        else if (lunghezza > 0 && search.getTipoParametro().get(lunghezza -1 ) != "AND" && search.getTipoParametro().get(lunghezza -1 ) != "OR") {
            errInserisciParametro.setText("Non puoi selezionare due parametri di seguito senza inserire AND o OR");
        }
        // Se tutto è giusto
        else{
            // Tolgo l'etichetta dell'errore (funziona solo c'è)
            errInserisciParametro.setText("");
            errDate.setText("");

            if(!lavoratori.getSelectionModel().isEmpty()){
                queryVariable = queryVariable + lavoratori.getSelectionModel().getSelectedItem().toString();
                query.setText(queryVariable);

                // Aggiungo all'array il tipo di selezione
                search.setTipoParametro("Lavoratore");

                // Setto l'oggetto (solo l'ID Lavoratore)
                String [] temp = lavoratori.getSelectionModel().getSelectedItem().toString().split("\\s+");
                search.setRicercaLavoratori(temp[0]);

            }
            else if(!lingue.getSelectionModel().isEmpty()){
                queryVariable = queryVariable + lingue.getSelectionModel().getSelectedItem().toString();
                query.setText(queryVariable);

                // Aggiungo all'array il tipo di selezione
                search.setTipoParametro("Lingua");

                // Setto l'oggetto
                search.setRicercaLingue(lingue.getSelectionModel().getSelectedItem().toString());
            }
            else if(!mansioni.getSelectionModel().isEmpty()){
                queryVariable = queryVariable + mansioni.getSelectionModel().getSelectedItem().toString();
                query.setText(queryVariable);

                // Aggiungo all'array il tipo di selezione
                search.setTipoParametro("Mansione");

                // Setto l'oggetto
                search.setRicercaMansioni(mansioni.getSelectionModel().getSelectedItem().toString());

            }
            else if(!luogoResidenza.getSelectionModel().isEmpty()){
                queryVariable = queryVariable + luogoResidenza.getSelectionModel().getSelectedItem().toString();
                query.setText(queryVariable);

                // Aggiungo all'array il tipo di selezione
                search.setTipoParametro("LuogoRes");

                // Setto l'oggetto
                search.setRicercaLuoghiResidenza(luogoResidenza.getSelectionModel().getSelectedItem().toString());

            }
            else if(!automunito.getSelectionModel().isEmpty()){
                queryVariable = queryVariable + automunito.getSelectionModel().getSelectedItem().toString();
                query.setText(queryVariable);

                // Aggiungo all'array il tipo di selezione
                search.setTipoParametro("Automunito");


                // Setto l'oggetto
                search.setRicercaAutomuniti(automunito.getSelectionModel().getSelectedItem().toString());

            }
            else if(!patente.getSelectionModel().isEmpty()){
                queryVariable = queryVariable + patente.getSelectionModel().getSelectedItem().toString();
                query.setText(queryVariable);

                // Aggiungo all'array il tipo di selezione
                search.setTipoParametro("Patente");


                // Setto l'oggetto
                search.setRicercaPatenti(patente.getSelectionModel().getSelectedItem().toString());

            }
            else if(!periodoInizio.getSelectionModel().isEmpty() && !periodoFine.getSelectionModel().isEmpty()){
                queryVariable = queryVariable + periodoInizio.getSelectionModel().getSelectedItem().toString() + " - "+ periodoFine.getSelectionModel().getSelectedItem().toString();
                query.setText(queryVariable);

                // Aggiungo all'array il tipo di selezione
                search.setTipoParametro("Periodo");


                // Setto l'oggetto
                search.setRicercaPeriodoInizio(periodoInizio.getSelectionModel().getSelectedItem().toString());
                search.setRicercaPeriodoFine(periodoFine.getSelectionModel().getSelectedItem().toString());

            }
        }


    }


    public void tastoAnd(ActionEvent event)throws IOException {
        if( lavoratori.getSelectionModel().isEmpty() && lingue.getSelectionModel().isEmpty() && periodoInizio.getSelectionModel().isEmpty() && periodoFine.getSelectionModel().isEmpty() &&
                mansioni.getSelectionModel().isEmpty()   && luogoResidenza.getSelectionModel().isEmpty() && automunito.getSelectionModel().isEmpty() &&
                patente.getSelectionModel().isEmpty()){
            errInserisciParametro.setText("Non hai selezionato nessun parametro");
        }
        else if(query.getText().isBlank()){
            errInserisciParametro.setText("Non hai selezionato nessun parametro");
        }
        else if(search.getTipoParametro().get(search.getTipoParametro().size() - 1 ) == "AND"){
            errInserisciParametro.setText("Non hai selezionato nessun parametro");
        }
        else if (search.getTipoParametro().size() >= 3){
            errNumParametri.setText("Non puoi più inserire condizioni di ricerca. Premi il tasto Ricerca");
        }
        else{
            // Pulisci i campi
            errInserisciParametro.setText("");
            errDate.setText("");
            lavoratori.getSelectionModel().clearSelection();
            lingue.getSelectionModel().clearSelection();
            periodoInizio.getSelectionModel().clearSelection();
            periodoFine.getSelectionModel().clearSelection();
            mansioni.getSelectionModel().clearSelection();
            luogoResidenza.getSelectionModel().clearSelection();
            automunito.getSelectionModel().clearSelection();
            patente.getSelectionModel().clearSelection();

            // Mostra query sotto
            queryVariable = queryVariable + "  AND  ";
            query.setText(queryVariable);

            // Aggiungo all'array controllo tipologie l'AND
            search.setTipoParametro("AND");
        }

    }


    public void tastoOr(ActionEvent event)throws IOException {
        if( lavoratori.getSelectionModel().isEmpty() && lingue.getSelectionModel().isEmpty() && periodoInizio.getSelectionModel().isEmpty() && periodoFine.getSelectionModel().isEmpty() &&
                mansioni.getSelectionModel().isEmpty()   && luogoResidenza.getSelectionModel().isEmpty() && automunito.getSelectionModel().isEmpty() &&
                patente.getSelectionModel().isEmpty()){
            errInserisciParametro.setText("Non hai selezionato nessun parametro");
        }
        else if(query.getText().isBlank()){
            errInserisciParametro.setText("Non hai selezionato nessun parametro");
        }
        else if(search.getTipoParametro().get(search.getTipoParametro().size() - 1 ) == "OR"){
            errInserisciParametro.setText("Non hai selezionato nessun parametro");
        }
        else if (search.getTipoParametro().size() >= 3){
            errNumParametri.setText("Non puoi più inserire condizioni di ricerca. Premi il tasto Ricerca");
        }

        else{
            // Pulisci i campi
            errInserisciParametro.setText("");
            errDate.setText("");
            lavoratori.getSelectionModel().clearSelection();
            lingue.getSelectionModel().clearSelection();
            periodoInizio.getSelectionModel().clearSelection();
            periodoFine.getSelectionModel().clearSelection();
            mansioni.getSelectionModel().clearSelection();
            luogoResidenza.getSelectionModel().clearSelection();
            automunito.getSelectionModel().clearSelection();
            patente.getSelectionModel().clearSelection();



            // Mostra query sotto
            queryVariable = queryVariable + "  OR  ";
            query.setText(queryVariable);

            // Aggiungo all'array controllo tipologie l'AND
            search.setTipoParametro("OR");
        }
    }

    public void ricerca(ActionEvent event)throws IOException {
        if( lavoratori.getSelectionModel().isEmpty() && lingue.getSelectionModel().isEmpty() && periodoInizio.getSelectionModel().isEmpty() && periodoFine.getSelectionModel().isEmpty() &&
                mansioni.getSelectionModel().isEmpty()   && luogoResidenza.getSelectionModel().isEmpty() && automunito.getSelectionModel().isEmpty() &&
                patente.getSelectionModel().isEmpty() ){
            errInserisciParametro.setText("Non hai selezionato nessun parametro");
        }
        else if (search.getTipoParametro().isEmpty()){
            errInserisciParametro.setText("Non hai selezionato nessun parametro");
        }
        else{
            /* CHIAMO LA PAGINA SUCCESSIVA PASSANDO UN OGGETTO*/
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/VisualizzaRicerca.fxml"));
            Parent root = loader.load();

            VisualizzaRicerca visualizzaRicercaController = loader.getController();
            visualizzaRicercaController.setRicerca(search);

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

    }



    public void tornaIndietro(ActionEvent actionEvent)throws IOException {
        Main nuovaScena = new Main();
        nuovaScena.changeScene("view/HomePage.fxml");
    }


}
