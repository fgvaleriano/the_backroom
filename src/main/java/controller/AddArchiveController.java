package controller;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class AddArchiveController implements Initializable {
    @FXML private Label addArchiveLabel;

    @FXML private AnchorPane root;
    @FXML private VBox box;
    @FXML private GridPane nameGrid;

    @FXML private Label nameLabel;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //we add a listener to listen to dynamically scale the components...
        root.sceneProperty().addListener(((obs, prevScene, currScene) ->{
            if(currScene != null) scale(currScene);
        }));


    }

    private void scale(Scene scene){
        //Title Label
        addArchiveLabel.styleProperty().bind(scene.heightProperty().multiply(0.0625).
                asString("-fx-font-size: %.0fpx; -fx-font-family: 'Reddit Sans ExtraBold';"));

        //this listen for any height changes for the vbox where label is siiting, so that margin is dynamically increased as well
        box.heightProperty().addListener(((obs, prevBox, currBox) -> {
            VBox.setMargin(addArchiveLabel, new Insets(currBox.doubleValue() * 0.0520833, 0, 0, 0));

        }));

        nameLabel.styleProperty().bind(nameGrid.widthProperty().multiply(0.09).multiply(0.3)
                .asString("-fx-font-size: %.0fpx; -fx-font-family: 'Roboto Serif';"));

        scene.widthProperty().addListener((obs, oldWidth, newWidth) -> {
            System.out.println("Scene Width: " + newWidth);
        });

        scene.heightProperty().addListener((obs, oldHeight, newHeight) -> {
            System.out.println("Scene Height: " + newHeight);
        });
    }
}
