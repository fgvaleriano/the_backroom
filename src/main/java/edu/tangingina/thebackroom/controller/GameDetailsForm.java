package edu.tangingina.thebackroom.controller;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class GameDetailsForm extends BaseMediaForm{
    /*
        Handles specific inputs for books such as:
            - game engine
            - mode
            - system requirements
     */

    public GameDetailsForm() {
        view.getChildren().addAll(
                FormFieldFactory.createTextField("Title", 520),
                FormFieldFactory.createTextField("Game Developer", 175),
                FormFieldFactory.createTextField("Game Studio", 175),
                FormFieldFactory.createTextArea("Synopsis", 520),
                FormFieldFactory.createTextField("Mode", 120),
                FormFieldFactory.createTextField("Game Engine", 120),
                FormFieldFactory.createTextField("Genre", 120),
                FormFieldFactory.createYearPicker("Release Year", 120),
                FormFieldFactory.createTextArea("System Requirements", 520),
                FormFieldFactory.createTextField("Access Link", 520),
                FormFieldFactory.createTextField("Cover Art", 520),
                addButton()
        );
    }

    private Button addButton(){
        Button btn = new Button();
        btn.getStyleClass().add("image-button");

        Image img = new Image(getClass().getResourceAsStream(
                "/edu/tangingina/thebackroom/assets/add_btn.png"));
        ImageView view = new ImageView(img);
        view.setPreserveRatio(true);
        view.setFitWidth(125);

        btn.setGraphic(view);
        return btn;
    }
}
