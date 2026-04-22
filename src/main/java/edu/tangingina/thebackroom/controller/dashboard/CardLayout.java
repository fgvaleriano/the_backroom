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
            altText = new Label(title);
            altText.setFont(FontLoader.bold(15));
            this.getChildren().add(altText);
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
            imgView.setFitWidth(120);
            imgView.setFitHeight(180);
            imgView.setPreserveRatio(false);
            imgView.getStyleClass().add("cards");

            return imgView;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getTitle() {
        return title;
    }

    public String getImagePath() {
        return  imagePath;
    }
}
