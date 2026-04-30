package edu.tangingina.thebackroom.controller;

import edu.tangingina.thebackroom.util.DatabaseManager;
import edu.tangingina.thebackroom.util.FileManager;
import edu.tangingina.thebackroom.util.FontLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UpdateArchive {
    /*
        Handles the logic and UI for the update media archive function
     */

    private static Scene scene;
    private static ComboBox<String> mediaTypeSelector;
    private static StackPane bkgCard;
    private static Stage window;
    private static VBox innerCard, formContent, dynamicForm;
    private static ScrollPane inputHolder, sp;
    private static Label header, mediaTypeLabel, inputLabel, label;
    private static HBox mediaTypeRow, row;

    //modular forms that handles input
    private static BaseMediaForm baseMediaForm;
    private static BookDetailsForm bookDetailsForm;
    private static GameDetailsForm gameDetailsForm;
    private static FilmDetailsForm filmDetailsForm;
    private static TVShowDetailsForm tvShowDetailsForm;

    public static void updateArchiveView(int mediaId, String mediaType) {

        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Update Media");
        window.setResizable(false);

        StackPane root = getBkgCard();
        innerCard = getInnerCard();                         //inner beige card

        formContent = new VBox(20);
        formContent.setAlignment(Pos.TOP_LEFT);
        formContent.setPadding(new Insets(20, 40, 30, 40));

        inputHolder = getInputHolder();                     //scrolable pane for the formContent
        inputHolder.setContent(formContent);

        //---- HEADER COMPONENT ----
        header = new Label("Update Archive");
        header.setFont(FontLoader.extra(30));
        header.getStyleClass().add("header-text");
        header.setMaxWidth(Double.MAX_VALUE);
        header.setAlignment(Pos.CENTER);

        mediaTypeRow = getMediaType();

        mediaTypeSelector.setValue(mediaType);
        mediaTypeSelector.setDisable(true);

        dynamicForm = getDynamicForm(mediaType);

        //pulling data from db and populating form
        loadDataFromDB(mediaId, mediaType);

        formContent.getChildren().add(dynamicForm);
        innerCard.getChildren().addAll(header, inputHolder);
        root.getChildren().add(innerCard);

        StackPane.setAlignment(innerCard, Pos.CENTER);

        scene = new Scene(root, 950, 600);
        scene.getStylesheets().add(AddArchive_v2.class.getResource(
                "/edu/tangingina/thebackroom/the_backroom_style.css").toExternalForm());

        window.setScene(scene);
        window.showAndWait();
    }

    //closing dialog box
    public static void closeWindow() {
        window.close();
    }

    //dark background
    private static StackPane getBkgCard() {
        bkgCard = new StackPane();
        bkgCard.getStyleClass().add("card-bkg");

        return bkgCard;
    }

    //beige background that holds the scrollable pane
    private static VBox getInnerCard() {
        innerCard = new VBox();
        innerCard.getStyleClass().add("card-inner");
        innerCard.setPrefWidth(700);
        innerCard.setMaxSize(890, 530);
        innerCard.setFillWidth(true);

        return innerCard;
    }

    //scrollpane for the input fields
    private static ScrollPane getInputHolder() {
        sp = new ScrollPane();
        sp.getStyleClass().add("input-holder");
        sp.setFitToWidth(true);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        VBox.setVgrow(sp, Priority.ALWAYS);                 //scrollpane expand to take up the rest of the card
        return sp;
    }

    //media type selector
    private static HBox getMediaType() {
        mediaTypeLabel = new Label("Media Type");
        mediaTypeLabel.setFont(FontLoader.regular(18));
        mediaTypeLabel.setFont(FontLoader.bold(18));
        mediaTypeLabel.getStyleClass().add("input-label");

        //combo box that shows what type of media user will add
        mediaTypeSelector = new ComboBox<>();
        mediaTypeSelector.getItems().addAll("Book", "Game", "Film", "TV Show");
        mediaTypeSelector.setPromptText("Select Media Type");
        mediaTypeSelector.getStyleClass().add("combo-box");

        //dyanmic listener
        mediaTypeSelector.setOnAction(e -> {
            String selected = mediaTypeSelector.getValue();
            System.out.println("Media type: " + selected);
            updateDynamicForm(selected);                             //form for archive input
        });

        row = new HBox(20, mediaTypeLabel, mediaTypeSelector);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(10, 0, 0, 0));

        return row;
    }

    //dynamic container that changes depending on the type of media selected
    private static VBox getDynamicForm(String mediaType) {
        dynamicForm = new VBox();
        dynamicForm.getStyleClass().add("form-content");
        dynamicForm.setAlignment(Pos.TOP_LEFT);
        dynamicForm.setPadding(new Insets(10, 0,0, 15));

        updateDynamicForm(mediaType);

        return dynamicForm;
    }

    //form updates depending on the type of media that wil be added
    private static void updateDynamicForm(String selectedType) {
        dynamicForm.getChildren().clear();

        if (selectedType == null) {
            return;
        }

        switch (selectedType) {
            case "Book" -> {
                System.out.println("");
                bookDetailsForm = new BookDetailsForm();
                dynamicForm.getChildren().addAll(
                        bookDetailsForm.getView()
                );
            }
            case "Game" -> {
                gameDetailsForm = new GameDetailsForm();
                dynamicForm.getChildren().addAll(
                        gameDetailsForm.getView()
                );
            }
            case "Movie" -> {
                filmDetailsForm = new FilmDetailsForm();
                dynamicForm.getChildren().addAll(
                        filmDetailsForm.getView()
                );
            }
            case "TvShow" -> {
                tvShowDetailsForm = new TVShowDetailsForm();
                dynamicForm.getChildren().addAll(
                        tvShowDetailsForm.getView()
                );
            }
        }
    }

    private static void loadDataFromDB(int id, String type) {
        try {
            ResultSet rs = FileManager.getMediaData(id);
            if (rs != null && rs.next()) {
                switch (type) {
                    case "Book" -> {
                        String author = FileManager.getPersonnelName(id, "Author");
                        String publisher = FileManager.getCompanyName(id, "Publisher");
                        String category = FileManager.getCategory(id);
                        bookDetailsForm.populateForm(rs, author, category, publisher);
                    }
                    case "TvShow" -> {
                        String director = FileManager.getPersonnelName(id, "Director");
                        String studio = FileManager.getCompanyName(id, "Production Studio");
                        String category = FileManager.getCategory(id);
                        tvShowDetailsForm.populateForm(rs, director, studio, category);
                    }
                    case "Movie" -> {
                        String director = FileManager.getPersonnelName(id, "Director");
                        String studio = FileManager.getCompanyName(id, "Production Studio");
                        String category = FileManager.getCategory(id);
                        filmDetailsForm.populateForm(rs, director, studio, category);

                    }
                    case "Game" -> {
                        String dev = FileManager.getPersonnelName(id, "Game Developer");
                        String studio = FileManager.getCompanyName(id, "Game Studio");
                        String category = FileManager.getCategory(id);
                        String mode = FileManager.getCategory(id);
                        String platform = FileManager.getPlatform(id);
                        gameDetailsForm.populateForm(rs, dev, studio, category, mode, platform);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error loading data from DB: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
