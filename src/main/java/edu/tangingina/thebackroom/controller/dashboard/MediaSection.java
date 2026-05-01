package edu.tangingina.thebackroom.controller.dashboard;

import edu.tangingina.thebackroom.TheBackroom;
import edu.tangingina.thebackroom.util.FontLoader;
import edu.tangingina.thebackroom.util.MediaItem;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;

import java.util.List;
import java.util.function.Consumer;

public class MediaSection extends VBox {
    /*
        Section for different types of media
     */

    private Label sectionTitle;
    private FlowPane cardContainer;

    private Consumer<Integer> onMediaSelected;

    public MediaSection(String title, Consumer<Integer> onMediaSelected) {
        this.onMediaSelected = onMediaSelected;
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

    public void addCard(String title, String imagePath) {
        String mediaTitle = TheBackroom.mediaList.get(Integer.valueOf(title)).getMediaName();

        //we use the media title here so that if no img we set the name of the media on the empty card
        CardLayout card = new CardLayout(mediaTitle, imagePath);

        card.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {

                if (onMediaSelected != null) {
                    onMediaSelected.accept(Integer.valueOf(title));
                }
                //We switch scenes here
            }
        });

        cardContainer.getChildren().add(card);
    }
}
