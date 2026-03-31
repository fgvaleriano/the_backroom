module edu.tangingina.thebackroom {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.sql;


    opens edu.tangingina.thebackroom to javafx.fxml;
    exports edu.tangingina.thebackroom;
    exports edu.tangingina.thebackroom.util;
    opens edu.tangingina.thebackroom.util to javafx.fxml;
}