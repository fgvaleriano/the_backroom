package edu.tangingina.thebackroom.controller;

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
    private static Image btnImage;
    private static ComboBox<String> mediaTypeSelector;
    private static StackPane bkgCard;
    private static Stage window;
    private static VBox innerCard, formContent, dynamicForm;
    private static ScrollPane inputHolder, sp;
    private static Label header, mediaTypeLabel, inputLabel, label;
    private static Button addBtn;
    private static HBox mediaTypeRow, row;
    private static TextField archiveInput;
    private static TextArea textArea;


    public static void addArchiveView() {

        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Add");
        window.setResizable(false);

        StackPane root = getBkgCard();
        innerCard = getInnerCard();                         //inner beige card

        formContent = new VBox(20);
        formContent.setAlignment(Pos.TOP_CENTER);
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

        //---- ADD BUTTON ----
        /*addBtn = new Button();
        addBtn.getStyleClass().add("image-button");
        btnImage = new Image(AddArchive_v2.class.getResourceAsStream(
                "edu/tangingina/thebackroom/assets/add_btn.png"));
        ImageView imageView = new ImageView(btnImage);
        imageView.setFitWidth(150);
        imageView.setPreserveRatio(true);*/

        //will be hidden until media is selected
        //addBtn.setVisible(false);
        //addBtn.setManaged(false);

        formContent.getChildren().addAll(mediaTypeRow, dynamicForm);
        innerCard.getChildren().addAll(header, inputHolder);
        root.getChildren().add(innerCard);

        StackPane.setAlignment(innerCard, Pos.CENTER);

        scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(AddArchive_v2.class.getResource(
                "/edu/tangingina/thebackroom/the_backroom_style.css").toExternalForm());

        window.setScene(scene);
        window.showAndWait();
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
        innerCard.setMaxSize(700, 500);
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
        mediaTypeLabel.setFont(FontLoader.regular(15));
        mediaTypeLabel.getStyleClass().add("input-label");

        //combo box that shows what type of media user will add
        mediaTypeSelector = new ComboBox<>();
        mediaTypeSelector.getItems().addAll("Book", "Game", "Film", "TV Show");
        mediaTypeSelector.setPromptText("Select Media Type");
        //set font and color later

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
        dynamicForm.setPadding(new Insets(10, 0,0, 140));

        return dynamicForm;
    }

    //form updates depending on the type of media that wil be added
    private static void updateDynamicForm(String selectedType) {
        dynamicForm.getChildren().clear();

        if (selectedType == null) {
            return;
        }

        switch (selectedType) {
            case "Book" -> dynamicForm.getChildren().addAll(
                    createField("Title"),
                    createField("Author"),
                    createField("Synopsis"),
                    createField("Publish Date"),
                    createField("Genre"),
                    createField("ISBN"),
                    createField("Page Count"),
                    createField("Edition")
            );

            case "Game" -> dynamicForm.getChildren().addAll(
                    createField("Title"),
                    createField("Author"),
                    createField("Synopsis"),
                    createField("Publish Date"),
                    createField("Genre"),
                    createField("ISBN"),
                    createField("Page Count"),
                    createField("Edition")
            );

            case "Film" -> dynamicForm.getChildren().addAll(
                    createField("Title"),
                    createField("Author"),
                    createField("Synopsis"),
                    createField("Publish Date"),
                    createField("Genre"),
                    createField("ISBN"),
                    createField("Page Count"),
                    createField("Edition")
            );

            case "TV Show" -> dynamicForm.getChildren().addAll(
                    createField("Title"),
                    createField("Author"),
                    createField("Synopsis"),
                    createField("Publish Date"),
                    createField("Genre"),
                    createField("ISBN"),
                    createField("Page Count"),
                    createField("Edition")
            );
        }
    }

    //input fields
    private static VBox createField (String labelText) {
        inputLabel = new Label(labelText);
        archiveInput = new TextField();
        inputLabel.getStyleClass().add("input-label");

        VBox fieldBox = new VBox(8, inputLabel, archiveInput);
        fieldBox.setMaxWidth(320);

        return fieldBox;
    }

    private static VBox createTextAreaField(String labelText) {
        Label label = new Label(labelText);
        TextArea textArea = new TextArea();
        textArea.setPromptText("Enter " + labelText);
        textArea.setPrefRowCount(4);
        textArea.setWrapText(true);

        VBox fieldBox = new VBox(6, label, textArea);
        fieldBox.setMaxWidth(420);

        return fieldBox;
    }

}
