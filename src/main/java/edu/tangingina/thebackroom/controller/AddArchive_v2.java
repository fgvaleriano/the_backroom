package edu.tangingina.thebackroom.controller;

import edu.tangingina.thebackroom.util.FontLoader;
import javafx.fxml.*;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.event.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;


public class  AddArchive_v2 {
    private static Scene scene;
    private static StackPane bkgCard;
    private static Stage window;
    private static VBox innerCard, formContent;
    private static ScrollPane inputHolder, sp;
    private static Label header;


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

        //header component, "Add Archive" text
        header = new Label("Add Archive");
        header.setFont(FontLoader.extra(30));
        header.getStyleClass().add("header-text");

        header.setMaxWidth(Double.MAX_VALUE);
        header.setAlignment(Pos.CENTER);

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
}
