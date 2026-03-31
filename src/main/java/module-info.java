module edu.tangingina.thebackroom {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.sql;
    requires java.desktop;
    requires com.google.gson;

    opens edu.tangingina.thebackroom.controller to javafx.fxml;
    opens edu.tangingina.thebackroom to javafx.fxml;

    exports edu.tangingina.thebackroom;
    exports edu.tangingina.thebackroom.controller;
    exports edu.tangingina.thebackroom.util;
}