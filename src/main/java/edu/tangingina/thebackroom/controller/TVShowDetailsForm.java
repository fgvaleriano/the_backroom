package edu.tangingina.thebackroom.controller;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.ResultSet;
import java.util.Map;

public class TVShowDetailsForm extends BaseMediaForm{
    /*
        Handles specific inputs for books such as:
            - season count
            - episode count
            - status
     */

    private MultiValueField directorField, genreField, studioField;
    private FormFieldGroup titleField, seasonField,
            episodeField, statusField, synopsisField;
    private ImageFileField widgetField;
    private AccessLinkField linkField;


    public TVShowDetailsForm() {
        view.getChildren().addAll(formColumn());

        titleField = FormFieldFactory.createTextField("Title", 520);
        directorField = FormFieldFactory.createMultiValueField("Director", 175);
        studioField = FormFieldFactory.createMultiValueField("Studio", 120);
        synopsisField = FormFieldFactory.createTextArea("Synopsis", 520);
        genreField = FormFieldFactory.createMultiValueField("Genre", 120);
        seasonField = FormFieldFactory.createTextField("Season Count", 120);
        episodeField = FormFieldFactory.createTextField("Episode Count", 120);
        statusField = FormFieldFactory.createTextField("Status", 120);
        linkField = FormFieldFactory.createAccessLinkField("Access Link");
        widgetField = FormFieldFactory.createImageFileField("Book Cover", 200);

        formColumn().getChildren().addAll(
                titleField.getView(),
                directorField.getView(),
                studioField.getView(),
                synopsisField.getView(),
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
        seasonField.clearError();
        episodeField.clearError();
        statusField.clearError();
        genreField.clearError();
        studioField.clearError();
        synopsisField.clearError();
        linkField.clearError();

        if (titleField.isEmpty()) {
            titleField.showError();
            isValid = false;
        }

        if (seasonField.isEmpty()) {
            seasonField.showError();
            isValid = false;
        }

        if (episodeField.isEmpty()) {
            episodeField.showError();
            isValid = false;
        }

        if (statusField.isEmpty()) {
            statusField.showError();
            isValid = false;
        }

        if (synopsisField.isEmpty()) {
            synopsisField.showError();
            isValid = false;
        }

        if (genreField.isEmpty()) {
            genreField.showError();
            isValid = false;
        }

        if (studioField.isEmpty()) {
            studioField.showError();
            isValid = false;
        }

        if (linkField.isEmpty()) {
            linkField.showError();
            isValid = false;
        }

        return isValid;
    }

    public MultiValueField getDirectorField() {
        return directorField;
    }

    public MultiValueField getGenreField() {
        return genreField;
    }

    public void populateForm(ResultSet rs, String director, String studio, String category) {
        try {
            System.out.println("Reached here");
            titleField.setValue(rs.getString("name"));
            synopsisField.setValue(rs.getString("synopsis"));
            seasonField.setValue(rs.getString("season_count"));
            episodeField.setValue(rs.getString("episode_count"));
            statusField.setValue(rs.getString("status"));

            directorField.setValues(director);
            studioField.setValues(studio);
            genreField.setValues(category);
            linkField.setLink(rs.getString("access_link"));

            String path = rs.getString("icon_path");
            widgetField.setImage(path);

        } catch (Exception ex) {
            System.err.println("Error populating TV Show form: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
