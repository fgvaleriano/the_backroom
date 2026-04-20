package edu.tangingina.thebackroom.controller;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import javax.swing.text.Element;

public class BookDetailsForm extends BaseMediaForm {
    /*
        Handles specific inputs for books such as:
            - ISBN
            - Genre
            - Page Count
            - Edition
     */
    public BookDetailsForm() {

        view.getChildren().addAll(
                FormFieldFactory.createTextField("Title"),
                FormFieldFactory.createTextField("Author"),
                FormFieldFactory.createTextArea("Synopsis"),
                FormFieldFactory.createTextArea("Genre"),
                //add button here
                //change to date picker
                FormFieldFactory.createTextField("Release Year"),
                FormFieldFactory.createTextField("Edition"),
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
