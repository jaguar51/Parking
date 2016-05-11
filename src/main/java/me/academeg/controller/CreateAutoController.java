package me.academeg.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import me.academeg.API.Auto;
import me.academeg.API.Client;
import me.academeg.API.ContentProvider;
import me.academeg.components.AutoCompleteComboBoxListener;
import me.academeg.stringConverters.ClientsStringConverter;

import java.sql.SQLException;
import java.util.ArrayList;

public class CreateAutoController {

    private UpdateCallback callback;

    @FXML private ComboBox<Client> clientCB;
    @FXML private TextField plateTF;
    @FXML private TextField brandTF;
    @FXML private TextField colorTF;
    @FXML private TextArea additionalInfoTA;
    @FXML private Button okBTN;

    @FXML private void initialize() {
        try (ContentProvider provider = new ContentProvider()) {
            ArrayList<Client> clients = provider.getClients();
            if (clients.size() > 0) {
                clientCB.getItems().setAll(clients);
                new AutoCompleteComboBoxListener<>(clientCB);
                clientCB.setConverter(new ClientsStringConverter());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML private void createAuto() {
        Client client = clientCB.getValue();
        Auto auto = new Auto();
        auto.setBrand(brandTF.getText());
        auto.setPlate(plateTF.getText());
        auto.setColor(colorTF.getText());
        auto.setAddInf(additionalInfoTA.getText());

        if (auto.getPlate().length() == 0 || auto.getBrand().length() == 0 || auto.getColor().length() == 0
                || client == null) {
            showError("Заполните поля");
            return;
        }

        if (auto.getAddInf().length() == 0) {
            auto.setAddInf(null);
        }

        try (ContentProvider provider = new ContentProvider()) {
            provider.createAuto(auto, client);
        } catch (SQLException e) {
            showError(e.getMessage());
            e.printStackTrace();
            return;
        }

        if (callback != null) {
            callback.update();
        }

        Stage stage = (Stage) okBTN.getScene().getWindow();
        stage.close();
    }

    public void setCallback(UpdateCallback callback) {
        this.callback = callback;
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
