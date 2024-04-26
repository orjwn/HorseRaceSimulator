module com.example.simulation {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.simulation to javafx.fxml;
    exports com.example.simulation;
    exports com.example.simulation.models;
    opens com.example.simulation.models to javafx.fxml;
    exports com.example.simulation.utils;
    opens com.example.simulation.utils to javafx.fxml;
    exports com.example.simulation.storage;
    opens com.example.simulation.storage to javafx.fxml;
    exports com.example.simulation.controllers;
    opens com.example.simulation.controllers to javafx.fxml;
}