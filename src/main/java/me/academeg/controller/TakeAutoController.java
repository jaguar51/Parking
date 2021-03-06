package me.academeg.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import me.academeg.API.AutoOnPark;
import me.academeg.API.Client;
import me.academeg.API.ContentProvider;
import me.academeg.components.AutoCompleteComboBoxListener;
import me.academeg.stringConverters.ClientsStringConverter;
import tornadofx.control.DateTimePicker;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TakeAutoController {

    @FXML private ComboBox<Client> clientCB;
    @FXML private ComboBox<AutoOnPark> autoCB;
    @FXML private DateTimePicker endDateDP;
    @FXML private Button okBTN;

    @FXML private void initialize() {
        endDateDP.setDateTimeValue(LocalDateTime.now());

        try (ContentProvider contentProvider = new ContentProvider()) {
            ArrayList<Client> clients = contentProvider.getClients();
            if (clients != null && clients.size() > 0) {
                clientCB.getItems().setAll(clients);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        initViews();
    }

    private void initViews() {
        autoCB.setDisable(true);
        new AutoCompleteComboBoxListener<>(clientCB);
        clientCB.setConverter(new ClientsStringConverter());
        clientCB.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try (ContentProvider provider = new ContentProvider()) {
                if (newValue != null) {
                    autoCB.getItems().setAll(provider.getAutoPark(newValue));
                    autoCB.setDisable(false);
                } else {
                    autoCB.getItems().clear();
                    autoCB.setDisable(true);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            autoCB.setDisable(false);
        });

        new AutoCompleteComboBoxListener<>(autoCB);
        autoCB.setConverter(new StringConverter<AutoOnPark>() {
            private Map<String, AutoOnPark> map = new HashMap<>();

            @Override
            public String toString(AutoOnPark object) {
                if (object != null) {
                    String key = String.format("%s %s %s", object.getAuto().getBrand(), object.getAuto().getPlate(),
                            object.getAuto().getColor());
                    map.put(key, object);
                    return key;
                }
                return "";
            }

            @Override
            public AutoOnPark fromString(String string) {
                if (!map.containsKey(string)) {
                    return null;
                }
                return map.get(string);
            }
        });
    }

    @FXML private void takeCarClick() {
        Client client = clientCB.getValue();
        AutoOnPark auto = autoCB.getValue();
        LocalDateTime dateTime = endDateDP.getDateTimeValue();

        if (client == null || auto == null) {
            showError("Заполните поля");
            return;
        }

        try (ContentProvider provider = new ContentProvider()) {
            provider.takeAuto(auto, dateTime);
        } catch (SQLException e) {
            showError(e.getMessage());
            e.printStackTrace();
            return;
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
