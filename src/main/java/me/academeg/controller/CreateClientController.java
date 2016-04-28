package me.academeg.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import me.academeg.API.Client;
import me.academeg.API.ContentProvider;

import java.sql.SQLException;

public class CreateClientController {

    @FXML private TextField surnameTF;
    @FXML private TextField nameTF;
    @FXML private TextField patronymicTF;
    @FXML private TextField phoneTF;
    @FXML private Button okBTN;

    private RentBoxController rentBoxController;

    public void setRentBoxController(RentBoxController rentBoxController) {
        this.rentBoxController = rentBoxController;
    }

    @FXML private void createClient() {
        Client client = new Client();
        client.setSurname(surnameTF.getText());
        client.setName(nameTF.getText());
        client.setPatronymic(patronymicTF.getText());
        client.setPhone(phoneTF.getText());
        if (client.getSurname().length() == 0 || client.getName().length() == 0 || client.getPhone().length() == 0) {
            showError("Заполните следующие поля: Фамилия, имя, телефон");
            return;
        }

        ContentProvider contentProvider = new ContentProvider();
        try {
            contentProvider.open();
            contentProvider.createClient(client);
            contentProvider.close();
        } catch (SQLException e) {
            e.printStackTrace();
            showError(e.getMessage());
            return;
        }

        if (rentBoxController != null) {
            rentBoxController.updateClientList();
        }
        Stage stage = (Stage) okBTN.getScene().getWindow();
        stage.close();
    }

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(msg);
//        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
//        stage.getIcons().add(new Image(getClass().getResource("/icon.png").toString()));
        alert.showAndWait();
    }
}
