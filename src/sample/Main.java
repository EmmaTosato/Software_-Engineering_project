package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.utils.*;
import java.io.IOException;


public class Main extends Application {
    private static Stage stage;



    @Override
    public void start(Stage firstStage) throws IOException {
        stage = firstStage;
        Parent root = FXMLLoader.load(getClass().getResource("view/Login.fxml"));
        firstStage.setTitle("Servizio per la gestione dei lavoratori stagionali");
        firstStage.setScene(new Scene(root));
        firstStage.show();
    }

    public void changeScene(String fxml) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxml));
        stage.getScene().setRoot(root);
    }


    public static void main(String[] args) {
        launch();

    }


}

