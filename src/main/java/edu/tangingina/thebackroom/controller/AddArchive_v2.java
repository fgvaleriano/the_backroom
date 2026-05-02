package edu.tangingina.thebackroom.controller;

import edu.tangingina.thebackroom.TheBackroom;
import edu.tangingina.thebackroom.controller.dashboard.BaseView;
import edu.tangingina.thebackroom.controller.dashboard.DashboardShell;
import edu.tangingina.thebackroom.util.*;
import javafx.fxml.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.event.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.stage.*;


public class  AddArchive_v2 {
    private static Scene scene;
    private static ComboBox<String> mediaTypeSelector;
    private static StackPane bkgCard;
    public static Stage window;
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

    public static void addArchiveView() {

        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Add");
        window.setResizable(false);

        StackPane root = getBkgCard();
        innerCard = getInnerCard();                         //inner beige card

        formContent = new VBox(20);
        formContent.setAlignment(Pos.TOP_LEFT);
        formContent.setPadding(new Insets(20, 40, 30, 40));

        inputHolder = getInputHolder();                     //scrolable pane for the formContent
        inputHolder.setContent(formContent);

        //---- HEADER COMPONENT ----
        header = new Label("Add Archive");
        header.setFont(FontLoader.extra(30));
        header.getStyleClass().add("header-text");
        header.setMaxWidth(Double.MAX_VALUE);
        header.setAlignment(Pos.CENTER);

        //combo box for media type selection and dynamic box after it
        mediaTypeRow = getMediaType();
        dynamicForm = getDynamicForm();

        formContent.getChildren().addAll(mediaTypeRow, dynamicForm);
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
        Scene sc = TheBackroom.sm.getMainScene();

        Node found = sc.lookup(".dashboard-shell");
        if(found instanceof DashboardShell shell){
            shell.refreshCurrentView();

        }
    }

    //this is for if user try to exit the add detail pane, we ask user if wish to continue or lose progress
    public static void exitWindow(){
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
    private static VBox getDynamicForm() {
        dynamicForm = new VBox();
        dynamicForm.getStyleClass().add("form-content");
        dynamicForm.setAlignment(Pos.TOP_LEFT);
        dynamicForm.setPadding(new Insets(10, 0,0, 15));

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
            case "Film" -> {
                filmDetailsForm = new FilmDetailsForm();
                dynamicForm.getChildren().addAll(
                        filmDetailsForm.getView()
                );
            }
            case "TV Show" -> {
                tvShowDetailsForm = new TVShowDetailsForm();
                dynamicForm.getChildren().addAll(
                        tvShowDetailsForm.getView()
                );
            }
        }
    }

}
