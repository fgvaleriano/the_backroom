package edu.tangingina.thebackroom.controller;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class GameDetailsForm extends BaseMediaForm{
    /*
        Handles specific inputs for books such as:
            - game engine
            - mode
            - system requirements
     */

    private MultiValueField gameDevField, gameStudioField, genreField;
    private FormFieldGroup titleField, modeField, engineField, linkField, widgetField;

    public GameDetailsForm() {
        view.getChildren().addAll(formColumn());

        titleField = FormFieldFactory.createTextField("Title", 520);
        gameDevField = FormFieldFactory.createMultiValueField("Game Developer", 120);
        gameStudioField = FormFieldFactory.createMultiValueField("Game Studio", 120);
        modeField = FormFieldFactory.createTextField("Game Mode", 120);
        engineField = FormFieldFactory.createTextField("Game Engine", 120);
        genreField = FormFieldFactory.createMultiValueField("Genre", 120);
        linkField = FormFieldFactory.createTextField("Access Link", 520);
        widgetField = FormFieldFactory.createTextField("Cover Art", 520);

        formColumn().getChildren().addAll(
                titleField.getView(),
                gameDevField.getView(),
                gameStudioField.getView(),
                FormFieldFactory.createTextArea("Synopsis", 520),
                modeField.getView(),
                engineField.getView(),
                genreField.getView(),
                FormFieldFactory.createYearPicker("Release Year", 120),
                FormFieldFactory.createTextArea("System Requirements", 520),
                linkField.getView(),
                widgetField.getView(),
                addButton()
        );

        Node lastNode = formColumn().getChildren().get(formColumn().getChildren().size() - 1);
        VBox.setMargin(lastNode, new javafx.geometry.Insets(0, 0, 0, 0));
    }

    private Node addButton(){
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
        HBox container = new HBox(btn);
        container.setAlignment(Pos.CENTER);
        container.setPrefWidth(520);

        return container;
    }

    //input validation
    private boolean validateInputs() {
        boolean isFilled = false;

        titleField.clearError();
        modeField.clearError();
        engineField.clearError();
        linkField.clearError();
        //inlcude system reqs

        if (titleField.isEmpty()) {
            titleField.showError();
            isFilled = false;
        }

        if (modeField.isEmpty()) {
            modeField.showError();
            isFilled = false;
        }

        if (engineField.isEmpty()) {
            engineField.showError();
            isFilled = false;
        }

        if (linkField.isEmpty()) {
            linkField.showError();
            isFilled = false;
        }

        return true;
    }

    public MultiValueField getGameDevField() {
        return gameDevField;
    }

    public MultiValueField getGameStudioField() {
        return gameStudioField;
    }

    public MultiValueField getGenreField() {
        return genreField;
    }


}
