package edu.tangingina.thebackroom.controller.dashboard;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.function.Consumer;

public class SearchView extends BaseView{
    /*
        This class handles the search UI
            - filters by media
     */

    private VBox resultSection;
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
        root.getChildren().addAll(createSearchBar(), createResultSection());
    }

    //creating search bar
    private HBox createSearchBar() {
        filter = new ComboBox<>();
        filter.getItems().addAll("All", "Book", "Game", "Film", "TV Show");
        filter.setValue("All");
        filter.getStyleClass().add("combo-box");
        filter.setPrefHeight(35);
    }

}
