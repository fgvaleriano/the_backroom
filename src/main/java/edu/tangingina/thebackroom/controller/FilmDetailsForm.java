package edu.tangingina.thebackroom.controller;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class FilmDetailsForm extends BaseMediaForm{
    /*
        Handles specific inputs for films such as:
            - Duration
            - language
     */

    public FilmDetailsForm() {
        view.getChildren().addAll(
                FormFieldFactory.createTextField("Title"),
                FormFieldFactory.createTextField("Director"),
                FormFieldFactory.createTextArea("Synopsis"),
                FormFieldFactory.createTextArea("Genre"),
                FormFieldFactory.createTextField("Duration"),
                FormFieldFactory.createTextField("Language"),
                //release year
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
