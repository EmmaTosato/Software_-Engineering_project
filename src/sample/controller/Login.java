package sample.controller;

import sample.Main;
import sample.model.dao.DipendenteDaoImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sample.model.Dipendente;

import java.io.IOException;


public class Login {
    @FXML
    private Label errUsername;
    @FXML
    private Label errPassword;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Button entra;


    public void accesso(ActionEvent actionEvent) throws IOException {
        Main nuovaScena = new Main();

        int control = DipendenteDaoImpl.checkCredenziali(username.getText(), password.getText());

        if(control == 1){
            nuovaScena.changeScene("view/HomePage.fxml");
        }
        else{
            errUsername.setText("Username errato: reinserire");
            errPassword.setText("Password errata: reinserire");
            username.setText("");
            password.setText("");
        }
    }

}
