package edu.tangingina.thebackroom.controller.dashboard.Media_Details;

import edu.tangingina.thebackroom.controller.dashboard.CardLayout;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.function.Consumer;

public class GamePage extends VBox {
    /*
        Accepts click action that opens the details page fo a game
     */

    private final Consumer<BaseMedia> onMediaClicked;
    private List<GameMedia> games;

    public GamePage(Consumer<BaseMedia> onMediaClicked) {
        this.onMediaClicked = onMediaClicked;
        buildPage();
    }

    private void buildPage() {
        this.getStyleClass().add("books-page");
        this.setAlignment(Pos.TOP_CENTER);
        this.setSpacing(30);
        this.setPadding(new Insets(50, 80, 80, 80));

        Label title = new Label("Films and TV Shows");
        title.getStyleClass().add("page-title");

        FlowPane bookGrid = new FlowPane();
        bookGrid.getStyleClass().add("book-grid");
        bookGrid.setAlignment(Pos.TOP_CENTER);
        bookGrid.setHgap(35);
        bookGrid.setVgap(35);

        //change this
        games = SampleMediaData.getSampleGames();

        for (GameMedia game : SampleMediaData.getSampleGames()) {
            CardLayout card = new CardLayout(
                    game.getTitle(),
                    game.getImagePath()
            );

            card.setOnMouseClicked(e -> {
                onMediaClicked.accept(game);
            });

            bookGrid.getChildren().add(card);
        }

        this.getChildren().addAll(title, bookGrid);
    }
}


