package me.academeg.controller;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import me.academeg.API.Client;
import me.academeg.API.ContentProvider;

import java.sql.SQLException;
import java.util.ArrayList;

public class TestViewController {

    @FXML private TableView<Client> tableView;
    @FXML private TableColumn<Client, String> id;
    @FXML private TableColumn<Client, String> surname;
    @FXML private TableColumn<Client, String> phone;

    private ObservableList<Client> personData = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        id.setCellValueFactory(param -> new ReadOnlyStringWrapper(String.valueOf(param.getValue().getId())));
        surname.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getSurname()));
        phone.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getPhone()));
        try (ContentProvider contentProvider = new ContentProvider()) {
            ArrayList<Client> clients = contentProvider.testViewSelect();
            personData.addAll(clients);
            tableView.setItems(personData);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
