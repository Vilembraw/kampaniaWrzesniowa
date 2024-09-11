module org.example.kolos2021canvas {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens org.example.kolos2021canvas to javafx.fxml;
    exports org.example.kolos2021canvas;
}