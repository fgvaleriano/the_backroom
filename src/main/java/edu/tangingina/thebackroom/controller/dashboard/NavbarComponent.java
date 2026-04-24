package edu.tangingina.thebackroom.controller.dashboard;

import edu.tangingina.thebackroom.util.FontLoader;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.net.URL;

public class NavbarComponent extends HBox {
    /*
        Handles navigation bar implementation and logic
     */

    private ImageView logo;
    private Region spacer;
    private HBox btnHolder;
    private Button homeBtn, booksBtn, gamesBtn, filmsBtn;

    public NavbarComponent(Runnable onHome, Runnable onBooks, Runnable onGames, Runnable onFilms) {
        this.setAlignment(Pos.CENTER_LEFT);
        this.setPadding(new Insets(15, 30, 15, 30));
        this.setSpacing(40);
        this.getStyleClass().add("nav-bar");

        //logo
        URL url = getClass().getResource("/edu/tangingina/thebackroom/assets/tbr.png");
        if (url == null) {
            System.out.println("Cannot find: " + url);
        }
        Image img = new Image(url.toExternalForm());
        logo = new ImageView(img);
        logo.setFitWidth(100);
        logo.setPreserveRatio(true);

        //navigation buttons
        homeBtn = createBtns("Home", true);
        booksBtn = createBtns("Books", false);
        gamesBtn = createBtns("Games", false);
        filmsBtn = createBtns("Films and TV Shows", false);

        homeBtn.setOnAction(e -> {
            System.out.println("Home clicked");
            setActive(homeBtn);
            onHome.run();
        });
        booksBtn.setOnAction(e -> {
            System.out.println("Books clicked");
            setActive(booksBtn);
            onBooks.run();
        });
        gamesBtn.setOnAction(e -> {
            System.out.println("Games clicked");
            setActive(gamesBtn);
            onGames.run();
        });
        filmsBtn.setOnAction(e -> {
            System.out.println("filmsBtns clicked");
            setActive(filmsBtn);
            onFilms.run();
        });

        btnHolder = new HBox(18, homeBtn, booksBtn, gamesBtn, filmsBtn);
        btnHolder.setAlignment(Pos.CENTER_LEFT);

        setActive(homeBtn);             //default active buttons

        //search bar and profile
        Button searchBtn = new Button("Search...");
        searchBtn.setFont(FontLoader.regular(15));
        searchBtn.setPrefWidth(200);
        searchBtn.getStyleClass().add("search-btn");

        Circle profileCircle = new Circle(15, Color.web("#637991"));

        spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        this.getChildren().addAll(logo, btnHolder, spacer, searchBtn, profileCircle);
    }

    private Button createBtns (String text, boolean isActive) {
        Button btn = new Button(text);
        btn.setFont(FontLoader.extra(25));
        btn.getStyleClass().add("nav-btn");

        return btn;
    }

    private void setActive(Button activeBtn) {
        Button[] buttons = new Button[]{homeBtn, booksBtn, gamesBtn, filmsBtn};

        for (Button butn : buttons) {
            butn.getStyleClass().removeAll("nav-btn", "nav-btn-active");
            butn.getStyleClass().add("nav-btn");
        }

        activeBtn.getStyleClass().remove("nav-btn");
        activeBtn.getStyleClass().add("nav-btn-active");
    }
}
