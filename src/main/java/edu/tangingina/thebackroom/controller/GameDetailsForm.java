package edu.tangingina.thebackroom.controller;

import edu.tangingina.thebackroom.TheBackroom;
import edu.tangingina.thebackroom.dao.impl.MediaDaoImpl;
import edu.tangingina.thebackroom.model.*;
import edu.tangingina.thebackroom.util.FileManager;
import edu.tangingina.thebackroom.util.FontLoader;
import edu.tangingina.thebackroom.util.Utility;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static edu.tangingina.thebackroom.TheBackroom.mediaList;
import static edu.tangingina.thebackroom.TheBackroom.mediaUniqID;

public class GameDetailsForm extends BaseMediaForm{
    /*
        Handles specific inputs for books such as:
            - game engine
            - mode
            - system requirements
     */

    private MultiValueField gameDevField, gameStudioField, genreField, modeField,platformField, gamePublisherField;
    private FormFieldGroup titleField, engineField, systemReqsField, synopsisField, yearField;
    private ImageFileField widgetField;
    private AccessLinkField linkField;
    private int mediaId = -1;
    private boolean isUpdateMode = false;
    private Button btn;
    private Label errorLabel;

    private Media oldMedia;

    public GameDetailsForm() {
        view.getChildren().addAll(formColumn());

        titleField = FormFieldFactory.createTextField("Title", 520);
        gameDevField = FormFieldFactory.createMultiValueField("Game Developer", 200);
        gameStudioField = FormFieldFactory.createMultiValueField("Game Studio", 200);
        gamePublisherField = FormFieldFactory.createMultiValueField("Game Publisher", 200);
        modeField = FormFieldFactory.createMultiValueField("Game Mode", 200);
        platformField = FormFieldFactory.createMultiValueField("Game Platform", 200);
        engineField = FormFieldFactory.createTextField("Game Engine", 200);
        genreField = FormFieldFactory.createMultiValueField("Genre", 200);
        linkField = FormFieldFactory.createAccessLinkField("Access Link");
        widgetField = FormFieldFactory.createImageFileField("Cover Art", 250);
        systemReqsField = FormFieldFactory.createTextArea("System Requirements", 520);
        synopsisField = FormFieldFactory.createTextArea("Synopsis", 520);
        yearField = FormFieldFactory.createYearPicker("Release Year", 120);

        formColumn().getChildren().addAll(
                titleField.getView(),
                gameDevField.getView(),
                gameStudioField.getView(),
                gamePublisherField.getView(),
                synopsisField.getView(),
                modeField.getView(),
                platformField.getView(),
                engineField.getView(),
                genreField.getView(),
                yearField.getView(),
                systemReqsField.getView(),
                linkField.getView(),
                widgetField.getView(),
                addButton()
        );

        Node lastNode = formColumn().getChildren().get(formColumn().getChildren().size() - 1);
        VBox.setMargin(lastNode, new javafx.geometry.Insets(0, 0, 0, 0));

        addWindowListener();
    }

