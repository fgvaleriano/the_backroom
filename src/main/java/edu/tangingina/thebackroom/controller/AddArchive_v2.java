package edu.tangingina.thebackroom.controller;

import edu.tangingina.thebackroom.util.*;
import javafx.fxml.*;
import javafx.geometry.Pos;
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
    private static Label header;
    private static Button addBtn;


    public static void addArchiveView() {

        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Add");
        window.setResizable(false);

        StackPane root = getBkgCard();
        innerCard = getInnerCard();                         //inner beige card

        formContent = new VBox(15);
        formContent.setStyle("-fx-padding: 10;");

        inputHolder = getInputHolder();                     //scrolable pane for the formContent
        inputHolder.setContent(formContent);

        //---- HEADER COMPONENT ----
        header = new Label("Add Archive");
        header.setFont(FontLoader.extra(30));
        header.getStyleClass().add("header-text");

        header.setMaxWidth(Double.MAX_VALUE);
        header.setAlignment(Pos.CENTER);

        //---- ADD BUTTON ----
        addBtn = new Button();
        addBtn.getStyleClass().add("image-button");
        btnImage = new Image(AddArchive_v2.class.getResourceAsStream(
                "edu/tangingina/thebackroom/assets/add_btn.png"));
        ImageView imageView = new ImageView(btnImage);
        imageView.setFitWidth(150);
        imageView.setPreserveRatio(true);

        //will be hidden until media is selected
        addBtn.setVisible(false);
        addBtn.setManaged(false);

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

        VBox.setVgrow(sp, Priority.ALWAYS);                 //scrollpane expand to take up the rest of the card
        return sp;
    }

    //media type selector
    private static ComboBox getMediaType() {
        mediaTypeSelector = new ComboBox<>();
        mediaTypeSelector.getItems().addAll("Books", "Movies", "Film and TV Show");
        mediaTypeSelector.setPromptText("Select Media Type");

        //dyanmic listener
        mediaTypeSelector.setOnAction(e -> {
            String selected = mediaTypeSelector.getValue();
            System.out.println("Media type: " + selected);
        });

        return mediaTypeSelector;
    }

    //dynamic container that changes depending on the type of media selected
    private static VBox getDynamicForm() {
        dynamicForm = new VBox();
        dynamicForm.getStyleClass().add("form-content");

        return dynamicForm;
    }

}
