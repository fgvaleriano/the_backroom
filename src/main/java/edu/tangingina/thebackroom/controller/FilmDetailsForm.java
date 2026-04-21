package edu.tangingina.thebackroom.controller;

import javafx.scene.Node;
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
        view.getChildren().add(formColumn());
        formColumn().getChildren().addAll(
                FormFieldFactory.createTextField("Title", 520),
                FormFieldFactory.createFieldWithButton(FormFieldFactory.createTextField("Director", 175),
                        plusBtn()),
                FormFieldFactory.createTextField("Studio", 175),
                FormFieldFactory.createTextArea("Synopsis", 520),
                FormFieldFactory.createFieldWithButton(FormFieldFactory.createTextField("Genre", 120),
                        plusBtn()),
                FormFieldFactory.createTextField("Duration", 120),
                FormFieldFactory.createTextField("Language", 120),
                FormFieldFactory.createYearPicker("Release Year", 120),
                FormFieldFactory.createTextField("Access Link", 520),
                FormFieldFactory.createTextField("Film Poster", 520),
                addButton()
        );

        Node lastNode = formColumn().getChildren().get(formColumn().getChildren().size() - 1);
        VBox.setMargin(lastNode, new javafx.geometry.Insets(0, 0, 0, 0));
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

    private Button plusBtn() {
        Button plsBtn = new Button();
        plsBtn.getStyleClass().add("image-button");
        Image img = new Image(getClass().getResourceAsStream(
                "/edu/tangingina/thebackroom/assets/plus_btn.png"));
        ImageView view = new ImageView(img);
        view.setPreserveRatio(true);
        view.setFitWidth(32);

        plsBtn.setGraphic(view);
        return plsBtn;
    }
}
