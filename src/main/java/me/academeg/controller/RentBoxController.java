package me.academeg.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import me.academeg.API.Client;
import me.academeg.API.ContentProvider;
import me.academeg.API.Employee;
import me.academeg.API.ParkingLot;
import me.academeg.components.AutoCompleteComboBoxListener;
import me.academeg.stringConverters.ClientsStringConverter;
import me.academeg.stringConverters.EmployeesStringConverter;
import me.academeg.stringConverters.LotsStringConverter;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class RentBoxController implements UpdateCallback {

    @FXML private ComboBox<ParkingLot> lotsCB;
    @FXML private DatePicker startRentDP;
    @FXML private DatePicker endRentDP;
    @FXML private TextField coastTF;
    @FXML private ComboBox<Employee> employeesCB;
    @FXML private ComboBox<Client> clientsCB;
    @FXML private Button rentBTN;

    @FXML private void initialize() {
        initViews();
        try {
            ContentProvider contentProvider = new ContentProvider();
            contentProvider.open();
            ArrayList<ParkingLot> freeParkingLot = contentProvider.getFreeParkingLot();
            ArrayList<Employee> employees = contentProvider.getEmployees();
            ArrayList<Client> clients = contentProvider.getClients();
            contentProvider.close();

            if (freeParkingLot.size() > 0) {
                lotsCB.getItems().setAll(freeParkingLot);
                lotsCB.setValue(freeParkingLot.get(0));
            }

            if (employees.size() > 0) {
                employeesCB.getItems().setAll(employees);
                employeesCB.setValue(employees.get(0));
            }

            if (clients.size() > 0) {
                clientsCB.getItems().setAll(clients);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        startRentDP.setValue(LocalDate.now());
        endRentDP.setValue(LocalDate.now().plusDays(1));
    }

    @FXML private void initViews() {
        new AutoCompleteComboBoxListener<>(lotsCB);
        lotsCB.setConverter(new LotsStringConverter());

        new AutoCompleteComboBoxListener<>(employeesCB);
        employeesCB.setConverter(new EmployeesStringConverter());

        new AutoCompleteComboBoxListener<>(clientsCB);
        clientsCB.setConverter(new ClientsStringConverter());
    }

    @FXML private void rentLot() {
        Client client = clientsCB.getValue();
        ParkingLot lot = lotsCB.getValue();
        Employee employee = employeesCB.getValue();
        LocalDate startDate = startRentDP.getValue();
        LocalDate endDate = endRentDP.getValue();
        float coast;
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

    /**
     * Update client list after add new client
     */
    @Override
    public void update() {
        ContentProvider contentProvider = new ContentProvider();
        try {
            contentProvider.open();
            ArrayList<Client> clients = contentProvider.getClients();
            clientsCB.getItems().removeAll();
            clientsCB.getItems().addAll(clients);
            contentProvider.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML private void createUser() {
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/create_client.fxml"));
            root = loader.load();
            ((CreateClientController) loader.getController()).setCallback(this);
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
