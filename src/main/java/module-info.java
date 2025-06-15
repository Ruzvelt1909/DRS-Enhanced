module com.mycompany.drsenhanced {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.mycompany.drsenhanced to javafx.fxml;
    exports com.mycompany.drsenhanced;
}
