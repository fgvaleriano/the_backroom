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
                FormFieldFactory.createTextField("Title", 520),
                FormFieldFactory.createTextField("Author", 175),
                FormFieldFactory.createTextField("Publisher", 175),
                FormFieldFactory.createTextArea("Synopsis", 520),
                FormFieldFactory.createTextField("Genre", 120),
                FormFieldFactory.createYearPicker("Release Year", 120),
                FormFieldFactory.createTextField("Edition", 120),
                FormFieldFactory.createTextField("Access Link", 520),
                FormFieldFactory.createTextField("Book Cover", 520),
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
