package edu.tangingina.thebackroom.controller.dashboard;

import edu.tangingina.thebackroom.model.Media;
import edu.tangingina.thebackroom.model.MediaType;
import edu.tangingina.thebackroom.util.FontLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.util.function.Consumer;

public class SearchView extends BaseView{
    /*
        This class handles the search UI
            - filters by media
     */

    private VBox resultSection, card;
    private TextField searchField;
    private ComboBox<String> filter;
    private FlowPane results;
    private Label header;
    private final Consumer<Integer> onMediaSelected;

    public SearchView(Consumer<Integer> onMediaSelected) {
        this.onMediaSelected = onMediaSelected;
        buildLayout();
    }

    @Override
    protected void buildLayout() {
        root.setAlignment(Pos.TOP_LEFT);
        root.setPadding(new Insets(40, 40, 40, 200));
        root.getChildren().addAll(createSearchBar(), createResultSection());
    }

    //creating search bar
    private HBox createSearchBar() {
        filter = new ComboBox<>();
        filter.getItems().addAll("All", "Book", "Game", "Film", "TV Show");
        filter.setValue("All");
        filter.getStyleClass().add("combo-box");
        filter.setPrefHeight(35);

        searchField = new TextField();
        searchField.setFont(FontLoader.regular(18));
        searchField.setPromptText("Enter to Search");
        searchField.getStyleClass().add("input-field");
        searchField.setPrefHeight(40);
        searchField.setPrefWidth(520);

        Button btn = new Button();
        btn.getStyleClass().add("image-button");
        Image img = new Image(ImportDialog.class.getResourceAsStream(
                "/edu/tangingina/thebackroom/assets/search.png"));
        ImageView view = new ImageView(img);
        view.setPreserveRatio(true);
        view.setFitWidth(125);

        btn.setGraphic(view);

        btn.setOnAction(e -> {
            System.out.println("Search button clicked");
            if (searchField.getText().trim().isEmpty()) {
                searchField.getStyleClass().add("input-field-error");
            } else {
                searchField.getStyleClass().remove("input-field-error");
                handleSearch();
            }
        });

        HBox searchBar = new HBox(12, filter, searchField, btn);
        searchBar.setAlignment(Pos.CENTER);
        return searchBar;
    }

    //result section, only appears after search btn is clicked
    private VBox createResultSection() {
        header = new Label("Results");
        header.setFont(FontLoader.extra(28));
        header.getStyleClass().add("result-header-text");
        header.setVisible(false);
        header.setManaged(false);

        results = new FlowPane();
        results.setHgap(24);
        results.setVgap(24);
        results.setPadding(new Insets(30,0,0,0));

        resultSection = new VBox(16, header, results);
        resultSection.setPadding(new Insets(30, 0, 0, 0));
        resultSection.setAlignment(Pos.TOP_LEFT);
        return resultSection;
    }

    //search logic
    private void handleSearch() {
        //TEMPORARY CHANGE IN FINAL
        results.getChildren().clear();

        header.setVisible(true);
        header.setManaged(true);

        String[] mockTitles = {
                "The Invisible Life of Addie LaRue",
                "No Longer Human",
                "Lord of the Flies",
                "The Hobbit",
                "Dune",
                "1984"
        };

        for (String mockTitle : mockTitles) {
            results.getChildren().add(buildMockCard(mockTitle, mockTitle));
        }
    }

    //TEMPORARY CHANGE AND DELETE DO NOT COPY FORMAT, STICK TO OG
    private CardLayout buildMockCard(String title, String imagePath) {
        CardLayout card = new CardLayout(title, imagePath);
        return card;
    }

    /*private boolean matchesFilter(MediaType type, String filter) {
        return switch (filter) {
            case "All" -> true;
            case "Book" -> type == MediaType.Book;
            case "Game" -> type == MediaType.Game;
            case "Film" -> type == MediaType.Movie;
            case "TV Show" -> type == MediaType.TvShow;
        };
    }*/

    //empty state when search is not clicked
    private void showEmpty(String query) {
        results.setVisible(true);
        results.setManaged(true);

        Label empty = new Label ("No results found for \"" + query + "\"");
        empty.setFont(FontLoader.bold(16));
        empty.getStyleClass().add("header-text");
        empty.setPadding(new Insets(20, 0,0,0));
        results.getChildren().add(empty);
    }

    private VBox buildMediaCard(Media media) {
        card = new VBox(8);
        card.getStyleClass().add("media-card-holder");
        card.setAlignment(Pos.TOP_CENTER);
        card.setPrefWidth(160);
        card.setMaxWidth(160);

        ImageView cover = new ImageView();
        cover.setFitWidth(150);
        cover.setFitHeight(210);
        cover.setPreserveRatio(true);
        cover.getStyleClass().add("cards");

        try {
            if (media.getMediaIcon() != null && !media.getMediaIcon().isEmpty()) {
                cover.setImage(new Image("file: " + media.getMediaIcon(), true));
            }
        } catch (Exception e) {
            System.out.println("Cannot access image");
        }

        card.getChildren().add(cover);
        card.setOnMouseClicked(e -> onMediaSelected.accept(media.getID()));
        return card;
    }

}