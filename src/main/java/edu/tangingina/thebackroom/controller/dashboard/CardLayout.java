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
        - includes media cover in details page
     */

    private final String title;
    private final String imagePath;
    private ImageView cover, imgView;
    private Image img;
    private Label altText;

    private final double fitWid;
    private final double fitHei;
    //private final boolean clickable;

    public CardLayout(String title, String imagePath) {
        this(title, imagePath, 130, 205, true, null);
    }

    public CardLayout(String title, String imagePath, double width, double height, boolean showTitle) {
        this(title, imagePath, width, height, showTitle, null);
    }

    public CardLayout(String title, String imagePath, double fitWid, double fitHei, boolean showTitle, Runnable onClick) {
        this.title = title;
        this.imagePath = imagePath;
        this.fitWid = fitWid;
        this.fitHei = fitHei;

        if (onClick != null) {
            this.setCursor(Cursor.HAND);
            this.setOnMouseClicked(e -> {
                onClick.run();
            });
        }

        buildLayout();
    }

    private void buildLayout() {
        this.getStyleClass().add("card-layout");
        this.setAlignment(Pos.CENTER);

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
            imgView.setFitWidth(fitWid);
            imgView.setFitHeight(fitHei);
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

        altCard.setPrefSize(fitWid, fitHei);
        altCard.setMinSize(fitWid, fitHei);
        altCard.setMaxSize(fitWid, fitHei);

        altText = new Label(title);
        altText.setFont(FontLoader.extra(25));
        altText.getStyleClass().add("card-alt-text");
        altText.setWrapText(true);
        altText.setAlignment(Pos.CENTER);

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