    private Node addButton(){
        btn = new Button();
        btn.getStyleClass().add("image-button");

        errorLabel = new Label("An error occurred, please try again");
        errorLabel.setPadding(new Insets(5, 0, 10, 10));
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
            }else{
                errorLabel.setText("Please fill in all required fields.");
                errorLabel.setVisible(true);
                errorLabel.setManaged(true);
            }
        });

        HBox btnContainer = new HBox(btn);
        btnContainer.setAlignment(Pos.CENTER);

        VBox container = new VBox(8,errorLabel, btnContainer);
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

        String errorMessage = "";

        // //high priority this before we check if empty fields
        if (titleField.getUserInput().length() > 255) {
            titleField.showError();
            errorMessage = "Title is too long (Max 255 characters).";
            isValid = false;
        }
        else if (engineField.getUserInput().length() > 100) {
            engineField.showError();
            errorMessage = "Engine name is too long (Max 100 characters).";
            isValid = false;
        }

            if (titleField.isEmpty() || gameDevField.isEmpty() || gameStudioField.isEmpty() ||
                    genreField.isEmpty() || synopsisField.isEmpty() || linkField.isEmpty()) {

                //This highlights all empty Fields
                if (titleField.isEmpty()) titleField.showError();
                if (gameDevField.isEmpty()) gameDevField.showError();
                if (gameStudioField.isEmpty()) gameStudioField.showError();
                if (genreField.isEmpty()) genreField.showError();
                if (synopsisField.isEmpty()) synopsisField.showError();
                if (linkField.isEmpty()) linkField.showError();

                if(errorMessage.isEmpty()) errorMessage = "Please fill in all required fields.";
                isValid = false;
            }


        if (!isValid) {
            errorLabel.setText(errorMessage);
            errorLabel.setVisible(true);
            errorLabel.setManaged(true);
        }

        return isValid;
    }

    public void populateForm(String game_dev, String game_studio, String category, String game_mode,
                             String game_platform, String links, String game_publisher, String path, Media media) {

        TheBackroom.util.setIfNotNull(titleField, media.getMediaName());
        TheBackroom.util.setIfNotNull(synopsisField, media.getSynopsis());
        TheBackroom.util.setIfNotNull(engineField, media.getGameEngine());
        TheBackroom.util.setIfNotNull(systemReqsField, media.getSystemRequirements());

        ComboBox<Integer> yearPicker = (ComboBox<Integer>) yearField.getInputs();
        if (yearPicker != null && media.getReleaseYear() != null && !media.getReleaseYear().equals("null")) {
            yearPicker.setValue(Integer.valueOf(media.getReleaseYear()));
        }

        TheBackroom.util.setIfNotNull(gameDevField, game_dev);
        TheBackroom.util.setIfNotNull(gameStudioField, game_studio);
        TheBackroom.util.setIfNotNull(gamePublisherField, game_publisher);
        TheBackroom.util.setIfNotNull(modeField, game_mode);
        TheBackroom.util.setIfNotNull(genreField, category);
        TheBackroom.util.setIfNotNull(platformField, game_platform);
        TheBackroom.util.setIfNotNull(linkField, links);
        TheBackroom.util.setIfNotNull(widgetField, path);

        setOldMedia(media);
        addWindowListener();
    }

    private void refreshButton() {
        String assetName = isUpdateMode ? "update_btn.png" : "add_btn.png";
        Image img = new Image(getClass().getResourceAsStream("/edu/tangingina/thebackroom/assets/" + assetName));
        ImageView view = new ImageView(img);
        view.setPreserveRatio(true);
        view.setFitWidth(125);
        btn.setGraphic(view);
    }

    private void handleAdd() {
        MediaDaoImpl mediaDao = TheBackroom.mediaDao;
        Utility util = TheBackroom.util;
        FileManager fm = TheBackroom.fm;

        String title = titleField.getUserInput();
        MediaType mediaType = MediaType.Game;
        String synopsis = synopsisField.getUserInput();

        ComboBox<Integer> yearPicker = (ComboBox<Integer>) yearField.getInputs();

        String year = "2024";
        if(yearPicker != null){
            year = String.valueOf(yearPicker.getValue());
        }
        String imgIcon = fm.saveIMGRelative(widgetField.getSelectedFile());
        List<String> genre = genreField.getValues();
        List<AccessLinkField.AccessLink> onlineAccess = linkField.getValues();

        String gameEngine = engineField.getUserInput();
        String systemRequirements = systemReqsField.getUserInput();
        List<String> mode = modeField.getValues();
        List<String> platform = platformField.getValues();
        List<String> gameDev = gameDevField.getValues();
        List<String> gameStudio = gameStudioField.getValues();
        List<String> gamePublisher = gamePublisherField.getValues();

        ArrayList<Category> mediaGenre = util.ensureCategoryExist(genre);
        ArrayList<Website> mediaWebsite = util.ensureWebsiteExists(onlineAccess);
        ArrayList<Person> mediaGameDev = util.ensurePersonExist(gameDev, "Game Developer");
        ArrayList<Company> mediaGameStudio = util.ensureCompanyExists(gameStudio, "Game Studio");
        ArrayList<Company> mediaGamePublisher = util.ensureCompanyExists(gamePublisher, "Publisher");
        ArrayList<GameMode> mediaGameMode = util.ensureGameModeExists(mode);
        ArrayList<Platform> mediaGamePlatform = util.ensureGamePlatformExists(platform);

        ArrayList<Company> mediaGameCompany = new ArrayList<>();
        mediaGameCompany.addAll(mediaGamePublisher);
        mediaGameCompany.addAll(mediaGameStudio);

        Media media = new Media(0, title, mediaType, year, synopsis, imgIcon, mediaWebsite, mediaGenre);
        media.setGameDetails(gameEngine, systemRequirements, mediaGameDev, mediaGameCompany, mediaGamePlatform, mediaGameMode);


        try{
            mediaDao.addMedia(media);
            mediaList.put(media.getID(), media);
            mediaUniqID.put(util.getMediaKey(media.getMediaName(), media.getMediaType().name(), media.getReleaseYear()), media.getID());
            TheBackroom.gameMedia.add(media.getID());

            AddArchive_v2.closeWindow();

        }catch (Exception e1){
            errorLabel.setText("An error occured. Please try again later");
            errorLabel.setVisible(true);
            errorLabel.setManaged(true);
            e1.getMessage();
        }
    }

    public void setUpdateMode(int mediaId) {
        this.isUpdateMode = true;
        this.mediaId = mediaId;
        refreshButton();
    }

    private void handleUpdate() {
        System.out.println("Update mode");

        MediaDaoImpl mediaDao = TheBackroom.mediaDao;
        Utility util = TheBackroom.util;
        FileManager fm = TheBackroom.fm;

        String title = titleField.getUserInput();
        MediaType mediaType = MediaType.Game;
        String synopsis = synopsisField.getUserInput();

        ComboBox<Integer> yearPicker = (ComboBox<Integer>) yearField.getInputs();
        String year = "2024";
        if (yearPicker != null) year = String.valueOf(yearPicker.getValue());

        String imgIcon = widgetField.getSelectedFile() != null
                ? fm.saveIMGRelative(widgetField.getSelectedFile())
                : widgetField.getCurrentPath();
        List<String> genre = genreField.getValues();
        List<AccessLinkField.AccessLink> onlineAccess = linkField.getValues();

        String gameEngine = engineField.getUserInput();
        String systemRequirements = systemReqsField.getUserInput();
        List<String> mode = modeField.getValues();
        List<String> platform = platformField.getValues();
        List<String> gameDev = gameDevField.getValues();
        List<String> gameStudio = gameStudioField.getValues();
        List<String> gamePublisher = gamePublisherField.getValues();

        ArrayList<Category> mediaGenre = util.ensureCategoryExist(genre);
        ArrayList<Website> mediaWebsite = util.ensureWebsiteExists(onlineAccess);
        ArrayList<Person> mediaGameDev = util.ensurePersonExist(gameDev, "Game Developer");
        ArrayList<Company> mediaGameStudio = util.ensureCompanyExists(gameStudio, "Game Studio");
        ArrayList<Company> mediaGamePublisher = util.ensureCompanyExists(gamePublisher, "Publisher");
        ArrayList<GameMode> mediaGameMode = util.ensureGameModeExists(mode);
        ArrayList<Platform> mediaGamePlatform = util.ensureGamePlatformExists(platform);

        ArrayList<Company> mediaGameCompany = new ArrayList<>();
        mediaGameCompany.addAll(mediaGamePublisher);
        mediaGameCompany.addAll(mediaGameStudio);

        Media media = new Media(mediaId, title, mediaType, year, synopsis, imgIcon, mediaWebsite, mediaGenre);
        media.setGameDetails(gameEngine, systemRequirements, mediaGameDev, mediaGameCompany, mediaGamePlatform, mediaGameMode);

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
            errorLabel.setText("An error occured. Please try again later");
            errorLabel.setVisible(true);
            errorLabel.setManaged(true);
        }
    }

    private void setOldMedia(Media media){
        this.oldMedia = media;
    }

    private boolean checkEmptyField(){
        return titleField.isEmpty() &&
                synopsisField.isEmpty() &&
                (yearField == null || yearField.getUserInput() == null || yearField.getUserInput().trim().isEmpty()) &&
                (widgetField == null || widgetField.getSelectedFile() == null) &&
                (genreField == null || genreField.isEmpty()) &&
                (linkField == null || linkField.isEmpty()) &&
                engineField.isEmpty() &&
                systemReqsField.isEmpty() &&
                (gameDevField == null || gameDevField.isEmpty()) &&
                (gameStudioField == null || gameStudioField.isEmpty()) &&
                (gamePublisherField == null || gamePublisherField.isEmpty()) &&
                (modeField == null || modeField.isEmpty()) &&
                (platformField == null || platformField.isEmpty());
    }

    private void addWindowListener(){
        javafx.application.Platform.runLater(() -> {
            Stage stage = null;

            if (isUpdateMode) {
                stage = UpdateArchive.window;
            } else {
                stage = AddArchive_v2.window;
            }

            if (stage != null) {
                stage.setOnCloseRequest(event -> {
                    event.consume();
                    handleExitAttempt();
                });
            }
        });
    }

    private void handleExitAttempt(){
        if(!checkEmptyField()){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Unsaved Changes");
            alert.setHeaderText("Wait a minute!");
            alert.setContentText("You have unsaved inputs on this form. Do you wish to cancel and lose your progress?");

            DialogPane dialogPane = alert.getDialogPane();
            try {
                Image logo = new Image(getClass().getResourceAsStream("/edu/tangingina/thebackroom/assets/tbr.png"));
                ImageView icon = new ImageView(logo);
                icon.setFitHeight(50);
                icon.setFitWidth(50);
                icon.setPreserveRatio(true);
                alert.setGraphic(icon);

                String cssPath = getClass().getResource("/edu/tangingina/thebackroom/the_backroom_style.css").toExternalForm();
                dialogPane.getStylesheets().add(cssPath);
                dialogPane.getStyleClass().add("custom-dialog");
            } catch (Exception e) {
                System.out.println("Could not load CSS for dialog: " + e.getMessage());
            }

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                if(isUpdateMode){
                    UpdateArchive.closeWindow();
                }else{
                    AddArchive_v2.exitWindow();
                }
            } else {
                System.out.println("User chose to stay.");
            }
        }else{
            if(isUpdateMode){
                UpdateArchive.closeWindow();
            }else{
                AddArchive_v2.exitWindow();
            }
        }
    }
}
