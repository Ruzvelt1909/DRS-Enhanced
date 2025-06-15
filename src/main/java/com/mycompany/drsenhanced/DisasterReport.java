package com.mycompany.drsenhanced;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DisasterReport {
    private final StringProperty type;
    private final StringProperty description;
    private final StringProperty priority;
    private final StringProperty status;
    private final StringProperty address;

    public DisasterReport(String type, String description, String priority, String status, String address) {
        this.type = new SimpleStringProperty(type);
        this.description = new SimpleStringProperty(description);
        this.priority = new SimpleStringProperty(priority);
        this.status = new SimpleStringProperty(status);
        this.address = new SimpleStringProperty(address);
    }

    public StringProperty typeProperty() { return type; }
    public StringProperty descriptionProperty() { return description; }
    public StringProperty priorityProperty() { return priority; }
    public StringProperty statusProperty() { return status; }
    public StringProperty addressProperty() { return address; }
}
