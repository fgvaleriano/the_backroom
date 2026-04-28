package edu.tangingina.thebackroom.controller.dashboard.Media_Details;

import edu.tangingina.thebackroom.controller.dashboard.CardLayout;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.List;
import java.util.function.*;

public class BookPage extends VBox {
    /*
        Accepts a click action that opens the details page of the book
     */

    private final Consumer<BaseMedia> onMediaClicked;

    public BookPage(Consumer<BaseMedia> onMediaClicked) {
        this.onMediaClicked = onMediaClicked;

        buildPage();
    }

    private void buildPage() {
        this.getStyleClass().add("books-page");
        this.setAlignment(Pos.TOP_CENTER);
        this.setSpacing(30);
        this.setPadding(new Insets(50, 80, 80, 80));

        Label title = new Label("Books");
        title.getStyleClass().add("page-title");

        FlowPane bookGrid = new FlowPane();
        bookGrid.getStyleClass().add("book-grid");
        bookGrid.setAlignment(Pos.TOP_CENTER);
        bookGrid.setHgap(35);
        bookGrid.setVgap(35);

        List<BaseMedia> books = List.of(
                SampleMediaData.getSampleBook()
        );

        for (BaseMedia book : books) {
            CardLayout card = new CardLayout(
                    book.getTitle(),
                    book.getImagePath()
            );

            card.setOnMouseClicked(event -> {
                onMediaClicked.accept(book);
            });

            bookGrid.getChildren().add(card);
        }

        this.getChildren().addAll(title, bookGrid);
    }
}
