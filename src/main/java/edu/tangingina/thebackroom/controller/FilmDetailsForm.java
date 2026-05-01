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

    private Media oldMedia;

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

        return isValid;
    }

    public void populateForm (String director, String studio, String category, String links, String path, Media media) {
        TheBackroom.util.setIfNotNull(titleField, media.getMediaName());
        TheBackroom.util.setIfNotNull(synopsisField, media.getSynopsis());
        TheBackroom.util.setIfNotNull(durationField, media.getDuration());
        TheBackroom.util.setIfNotNull(languageField, media.getLanguage());

        ComboBox<Integer> yearPicker = (ComboBox<Integer>) yearField.getInputs();
        if (yearPicker != null && media.getReleaseYear() != null && !media.getReleaseYear().equals("null")) {
            yearPicker.setValue(Integer.valueOf(media.getReleaseYear()));
        }

        TheBackroom.util.setIfNotNull(directorField, director);
        TheBackroom.util.setIfNotNull(studioField, studio);
        TheBackroom.util.setIfNotNull(genreField, category);
        TheBackroom.util.setIfNotNull(linkField, links);
        TheBackroom.util.setIfNotNull(widgetField, path);
        setOldMedia(media);
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
            TheBackroom.videoMedia.add(media.getID());

            AddArchive_v2.closeWindow();

        }catch (Exception e1){
            errorLabel.setVisible(true);
            errorLabel.setManaged(true);
            e1.getMessage();
        }
    }

    private void handleUpdate() {
        System.out.println("Update mode");

        MediaDaoImpl mediaDao = TheBackroom.mediaDao;
        Utility util = TheBackroom.util;
        FileManager fm = TheBackroom.fm;

        String title = titleField.getUserInput();
        MediaType mediaType = MediaType.Book;
        String synopsis = synopsisField.getUserInput();

        ComboBox<Integer> yearPicker = (ComboBox<Integer>) yearField.getInputs();
        String year = "2024";
        if (yearPicker != null) year = String.valueOf(yearPicker.getValue());

        // saves only if nagpick ng new path
        String imgIcon = widgetField.getSelectedFile() != null
                ? fm.saveIMGRelative(widgetField.getSelectedFile())
                : widgetField.getCurrentPath();

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
            //if the name was updated, then we update our cache
            if(!media.getMediaName().equals(oldMedia.getMediaName())){
                TheBackroom.mediaUniqID.remove(TheBackroom.util.getMediaKey(oldMedia.getMediaName(), oldMedia.getMediaType().name(), oldMedia.getReleaseYear()));
                TheBackroom.mediaUniqID.put(TheBackroom.util.getMediaKey(media.getMediaName(), media.getMediaType().name(), media.getReleaseYear()), media.getID());
            }

            mediaDao.updateMedia(media, oldMedia);
            mediaList.put(mediaId, media);
            UpdateArchive.closeWindow();
        }catch (Exception e){
            errorLabel.setVisible(true);
            errorLabel.setManaged(true);
        }
    }

    private void setOldMedia(Media media){
        this.oldMedia = media;
    }
}
