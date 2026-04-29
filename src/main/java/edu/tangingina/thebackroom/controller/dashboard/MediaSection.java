package edu.tangingina.thebackroom.controller.dashboard;

import edu.tangingina.thebackroom.controller.dashboard.Media_Details.BaseMedia;
import edu.tangingina.thebackroom.util.FontLoader;
import edu.tangingina.thebackroom.util.MediaItem;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.List;

public class MediaSection extends VBox {
    /*
        Section for different types of media
     */

    private Label sectionTitle;
    private FlowPane cardContainer;

    public MediaSection(String title) {
        buildLayout(title);
    }

    private void buildLayout(String title) {
        this.getStyleClass().add("media-section");
        this.setAlignment(Pos.TOP_LEFT);

        //subgroup title for different medias0
        sectionTitle = new Label(title);
        sectionTitle.setFont(FontLoader.extra(20));
        sectionTitle.setFont(FontLoader.extra(34));
        sectionTitle.getStyleClass().add("media-section-title");

        //handles cards
        cardContainer = new FlowPane();
        cardContainer.getStyleClass().add("media-card-holder");
        cardContainer.setHgap(38);
        cardContainer.setVgap(28);

        this.getChildren().addAll(sectionTitle, cardContainer);
    }

    public void addCard(BaseMedia media, Runnable onClick) {
        CardLayout card = new CardLayout(
                media.getTitle(),
                media.getImagePath(),
                130,
                205,
                true,
                onClick
        );

        cardContainer.getChildren().add(card);
    }

    public void addCard(String title, String imagePath) {
        CardLayout card = new CardLayout(
                title,
                imagePath,
                130,
                205,
                true
        );

        cardContainer.getChildren().add(card);
    }
}
