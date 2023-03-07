package sample.controller;

import sample.Main;
import sample.model.dao.LavoratoreDaoImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;

public class CancellazioneLavoratore {


    @FXML
    private Button elimina;
    @FXML
    private Button ripensa;
    @FXML
    private Button goBack;
    @FXML
    private Label domanda;

    private String id;
    private LavoratoreDaoImpl lavoratoreDao = new LavoratoreDaoImpl();



    public void visualizzaLavoratore (String idLavoratore){
        domanda.setText("Sei sicuro di voler eliminare il lavoratore " + idLavoratore + " ?\n" + "Eliminerai anche tutti i dati ad esso associati, compresi i lavori svolti.");
        id = idLavoratore;

    }

    public void elimina(ActionEvent actionEvent) throws IOException {
        lavoratoreDao.deleteLavoratore(id);
        Main nuovaScena = new Main();
        nuovaScena.changeScene("view/HomePage.fxml");

    }


    public void ripensa(ActionEvent actionEvent) throws IOException {
        Main nuovaScena = new Main();
        nuovaScena.changeScene("view/HomePage.fxml");
    }


    public void tornaIndietro(ActionEvent actionEvent) throws IOException {
        Main nuovaScena = new Main();
        nuovaScena.changeScene("view/HomePage.fxml");
    }



}
