package edu.tangingina.thebackroom.controller;

import edu.tangingina.thebackroom.util.FontLoader;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.event.*;
import javafx.scene.control.*;
import java.net.*;
import java.util.*;


public class AddArchive_v2 implements Initializable{

    @FXML
    private Label title;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        applyFont();
    }

    private void applyFont() {
        title.setFont(FontLoader.extra(18));
    }

    @FXML
    private void handleClick(ActionEvent event) {
        System.out.println("Button Clicked");
    }
}
