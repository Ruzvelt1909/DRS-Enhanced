package com.mycompany.drsenhanced;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.*;

public class DashboardController {

    @FXML
    private TableView<DisasterReport> reportTable;
    @FXML
    private TableColumn<DisasterReport, String> colType;
    @FXML
    private TableColumn<DisasterReport, String> colDesc;
    @FXML
    private TableColumn<DisasterReport, String> colPriority;
    @FXML
    private TableColumn<DisasterReport, String> colStatus;
    @FXML
    private TableColumn<DisasterReport, String> colAddress;

    @FXML
    private ComboBox<String> priorityFilter;
    @FXML
    private ComboBox<String> statusFilter;

    private ObservableList<DisasterReport> reportList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colType.setCellValueFactory(data -> data.getValue().typeProperty());
        colDesc.setCellValueFactory(data -> data.getValue().descriptionProperty());
        colPriority.setCellValueFactory(data -> data.getValue().priorityProperty());
        colStatus.setCellValueFactory(data -> data.getValue().statusProperty());
        colAddress.setCellValueFactory(data -> data.getValue().addressProperty());

        priorityFilter.getItems().addAll("CRITICAL", "HIGH", "MEDIUM", "LOW");
        statusFilter.getItems().addAll("NEW", "IN_PROGRESS", "RESOLVED");

    }

    @FXML
    private void handleApplyFilter() {
        reportList.clear();

        // Get filter values safely
        String selectedPriority = priorityFilter.getValue();
        String selectedStatus = statusFilter.getValue();

        // Reuse original dropdown values in case they got cleared
        if (priorityFilter.getItems().isEmpty()) {
            priorityFilter.getItems().setAll("CRITICAL", "HIGH", "MEDIUM", "LOW");
        }
        if (statusFilter.getItems().isEmpty()) {
            statusFilter.getItems().setAll("NEW", "IN_PROGRESS", "RESOLVED");
        }

        String query = "SELECT * FROM disaster_reports WHERE 1=1";
        if (selectedPriority != null && !selectedPriority.isEmpty()) {
            query += " AND priority = '" + selectedPriority + "'";
        }
        if (selectedStatus != null && !selectedStatus.isEmpty()) {
            query += " AND status = '" + selectedStatus + "'";
        }

        try (Connection conn = DBConnection.getConnection()) {
            ResultSet rs = conn.createStatement().executeQuery(query);
            while (rs.next()) {
                reportList.add(new DisasterReport(
                        rs.getString("type"),
                        rs.getString("description"),
                        rs.getString("priority"),
                        rs.getString("status"),
                        rs.getString("address")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        reportTable.setItems(reportList);
        reportTable.refresh(); // 🔁 forces refresh on repeated filter

    }

    @FXML
    private void handleBackToForm(javafx.event.ActionEvent event) {
        SceneSwitcher.switchTo(reportTable.getScene(), "ReportForm.fxml", "Disaster Response System");
    }

    @FXML
    private void loadReports() {
        reportList.clear();
        try (Connection conn = DBConnection.getConnection()) {
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM disaster_reports");
            while (rs.next()) {
                reportList.add(new DisasterReport(
                        rs.getString("type"),
                        rs.getString("description"),
                        rs.getString("priority"),
                        rs.getString("status"),
                        rs.getString("address")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        reportTable.setItems(reportList);
        priorityFilter.getSelectionModel().clearSelection();
        statusFilter.getSelectionModel().clearSelection();

    }
}
