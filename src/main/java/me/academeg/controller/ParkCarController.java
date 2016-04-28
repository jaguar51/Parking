package me.academeg.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.util.StringConverter;
import me.academeg.API.Client;
import me.academeg.API.ContentProvider;
import me.academeg.Components.AutoCompleteComboBoxListener;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ParkCarController {

    private ArrayList<Client> clients;

    @FXML private ComboBox<Client> clientCB;
    @FXML private ComboBox lotCB;
    @FXML private ComboBox autoCB;
    @FXML private DatePicker startDateDP;
    @FXML private Button okBTN;

    @FXML private void initialize() {

        ContentProvider contentProvider = new ContentProvider();
        try {
            contentProvider.open();
            clients = contentProvider.getClients();
            if (clients.size() > 0) {
                clientCB.getItems().addAll(clients);
            }
            contentProvider.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        initViews();
    }

    private void initViews() {
        lotCB.setDisable(true);
        autoCB.setDisable(true);
        new AutoCompleteComboBoxListener<>(clientCB);
        clientCB.setConverter(new StringConverter<Client>() {
            private Map<String, Client> map = new HashMap<>();

            @Override
            public String toString(Client object) {
                if (object != null) {
                    String key = String.format("%s %s %s", object.getSurname(), object.getName(),
                            object.getPatronymic());
                    map.put(key, object);
                    return key;
                }
                return "";
            }

            @Override
            public Client fromString(String string) {
                if (!map.containsKey(string)) {
                    return null;
                }
                return map.get(string);
            }
        });

        clientCB.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Client>() {
            @Override
            public void changed(ObservableValue<? extends Client> observable, Client oldValue, Client newValue) {
                System.out.println(newValue.toString());
            }
        });
    }

    @FXML private void parkCarClick() {

    }

}
