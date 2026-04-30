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

    private final String title;
    private final String imagePath;
    private ImageView cover, imgView;
    private Image img;
    private Label altText;
    private int width, height;
    private boolean isHoverable; // NEW FLAG

    // Constructor for details page (custom size + NO HOVER)
    public CardLayout(String title, String imagePath, int width, int height, boolean isHoverable) {
        this.title = title;
        this.imagePath = imagePath;
        this.width = width;
        this.height = height;
        this.isHoverable = isHoverable;
        buildLayout();
    }

    // Constructor for backwards compatibility
    public CardLayout(String title, String imagePath, int width, int height) {
        this(title, imagePath, width, height, false); // Defaults to NO HOVER
    }

    // Constructor for Homepage (standard size + HOVER ON)
    public CardLayout(String title, String imagePath) {
        this.title = title;
        this.imagePath = imagePath;
        this.width = 150;
        this.height = 225;
        this.isHoverable = true; // Defaults to HOVER ON
        buildLayout();
    }

    private void buildLayout() {
        this.getStyleClass().add("card-layout");
        this.setAlignment(Pos.CENTER);

        // Only show hand cursor if it's meant to be clicked
        if (isHoverable) {
            this.setCursor(Cursor.HAND);
        } else {
            this.setCursor(Cursor.DEFAULT);
        }

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
                File file = new File(imagePath);
                if (file.exists()) {
                    path = file.toURI().toString();
                } else {
                    System.out.println("Missing card cover: " + imagePath);
                    return null;
                }
            }

            img = new Image(path, true);
            imgView = new ImageView(img);
            imgView.setFitWidth(width);
            imgView.setFitHeight(height);
            imgView.setPreserveRatio(false);

            // Apply different CSS based on hover flag
            if (isHoverable) {
                imgView.getStyleClass().add("cards");
            } else {
                imgView.getStyleClass().add("cards-static");
            }

            return imgView;

        } catch (Exception e) {
            System.err.println("Error processing image path: " + imagePath);
            return null;
        }
    }

    public StackPane createAltCard() {
        StackPane altCard = new StackPane();

        // Apply different CSS based on hover flag
        if (isHoverable) {
            altCard.getStyleClass().add("card-alt");
        } else {
            altCard.getStyleClass().add("card-alt-static");
        }

        altText = new Label(title);
        altText.setFont(FontLoader.extra(25));
        altText.getStyleClass().add("card-alt-text");

        altCard.getChildren().add(altText);
        return altCard;
    }

    public String getTitle() { return title; }
    public String getImagePath() { return imagePath; }
}