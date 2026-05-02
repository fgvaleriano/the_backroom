package edu.tangingina.thebackroom.controller.dashboard;

import edu.tangingina.thebackroom.TheBackroom;
import edu.tangingina.thebackroom.model.Category;
import edu.tangingina.thebackroom.model.Media;
import edu.tangingina.thebackroom.model.MediaType;
import edu.tangingina.thebackroom.util.FontLoader;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.util.function.Consumer;
import java.util.*;

public class SearchView extends BaseView{
    /*
        This class handles the search UI
            - filters by media
     */

    private VBox resultSection, card;
    private TextField searchField;
    private ComboBox<String> filter, filterGenre;
    private FlowPane results;
    private Label header;
    private final Consumer<Integer> onMediaSelected;

    private Timeline timeCounter;

    private ArrayList<String> bookGenre, movieGenre, tvShowGenre, gameGenre;

    public SearchView(Consumer<Integer> onMediaSelected) {
        this.onMediaSelected = onMediaSelected;

        try{
            List<String> bookData = TheBackroom.mediaDao.getTopMediaCategory("Book");
            List<String> movieData = TheBackroom.mediaDao.getTopMediaCategory("Movie");
            List<String> tvData = TheBackroom.mediaDao.getTopMediaCategory("TvShow");
            List<String> gameData = TheBackroom.mediaDao.getTopMediaCategory("Game");

            bookGenre = new ArrayList<>(bookData);
            movieGenre = new ArrayList<>(movieData);
            tvShowGenre = new ArrayList<>(tvData);
            gameGenre = new ArrayList<>(gameData);

            bookGenre.add(0, "All");
            movieGenre.add(0, "All");
            tvShowGenre.add(0, "All");
            gameGenre.add(0, "All");
        }catch(Exception e){

        }
        buildLayout();
    }

    @Override
    protected void buildLayout() {
        root.setAlignment(Pos.TOP_LEFT);
        root.setPadding(new Insets(40, 40, 40, 200));
        root.getChildren().addAll(createSearchBar(), createResultSection());
        handleSearch();
    }

    //creating search bar
    private HBox createSearchBar() {
        filter = new ComboBox<>();
        filter.getItems().addAll("All", "Book", "Game", "Film", "TV Show");
        filter.setValue("All");
        filter.getStyleClass().add("combo-box");
        filter.setPrefHeight(35);

        filterGenre = new ComboBox<>();
        filterGenre.getStyleClass().add("combo-box");
        filterGenre.setVisible(false);
        filterGenre.setManaged(false);
        filterGenre.setPrefHeight(35);

        filter.setOnAction(e -> {
            String type = filter.getValue();
            if (type == null || type.equals("All")) {
                filterGenre.setVisible(false);
                filterGenre.setManaged(false);
                filterGenre.getItems().clear();
            } else {
                filterGenre.getItems().setAll(getGenreList(type));
                filterGenre.setValue("All");
                filterGenre.setVisible(true);
                filterGenre.setManaged(true);
            }

            handleSearch();
        });

        filterGenre.setOnAction(e -> {
            if(filterGenre.getValue() != null){
                handleSearch();
            }
        });

        searchField = new TextField();
        searchField.setFont(FontLoader.regular(18));
        searchField.setPromptText("Enter to Search");
        searchField.getStyleClass().add("input-field");
        searchField.setPrefHeight(40);
        searchField.setPrefWidth(520);

        searchField.textProperty().addListener(((observableValue, oldValue, newValue) -> {
            //this is the timer listening if the user stopped typing, if yes then we show search results
            if(timeCounter != null){
                timeCounter.stop();
            }

            timeCounter = new Timeline(
                    new KeyFrame(
                        Duration.millis(200), event ->{
                            handleSearch();
                        }
                    )
            );

            timeCounter.play();
        }));

        HBox searchBar = new HBox(12, filter, filterGenre, searchField);
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
        results.getChildren().clear();
        results.setVisible(true);
        results.setManaged(true);

        header.setVisible(true);
        header.setManaged(true);

        ArrayList<Integer> searchResult = new ArrayList<>();
        String name = searchField.getText().trim();
        String selectedGenre = filterGenre.getValue();

        MediaType mediaType = null;

        switch (filter.getValue()){
            case "Book":
                mediaType = MediaType.Book;
                break;

            case "Film":
                mediaType = MediaType.Movie;
                break;

            case "TV Show":
                mediaType = MediaType.TvShow;
                break;

            case "Game":
                mediaType = MediaType.Game;
                break;
        }

        for(Media m : TheBackroom.mediaList.values()){
            boolean nameMatch = (name == null ||
                    m.getMediaName().toLowerCase().startsWith(name.toLowerCase()));

            boolean typeMatch = (mediaType == null) ||
                    m.getMediaType() == mediaType;

            boolean genreMatch = (selectedGenre == null || selectedGenre.equalsIgnoreCase("All")) ||
                    (m.getMediaGenres() != null && m.getMediaGenres().stream()
                            .anyMatch(cat -> cat.getCategoryName().equalsIgnoreCase(selectedGenre)));

            if(nameMatch && typeMatch && genreMatch){
                searchResult.add(m.getID());
                results.getChildren().add(buildCard(m));
            }
        }

    }

    private CardLayout buildCard(Media m) {
        CardLayout card = new CardLayout(m.getMediaName(), m.getIconPath());

        card.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {

                if (onMediaSelected != null) {
                    onMediaSelected.accept(m.getID());
                }
                //We switch scenes here
            }
        });
        return card;
    }

    private ArrayList<String> getGenreList(String type){
        switch (type){
            case "Book":
                return bookGenre;

            case "Film":
                return movieGenre;

            case "TV Show":
                return tvShowGenre;

            case "Game":
                return gameGenre;
        }
        return null;
    }

}