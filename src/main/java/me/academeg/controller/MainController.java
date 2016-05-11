package me.academeg.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;
import me.academeg.API.ContentProvider;

import java.io.IOException;
import java.sql.SQLException;

public class MainController {

    @FXML private void rentBox() {
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/rent_box.fxml"));
            root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Аренда бокса");
            stage.setScene(new Scene(root, 450, 450));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML private void parkCar() {
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/park_car.fxml"));
            root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Постановка авто на стоянку");
            stage.setScene(new Scene(root, 450, 450));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML private void takeAutoClick() {
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/take_auto.fxml"));
            root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Забрать автомобиль");
            stage.setScene(new Scene(root, 450, 450));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML private void showFreeLotAlert() {
        String msg;
        try (ContentProvider contentProvider = new ContentProvider()) {
            msg = Integer.toString(contentProvider.getFreeParkingLotCount());
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Количество свободных боксов");
        alert.setHeaderText(null);
        alert.setContentText(msg);
//        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
//        stage.getIcons().add(new Image(getClass().getResource("/icon.png").toString()));
        alert.showAndWait();
    }
}
