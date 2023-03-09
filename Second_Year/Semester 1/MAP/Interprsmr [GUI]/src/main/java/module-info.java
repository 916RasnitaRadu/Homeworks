module com.example.demo2 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.example.demo2 to javafx.fxml;
    opens view.gui to javafx.fxml;
    opens controller to javafx.fxml;
    exports com.example.demo2;
    exports view.gui;
    exports controller;
}