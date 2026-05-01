package edu.tangingina.thebackroom.controller;

import edu.tangingina.thebackroom.TheBackroom;
import edu.tangingina.thebackroom.dao.impl.MediaDaoImpl;
import edu.tangingina.thebackroom.model.*;
import edu.tangingina.thebackroom.util.FileManager;
import edu.tangingina.thebackroom.util.FontLoader;
import edu.tangingina.thebackroom.util.Utility;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static edu.tangingina.thebackroom.TheBackroom.mediaList;
import static edu.tangingina.thebackroom.TheBackroom.mediaUniqID;

public class FilmDetailsForm extends BaseMediaForm{
    /*
        Handles specific inputs for films such as:
            - Duration
            - language
     */

    private MultiValueField directorField, genreField, studioField;
    private FormFieldGroup titleField, durationField, languageField, synopsisField, yearField;
    private ImageFileField widgetField;
    private AccessLinkField linkField;
    private int mediaId = -1;
    private boolean isUpdateMode = false;
    private Button btn;
    private Label errorLabel;

    public FilmDetailsForm() {
        view.getChildren().add(formColumn());

        titleField = FormFieldFactory.createTextField("Title", 520);
        directorField = FormFieldFactory.createMultiValueField("Director", 200);
        studioField = FormFieldFactory.createMultiValueField("Studio", 200);
        synopsisField = FormFieldFactory.createTextArea("Synopsis", 520);
        genreField = FormFieldFactory.createMultiValueField("Genre", 200);
        durationField = FormFieldFactory.createTextField("Duration", 200);
        languageField = FormFieldFactory.createTextField("Language", 200);
        linkField = FormFieldFactory.createAccessLinkField("Access Link");
        widgetField = FormFieldFactory.createImageFileField("Movie Poster", 250);
        yearField = FormFieldFactory.createYearPicker("Release Year", 120);

        formColumn().getChildren().addAll(
                titleField.getView(),
                directorField.getView(),
                studioField.getView(),
                synopsisField.getView(),
                genreField.getView(),
                durationField.getView(),
                languageField.getView(),
                yearField.getView(),
                linkField.getView(),
                widgetField.getView(),
                addButton()
        );

        Node lastNode = formColumn().getChildren().get(formColumn().getChildren().size() - 1);
        VBox.setMargin(lastNode, new javafx.geometry.Insets(0, 0, 0, 0));
    }

    private Node addButton(){
        btn = new Button();
        btn.getStyleClass().add("image-button");

        errorLabel = new Label("An error occurred, please try again");
        errorLabel.setFont(FontLoader.bold(20));
        errorLabel.getStyleClass().add("error-label");
        errorLabel.setVisible(false);
        errorLabel.setManaged(false);

        refreshButton();

        btn.setOnAction(e -> {
            if (validateInputs()) {
                if (isUpdateMode) {
                    handleUpdate();
                } else {
                    handleAdd();
                }
            }
        });

        HBox btnContainer = new HBox(btn);
        btnContainer.setAlignment(Pos.CENTER);

        VBox container = new VBox(8, btnContainer, errorLabel);
        container.setAlignment(Pos.CENTER);
        container.setPrefWidth(520);

        return container;
    }

    //input validation
    private boolean validateInputs() {
        boolean isValid = true;

        titleField.clearError();
        durationField.clearError();
        languageField.clearError();
        synopsisField.clearError();
        genreField.clearError();
        studioField.clearError();
        linkField.clearError();
        directorField.clearError();

        if (titleField.isEmpty()) {
            titleField.showError();
            isValid = false;
        }

        if (durationField.isEmpty()) {
            durationField.showError();
            isValid = false;
        }

        if (languageField.isEmpty()) {
            languageField.showError();
            isValid = false;
        }

        if (directorField.isEmpty()) {
            directorField.showError();
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

        if (linkField.isEmpty()) {
            linkField.showError();
            isValid = false;
        }

        return true;
    }

    public MultiValueField getDirectorField() {
        return directorField;
    }

    public MultiValueField getGenreField() {
        return genreField;
    }

    public void populateForm (ResultSet rs, String director, String studio, String category, String links) {
        try {
            titleField.setValue(rs.getString("name"));
            synopsisField.setValue(rs.getString("synopsis"));
            durationField.setValue(rs.getString("duration"));
            languageField.setValue(rs.getString("language"));

            ComboBox<Integer> yearPicker = (ComboBox<Integer>) yearField.getInputs();
            if (yearPicker != null) {
                yearPicker.setValue(Integer.parseInt(rs.getString("release_year")));
            }

            directorField.setValues(director);
            studioField.setValues(studio);
            genreField.setValues(category);
            linkField.setLink(links);

            String path = rs.getString("icon_path");
            widgetField.setImage(path);

        } catch (Exception ex) {
            System.err.println("Error populating TV Show form: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void refreshButton() {
        String assetName = isUpdateMode ? "update_btn.png" : "add_btn.png";
        Image img = new Image(getClass().getResourceAsStream("/edu/tangingina/thebackroom/assets/" + assetName));
        ImageView view = new ImageView(img);
        view.setPreserveRatio(true);
        view.setFitWidth(125);
        btn.setGraphic(view);
    }

    public void setUpdateMode(int mediaId) {
        this.isUpdateMode = true;
        this.mediaId = mediaId;
        refreshButton();
    }

    private void handleUpdate() {
        System.out.println("Update mode");
    }

    private void handleAdd() {
        MediaDaoImpl mediaDao = TheBackroom.mediaDao;
        Utility util = TheBackroom.util;
        FileManager fm = TheBackroom.fm;

        String title = titleField.getUserInput();
        MediaType mediaType = MediaType.Movie;
        String synopsis = synopsisField.getUserInput();

        ComboBox<Integer> yearPicker = (ComboBox<Integer>) yearField.getInputs();

        String year = "2024";
        if(yearPicker != null){
            year = String.valueOf(yearPicker.getValue());
        }
        String imgIcon = fm.saveIMGRelative(widgetField.getSelectedFile());
        List<String> genre = genreField.getValues();
        List<AccessLinkField.AccessLink> onlineAccess = linkField.getValues();

        String duration  = durationField.getUserInput();
        String language = languageField.getUserInput();
        List<String> director = directorField.getValues();
        List<String> studio = studioField.getValues();

        ArrayList<Category> mediaGenre = util.ensureCategoryExist(genre);
        ArrayList<Website> mediaWebsite = util.ensureWebsiteExists(onlineAccess);
        ArrayList<Person> movieDirector = util.ensurePersonExist(director, "Director");
        ArrayList<Company> movieStudio = util.ensureCompanyExists(studio, "Production Studio");

        Media media = new Media(0, title, mediaType, year, synopsis, imgIcon, mediaWebsite, mediaGenre);
        media.setMovieDetails(duration, language, movieDirector, movieStudio);


        try{
            mediaDao.addMedia(media);
            mediaList.put(media.getID(), media);
            mediaUniqID.put(util.getMediaKey(media.getMediaName(), media.getMediaType().name(), media.getReleaseYear()), media.getID());
            TheBackroom.bookMedia.add(media.getID());

            AddArchive_v2.closeWindow();

        }catch (Exception e1){
            errorLabel.setVisible(true);
            errorLabel.setManaged(true);
            e1.getMessage();
        }
    }
}