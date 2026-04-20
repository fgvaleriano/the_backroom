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
                FormFieldFactory.createTextField("Title"),
                FormFieldFactory.createTextField("Creator"),
                FormFieldFactory.createTextArea("Synopsis"),
                FormFieldFactory.createTextField("Mode"),
                FormFieldFactory.createTextArea("Game Engine"),
                //release year
                FormFieldFactory.createTextField("System Requirements"),
                FormFieldFactory.createTextField("Access Link"),
                FormFieldFactory.createTextField("Widget"),
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
