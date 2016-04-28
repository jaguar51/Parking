package me.academeg.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    @FXML
    void rentBox() {
        Parent root;
        try {
//            root = FXMLLoader.load(getClass().getResource("/fxml/rent_box.fxml"));
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/rent_box.fxml"));
            root = loader.load();
//            loader.getController() можно добавить ссылку на этот контроллер и обмениваться инфой
            Stage stage = new Stage();
            stage.setTitle("Аренда бокса");
            stage.setScene(new Scene(root, 450, 450));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
