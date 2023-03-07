package sample.controller;

import sample.Main;
import sample.model.dao.LavoratoreDaoImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.model.Lavoratore;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AggiornamentoLavori implements Initializable {
    @FXML
    private MenuButton menuButtonLavoratori;
    @FXML
    private Button inserimento;
    @FXML
    private Button modifica;
    @FXML
    private Button cancella;
    @FXML
    private Label lavoratoreScelto;
    @FXML
    private Label errSelezioneLavoratore;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Inseriamo tutti i lavoratori nel menu
        LavoratoreDaoImpl lavoratoreDao = new LavoratoreDaoImpl();
        setMenu(lavoratoreDao.getAllLavoratori());


    }

    public void tornaIndietro(ActionEvent actionEvent) throws IOException {
        Main nuovaScena = new Main();
        nuovaScena.changeScene("view/HomePage.fxml");
    }



    public void setMenu(List<Lavoratore> listaLavoratori) {
        if( listaLavoratori.isEmpty() ){
            MenuItem menuItem = new MenuItem("Lista vuota");
            menuButtonLavoratori.getItems().add(menuItem);
        }

        for (int i = 0; i < listaLavoratori.size(); i++) {
            MenuItem menuItem = new MenuItem(listaLavoratori.get(i).getCognome() + " " + listaLavoratori.get(i).getNome() + " " + listaLavoratori.get(i).getIdLavoratore());
            menuButtonLavoratori.getItems().add(menuItem);

            String idLav = listaLavoratori.get(i).getIdLavoratore();

            menuItem.setOnAction(event -> {
                errSelezioneLavoratore.setText("");
                lavoratoreScelto.setText(idLav);
            });
        }
    }



    public void inserisciLavoro(ActionEvent event) throws IOException {
        if (!lavoratoreScelto.getText().isBlank()) {

            /* CHIAMO LA PAGINA SUCCESSIVA PASSANDO L'ID DEL LAVORATORE*/
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/InserimentoLavoro.fxml"));
            Parent root = loader.load();

            InserimentoLavoro inserimentoLavoroContr = loader.getController();
            inserimentoLavoroContr.visualizzaLavoratore(lavoratoreScelto.getText());

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } else {
            errSelezioneLavoratore.setText("Non hai selezionato nessun lavoratore");
        }
    }

    public void modificaLavoro(ActionEvent event) throws IOException {
        if (!lavoratoreScelto.getText().isBlank()) {

            /* CHIAMO LA PAGINA SUCCESSIVA PASSANDO L'ID DEL LAVORATORE*/
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModificaLavoro.fxml"));
            Parent root = loader.load();

            ModificaLavoro modificaLavoroContr = loader.getController();
            modificaLavoroContr.visualizzaLavoratore(lavoratoreScelto.getText());

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } else {
            errSelezioneLavoratore.setText("Non hai selezionato nessun lavoratore");
        }
    }

    public void cancellaLavoro(ActionEvent event) throws IOException {
        if (!lavoratoreScelto.getText().isBlank()) {

            /* CHIAMO LA PAGINA SUCCESSIVA PASSANDO L'ID DEL LAVORATORE*/
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/CancellazioneLavoro.fxml"));
            Parent root = loader.load();

            CancellazioneLavoro cancellazioneLavoroContr = loader.getController();
            cancellazioneLavoroContr.visualizzaLavoratore(lavoratoreScelto.getText());

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } else {
            errSelezioneLavoratore.setText("Non hai selezionato nessun lavoratore");
        }
    }

}
