module Server {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.desktop;
    requires java.sql;


    opens Server to javafx.fxml;
    exports Server;
}