package sample.controller;

import sample.Main;
import sample.model.dao.LavoroDaoImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import sample.model.Lavoro;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.List;

public class CancellazioneLavoro {

    @FXML
    private MenuButton menuButtonLavori;
    @FXML
    private Label lavoratoreScelto;
    @FXML
    private Button elimina;
    @FXML
    private Button ripensa;
    @FXML
    private Button goBack;
    @FXML
    private Text testo1;
    @FXML
    private Text testo2;
    @FXML
    private Text domanda;


    private int chiave;
    private LavoroDaoImpl lavoroDao = new LavoroDaoImpl();


    public void visualizzaLavoratore (String idLavoratore){
        lavoratoreScelto.setText(idLavoratore);

        // Inseriamo tutti i lavori nel menu
        setMenu(lavoroDao.getAllLavori(idLavoratore));
    }

    public void setMenu(List<Lavoro> listaLavori) {
        if( listaLavori.isEmpty() ){
            MenuItem menuItem = new MenuItem("Lista vuota");
            menuButtonLavori.getItems().add(menuItem);
        }

        for (int i = 0; i < listaLavori.size(); i++) {
            MenuItem menuItem = new MenuItem(listaLavori.get(i).getNomeLavoro() + " " + listaLavori.get(i).getDataInizio() + " " + listaLavori.get(i).getDataFine());
            menuButtonLavori.getItems().add(menuItem);

            int chiaveTemp = listaLavori.get(i).getChiave();

            menuItem.setOnAction(event -> {
                chiave = chiaveTemp;

                // Attiva i bottoni per l'uscita (disattiva quelli della hompage)
                menuButtonLavori.setDisable(true);
                testo1.setDisable(true);
                testo2.setDisable(true);
                lavoratoreScelto.setDisable(true);
                domanda.setDisable(false);
                elimina.setDisable(false);
                ripensa.setDisable(false);

                // Rendi visibili i bottoni per l'uscita
                menuButtonLavori.setOpacity(0);
                testo1.setOpacity(0);
                testo2.setOpacity(0);
                lavoratoreScelto.setOpacity(0);
                domanda.setOpacity(1.0);
                elimina.setOpacity(1.0);
                ripensa.setOpacity(1.0);
            });
        }
    }

    public void elimina(ActionEvent actionEvent) throws IOException {
        lavoroDao.deleteLavoro(chiave);
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
