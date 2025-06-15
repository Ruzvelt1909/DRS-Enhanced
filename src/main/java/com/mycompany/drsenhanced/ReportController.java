package com.mycompany.drsenhanced;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;

public class ReportController {

    @FXML
    private TextField typeField, addressField;
    @FXML
    private TextArea descField;
    @FXML
    private ComboBox<String> priorityCombo, statusCombo;

    @FXML
    public void initialize() {
        priorityCombo.getItems().addAll("LOW", "MEDIUM", "HIGH", "CRITICAL");
        statusCombo.getItems().addAll("NEW", "IN_PROGRESS", "RESOLVED");
    }

    @FXML
    private void handleSubmit(ActionEvent event) {
        String type = typeField.getText();
        String desc = descField.getText();
        String address = addressField.getText();
        String priority = priorityCombo.getValue();
        String status = statusCombo.getValue();

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO disaster_reports (type, description, priority, status, address) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, type);
            ps.setString(2, desc);
            ps.setString(3, priority);
            ps.setString(4, status);
            ps.setString(5, address);
            ps.executeUpdate();
            showAlert("Success", "Report submitted successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Database error: " + e.getMessage());
        }
    }

    @FXML
    private void handleDescriptionKey(KeyEvent event) {
        String text = descField.getText().toLowerCase();

        if (text.contains("fire") || text.contains("explosion")) {
            priorityCombo.setValue("CRITICAL");
        } else if (text.contains("flood") || text.contains("collapse")) {
            priorityCombo.setValue("HIGH");
        } else if (text.contains("storm") || text.contains("accident")) {
            priorityCombo.setValue("MEDIUM");
        } else {
            priorityCombo.setValue("LOW");
        }
    }

    @FXML
    private void handleGoToDashboard(javafx.event.ActionEvent event) {
        SceneSwitcher.switchTo(typeField.getScene(), "Dashboard.fxml", "Disaster Dashboard");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
