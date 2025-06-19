module org.example.gui1client {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.gui1client to javafx.fxml;
    exports org.example.gui1client;
}