package edu.tangingina.thebackroom.controller.dashboard;

import edu.tangingina.thebackroom.util.FontLoader;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;

import java.io.File;
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

    private ImageView createCover(String imagePath) {
        try {
            String path;

            var resource = getClass().getResource(imagePath);

            if (resource != null) {
                path = resource.toExternalForm();
            } else {
                //this part since we have two kuan na pag put han img cover, you resources and mine is outside so we
                //have this  kuan la anay holder
                File file = new File(imagePath);

                if (file.exists()) {
                    path = file.toURI().toString();
                } else {
                    System.out.println("Missing card cover: " + imagePath);
                    System.out.println("Looked at: " + file.getAbsolutePath());
                    return null;
                }
            }

            img = new Image(path, true);
            imgView = new ImageView(img);
            imgView.setFitWidth(150);
            imgView.setFitHeight(225);
            imgView.setPreserveRatio(false);
            imgView.getStyleClass().add("cards");

            return imgView;

        } catch (Exception e) {
            System.err.println("Error processing image path: " + imagePath);
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
