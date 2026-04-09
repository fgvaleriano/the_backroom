module edu.tangingina.thebackroom {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.sql;
    requires java.desktop;
    requires com.google.gson;
    requires mysql.connector.j;
    requires de.mkammerer.argon2.nolibs;
    requires de.mkammerer.argon2;
    requires com.sun.jna;

    opens edu.tangingina.thebackroom.controller to javafx.fxml;
    opens edu.tangingina.thebackroom to javafx.fxml;

    exports edu.tangingina.thebackroom;
    exports edu.tangingina.thebackroom.controller;
    exports edu.tangingina.thebackroom.util;
    opens edu.tangingina.thebackroom.util to javafx.fxml;
}