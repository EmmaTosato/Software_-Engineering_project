package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.text.Text;
import sample.Main;
import sample.model.Dipendente;

import java.io.IOException;


public class HomePage  {
    @FXML
    private Button registra;
    @FXML
    private Button aggiornamento;
    @FXML
    private Button ricerca;

    // Per l'uscita
    @FXML
    private Button logout;
    @FXML
    private Button siEsci;
    @FXML
    private Button noResta;
    @FXML
    private Text domanda;



    /*OPZIONI DI AZIONE DISPONIBILI */
    public void registrazione(ActionEvent actionEvent) throws IOException {
        Main nuovaScena = new Main();
        nuovaScena.changeScene("view/AggiornamentoLavoratori.fxml");

    }

    public void aggioramento(ActionEvent actionEvent) throws IOException {
        Main nuovaScena = new Main();
        nuovaScena.changeScene("view/AggiornamentoLavori.fxml");
    }

    public void ricerca(ActionEvent actionEvent) throws IOException {
        Main nuovaScena = new Main();
        nuovaScena.changeScene("view/SceltaRicerca.fxml");
    }


    /* USCITA */
    public void chiediUscita(ActionEvent actionEvent) {
        // Attiva i bottoni per l'uscita (disattiva quelli della hompage)
        siEsci.setDisable(false);
        noResta.setDisable(false);
        domanda.setDisable(false);
        registra.setDisable(true);
        aggiornamento.setDisable(true);
        ricerca.setDisable(true);

        // Rendi visibili i bottoni per l'uscita
        siEsci.setOpacity(1.0);
        noResta.setOpacity(1.0);
        domanda.setOpacity(1.0);
        registra.setOpacity(0);
        aggiornamento.setOpacity(0);
        ricerca.setOpacity(0);

    }

    public void uscita(ActionEvent actionEvent) throws IOException {
        Main nuovaScena = new Main();
        nuovaScena.changeScene("view/Login.fxml");
    }

    public void rimani(ActionEvent actionEvent) {
        // Disattiva i bottoni per l'uscita (attiva quelli della hompage)
        siEsci.setDisable(true);
        noResta.setDisable(true);
        domanda.setDisable(true);
        registra.setDisable(false);
        aggiornamento.setDisable(false);
        ricerca.setDisable(false);

        // Rendi trasparenti i bottoni per l'uscita
        siEsci.setOpacity(0);
        noResta.setOpacity(0);
        domanda.setOpacity(0);
        registra.setOpacity(1.0);
        aggiornamento.setOpacity(1.0);
        ricerca.setOpacity(1.0);

    }



}
