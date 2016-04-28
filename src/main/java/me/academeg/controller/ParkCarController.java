package me.academeg.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import me.academeg.API.*;
import me.academeg.Components.AutoCompleteComboBoxListener;
import tornadofx.control.DateTimePicker;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ParkCarController {

    @FXML private ComboBox<Client> clientCB;
    @FXML private ComboBox<ParkingLot> lotCB;
    @FXML private ComboBox<Auto> autoCB;
    @FXML private DateTimePicker startDateDP;
    @FXML private ComboBox<Employee> employeesCB;
    @FXML private Button okBTN;

    @FXML private void initialize() {

        ContentProvider contentProvider = new ContentProvider();
        try {
            contentProvider.open();
            ArrayList<Client> clients = contentProvider.getClients();
            if (clients.size() > 0) {
                clientCB.getItems().setAll(clients);
            }
            ArrayList<Employee> employees = contentProvider.getEmployees();
            if (employees.size() > 0) {
                employeesCB.getItems().setAll(employees);
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
        startDateDP.setValue(LocalDate.now());
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

        new AutoCompleteComboBoxListener<>(autoCB);
        autoCB.setConverter(new StringConverter<Auto>() {
            private Map<String, Auto> map = new HashMap<>();

            @Override
            public String toString(Auto object) {
                if (object != null) {
                    String key = String.format("%s %s %s", object.getBrand(), object.getPlate(),
                            object.getColor());
                    map.put(key, object);
                    return key;
                }
                return "";
            }

            @Override
            public Auto fromString(String string) {
                if (!map.containsKey(string)) {
                    return null;
                }
                return map.get(string);
            }
        });

        new AutoCompleteComboBoxListener<>(lotCB);
        lotCB.setConverter(new StringConverter<ParkingLot>() {
            private Map<String, ParkingLot> map = new HashMap<>();

            @Override
            public String toString(ParkingLot object) {
                if (object != null) {
                    String key = Integer.toString(object.getId());
                    map.put(key, object);
                    return key;
                }
                return "";
            }

            @Override
            public ParkingLot fromString(String string) {
                if (!map.containsKey(string)) {
                    return null;
                }
                return map.get(string);
            }
        });

        new AutoCompleteComboBoxListener<>(employeesCB);
        employeesCB.setConverter(new StringConverter<Employee>() {
            private Map<String, Employee> map = new HashMap<>();
            @Override
            public String toString(Employee object) {
                if (object != null) {
                    String key = String.format("%s %s %s", object.getSurname(), object.getName(),
                            object.getPatronymic());
                    map.put(key, object);
                    return key;
                }
                return "";
            }

            @Override
            public Employee fromString(String string) {
                if (!map.containsKey(string)) {
                    return null;
                }
                return map.get(string);
            }
        });

        clientCB.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            ContentProvider provider = new ContentProvider();
            try {
                if (newValue != null) {
                    provider.open();
                    lotCB.setValue(null);
                    lotCB.getItems().setAll(provider.getFreeParkingLot(newValue));
                    lotCB.setDisable(false);
                    autoCB.setValue(null);
                    autoCB.getItems().setAll(provider.getAutoForParking(newValue));
                    autoCB.setDisable(false);
                    provider.close();
                } else {
                    lotCB.getItems().clear();
                    lotCB.setDisable(true);
                    autoCB.setDisable(true);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML private void parkCarClick() {
        Client client = clientCB.getValue();
        Employee employee = employeesCB.getValue();
        Auto auto = autoCB.getValue();
        ParkingLot lot = lotCB.getValue();
        LocalDateTime date = startDateDP.getDateTimeValue();

        if (client == null || lot == null || employee == null) {
            showError("Заполните все поля");
            return;
        }

        ContentProvider contentProvider = new ContentProvider();
        try {
            contentProvider.open();
            contentProvider.parkAuto(client, employee, auto, lot, date);
            contentProvider.close();
        } catch (SQLException e) {
            e.printStackTrace();
            showError(e.getMessage());
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
