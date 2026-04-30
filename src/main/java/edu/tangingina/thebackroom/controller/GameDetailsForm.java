package edu.tangingina.thebackroom.controller;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.ResultSet;

public class GameDetailsForm extends BaseMediaForm{
    /*
        Handles specific inputs for books such as:
            - game engine
            - mode
            - system requirements
     */

    private MultiValueField gameDevField, gameStudioField, genreField, modeField, platformField;
    private FormFieldGroup titleField, engineField, systemReqsField, synopsisField;
    private ImageFileField widgetField;
    private AccessLinkField linkField;

    public GameDetailsForm() {
        view.getChildren().addAll(formColumn());

        titleField = FormFieldFactory.createTextField("Title", 520);
        gameDevField = FormFieldFactory.createMultiValueField("Game Developer", 200);
        gameStudioField = FormFieldFactory.createMultiValueField("Game Studio", 200);
        modeField = FormFieldFactory.createMultiValueField("Game Mode", 120);
        engineField = FormFieldFactory.createTextField("Game Engine", 200);
        platformField = FormFieldFactory.createMultiValueField("Platform", 200);
        genreField = FormFieldFactory.createMultiValueField("Genre", 120);
        linkField = FormFieldFactory.createAccessLinkField("Access Link");
        widgetField = FormFieldFactory.createImageFileField("Cover Art", 200);
        systemReqsField = FormFieldFactory.createTextArea("System Requirements", 520);
        synopsisField = FormFieldFactory.createTextArea("Synopsis", 520);

        formColumn().getChildren().addAll(
                titleField.getView(),
                gameDevField.getView(),
                gameStudioField.getView(),
                synopsisField.getView(),
                modeField.getView(),
                engineField.getView(),
                platformField.getView(),
                genreField.getView(),
                FormFieldFactory.createYearPicker("Release Year", 120),
                systemReqsField.getView(),
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
        boolean isValid = true;

        titleField.clearError();
        engineField.clearError();
        linkField.clearError();
        gameDevField.clearError();
        gameStudioField.clearError();
        modeField.clearError();
        systemReqsField.clearError();
        synopsisField.clearError();

        if (titleField.isEmpty()) {
            titleField.showError();
            isValid = false;
        }

        if (engineField.isEmpty()) {
            engineField.showError();
            isValid = false;
        }

        if (linkField.isEmpty()) {
            linkField.showError();
            isValid = false;
        }

        if (synopsisField.isEmpty()) {
            synopsisField.showError();
            isValid = false;
        }

        if (systemReqsField.isEmpty()) {
            systemReqsField.showError();
            isValid = false;
        }

        if (gameDevField.isEmpty()) {
            gameDevField.showError();
            isValid = false;
        }

        if (gameStudioField.isEmpty()) {
            gameStudioField.showError();
            isValid = false;
        }

        if (genreField.isEmpty()) {
            genreField.showError();
            isValid = false;
        }

        return isValid;
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

    public void populateForm(ResultSet rs, String game_dev, String game_studio, String category, String game_mode,
                             String platform) {
        try {
            titleField.setValue(rs.getString("name"));
            synopsisField.setValue(rs.getString("synopsis"));
            engineField.setValue(rs.getString("game_engine"));
            systemReqsField.setValue(rs.getString("system_requirements"));

            gameDevField.setValues(game_dev);
            gameStudioField.setValues(game_studio);
            modeField.setValues(game_mode);
            genreField.setValues(category);
            platformField.setValues(platform);

            linkField.setLink(rs.getString("access_link"));

            String path = rs.getString("icon_path");
            widgetField.setImage(path);

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            ex.printStackTrace();
        }
    }


}
