module org.example.breakoutgame {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.breakoutgame to javafx.fxml;
    exports org.example.breakoutgame;
}