module org.example.breakoutgame {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens org.example.breakoutgame to javafx.fxml;
    exports org.example.breakoutgame;
}