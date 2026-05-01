package edu.tangingina.thebackroom.controller;

import edu.tangingina.thebackroom.TheBackroom;
import edu.tangingina.thebackroom.dao.impl.MediaDaoImpl;
import edu.tangingina.thebackroom.model.*;
import edu.tangingina.thebackroom.util.FileManager;
import edu.tangingina.thebackroom.util.Utility;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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

    public GameDetailsForm() {
        view.getChildren().addAll(formColumn());

        titleField = FormFieldFactory.createTextField("Title", 520);
        gameDevField = FormFieldFactory.createMultiValueField("Game Developer", 120);
        gameStudioField = FormFieldFactory.createMultiValueField("Game Studio", 120);
        gamePublisherField = FormFieldFactory.createMultiValueField("Game Publisher", 120);
        modeField = FormFieldFactory.createMultiValueField("Game Mode", 120);
        platformField = FormFieldFactory.createMultiValueField("Game Platform", 120);
        engineField = FormFieldFactory.createTextField("Game Engine", 120);
        genreField = FormFieldFactory.createMultiValueField("Genre", 120);
        linkField = FormFieldFactory.createAccessLinkField("Access Link");
        widgetField = FormFieldFactory.createImageFileField("Cover Art", 200);
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
    }

    private Node addButton(){
        btn = new Button();
        btn.getStyleClass().add("image-button");

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
        Image img = new Image(getClass().getResourceAsStream(
                "/edu/tangingina/thebackroom/assets/add_btn.png"));
        ImageView view = new ImageView(img);
        view.setPreserveRatio(true);
        view.setFitWidth(125);

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
                             String platform, String links) {
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

            linkField.setLink(links);

            String path = rs.getString("icon_path");
            widgetField.setImage(path);

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
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
            //Show Output Situation
            AddArchive_v2.closeWindow();

        }catch (Exception e1){
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
    }
}
