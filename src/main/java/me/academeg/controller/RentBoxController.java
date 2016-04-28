package me.academeg.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import me.academeg.API.Client;
import me.academeg.API.ContentProvider;
import me.academeg.API.Employee;
import me.academeg.API.ParkingLot;
import me.academeg.Components.AutoCompleteComboBoxListener;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RentBoxController {

    private ArrayList<ParkingLot> freeParkingLot;
    private ArrayList<Employee> employees;
    private ArrayList<Client> clients;

    @FXML private ComboBox<ParkingLot> lotsCB;
    @FXML private DatePicker startRentDP;
    @FXML private DatePicker endRentDP;
    @FXML private TextField coastTF;
    @FXML private ComboBox<Employee> employeesCB;
    @FXML private ComboBox<Client> clientsCB;
    @FXML private Button rentBTN;


    @FXML
    private void initialize() {
        initViews();
        try {
            ContentProvider contentProvider = new ContentProvider();
            contentProvider.open();
            freeParkingLot = contentProvider.getFreeParkingLot();
            employees = contentProvider.getEmployees();
            clients = contentProvider.getClients();
            contentProvider.close();

            if (freeParkingLot.size() > 0) {
                lotsCB.getItems().addAll(freeParkingLot);
                lotsCB.setValue(freeParkingLot.get(0));
            }

            if (employees.size() > 0) {
                employeesCB.getItems().addAll(employees);
                employeesCB.setValue(employees.get(0));
            }

            if (clients.size() > 0) {
                clientsCB.getItems().addAll(clients);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        startRentDP.setValue(LocalDate.now());
        endRentDP.setValue(LocalDate.now().plusDays(1));
    }

    @FXML
    private void initViews() {
        new AutoCompleteComboBoxListener<>(lotsCB);
        lotsCB.setConverter(new StringConverter<ParkingLot>() {
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

        new AutoCompleteComboBoxListener<>(clientsCB);
        clientsCB.setConverter(new StringConverter<Client>() {
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
    }

    @FXML
    private void rentLot() {
        Client client = clientsCB.getValue();
        ParkingLot lot = lotsCB.getValue();
        Employee employee = employeesCB.getValue();
        LocalDate startDate = startRentDP.getValue();
        LocalDate endDate = endRentDP.getValue();
        float coast = 0;
        try {
            coast = Float.parseFloat(coastTF.getText());
        } catch (NumberFormatException exc) {
            showError(exc.getMessage());
            return;
        }

        if (client == null || lot == null || employee == null ||  startDate == null || endDate == null || coast < 0) {
            showError("Check input data please");
            return;
        }

        ContentProvider contentProvider = new ContentProvider();
        try {
            contentProvider.open();
            contentProvider.rentLot(client, lot, employee, startDate, endDate, coast);
            contentProvider.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

//        Close window
        Stage stage = (Stage) rentBTN.getScene().getWindow();
        stage.close();
    }

    public void updateClientList()  {
        ContentProvider contentProvider = new ContentProvider();
        try {
            contentProvider.open();
            clients = contentProvider.getClients();
            clientsCB.getItems().removeAll();
            clientsCB.getItems().addAll(clients);
            contentProvider.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void createUser() {
        Parent root;
        try {
//            root = FXMLLoader.load(getClass().getResource("/fxml/rent_box.fxml"));
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/create_client.fxml"));
            root = loader.load();
            ((CreateClientController) loader.getController()).setRentBoxController(this);
            Stage stage = new Stage();
            stage.setTitle("Добавление клиента");
            stage.setScene(new Scene(root, 450, 450));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
