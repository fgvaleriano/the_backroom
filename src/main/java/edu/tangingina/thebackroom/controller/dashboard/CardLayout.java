package edu.tangingina.thebackroom.controller.dashboard;

import edu.tangingina.thebackroom.util.FontLoader;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;

import java.net.URL;

public class CardLayout extends StackPane {
    /*
        Handles cards (media cover) layout and clickable containers
     */

    private final String title;
    private final String imagePath;
    private ImageView cover, imgView;
    private Image img;
    private Label altText;

    public CardLayout(String title, String imagePath) {
        this.title = title;
        this.imagePath = imagePath;

        buildLayout();
    }

    private void buildLayout() {
        this.getStyleClass().add("card-layout");
        this.setAlignment(Pos.CENTER);
        this.setCursor(Cursor.HAND);

        cover = createCover(imagePath);

        if (cover != null) {
            this.getChildren().add(cover);
        } else {
            this.getChildren().add(createAltCard());
        }
    }

    private ImageView createCover (String imagePath) {
        try {
            var path = getClass().getResource(imagePath);

            if (path == null) {
                System.out.println("Missing card cover: " + imagePath);
                return null;
            }

            img = new  Image(path.toExternalForm());
            imgView = new  ImageView(img);
            imgView.setFitWidth(150);
            imgView.setFitHeight(225);
            imgView.setPreserveRatio(false);
            imgView.getStyleClass().add("cards");

            return imgView;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //when card image is unaccessible
    public StackPane createAltCard() {
        StackPane altCard = new StackPane();
        altCard.getStyleClass().add("card-alt");

        altText = new Label(title);
        altText.setFont(FontLoader.extra(25));
        altText.getStyleClass().add("card-alt-text");

        altCard.getChildren().add(altText);
        return altCard;
    }

    public String getTitle() {
        return title;
    }

    public String getImagePath() {
        return  imagePath;
    }
}
