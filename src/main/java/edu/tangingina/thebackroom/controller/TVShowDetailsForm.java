package edu.tangingina.thebackroom.controller;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class TVShowDetailsForm extends BaseMediaForm{
    /*
        Handles specific inputs for books such as:
            - season count
            - episode count
            - status
     */

    private MultiValueField directorField, genreField;
    private FormFieldGroup titleField, studioField, seasonField, episodeField, statusField, linkField, widgetField;


    public TVShowDetailsForm() {
        view.getChildren().addAll(formColumn());

        titleField = FormFieldFactory.createTextField("Title", 520);
        directorField = FormFieldFactory.createMultiValueField("Director", 175);
        studioField = FormFieldFactory.createTextField("Studio", 120);
        genreField = FormFieldFactory.createMultiValueField("Genre", 120);
        seasonField = FormFieldFactory.createTextField("Season Count", 120);
        episodeField = FormFieldFactory.createTextField("Episode Count", 120);
        statusField = FormFieldFactory.createTextField("Status", 120);
        linkField = FormFieldFactory.createTextField("Access Link", 520);
        widgetField = FormFieldFactory.createTextField("Book Cover", 520);

        formColumn().getChildren().addAll(
                titleField.getView(),
                directorField.getView(),
                studioField.getView(),
                FormFieldFactory.createTextArea("Synopsis", 520),
                genreField.getView(),
                seasonField.getView(),
                episodeField.getView(),
                FormFieldFactory.createYearPicker("Release Year", 120),
                statusField.getView(),
                linkField.getView(),
                widgetField.getView(),
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
        btn.setOnAction(e -> {
            validateInputs();

            if (validateInputs()) {
                AddArchive_v2.closeWindow();
            }
        });
        return btn;
    }

    //input validation
    private boolean validateInputs() {
        boolean isFilled = false;

        titleField.clearError();
        seasonField.clearError();
        episodeField.clearError();
        statusField.clearError();
        linkField.clearError();

        if (titleField.isEmpty()) {
            titleField.showError();
            isFilled = false;
        }

        if (seasonField.isEmpty()) {
            seasonField.showError();
            isFilled = false;
        }

        if (episodeField.isEmpty()) {
            episodeField.showError();
            isFilled = false;
        }

        if (statusField.isEmpty()) {
            statusField.showError();
            isFilled = false;
        }

        if (linkField.isEmpty()) {
            linkField.showError();
            isFilled = false;
        }

        return true;
    }

    public MultiValueField getDirectorField() {
        return directorField;
    }

    public MultiValueField getGenreField() {
        return genreField;
    }
}
