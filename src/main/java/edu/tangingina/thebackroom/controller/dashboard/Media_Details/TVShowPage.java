package edu.tangingina.thebackroom.controller.dashboard.Media_Details;

import edu.tangingina.thebackroom.controller.dashboard.CardLayout;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.util.List;
import java.util.function.Consumer;

public class TVShowPage extends VBox {
    /*
        Accepts click action that opens the details page fo a film
     */

    private final Consumer<BaseMedia> onMediaClicked;
    private List<FilmMedia> films;

    public TVShowPage(Consumer<BaseMedia> onMediaClicked) {
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

        films = SampleMediaData.getSampleFilms();

        for (FilmMedia film : SampleMediaData.getSampleFilms()) {
            CardLayout card = new CardLayout(
                    film.getTitle(),
                    film.getImagePath()
            );

            card.setOnMouseClicked(e -> {
                onMediaClicked.accept(film);
            });

            bookGrid.getChildren().add(card);
        }

        this.getChildren().addAll(title, bookGrid);
    }
}
