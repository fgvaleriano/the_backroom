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

    ImageView logo;
    Region spacer;
    HBox btnHolder;

    public NavbarComponent() {
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
        btnHolder = new HBox(18, createBtns("Home", true),
                createBtns("Books", false),
                createBtns("Games", false),
                createBtns("Films and TV Shows", false)
        );
        btnHolder.setAlignment(Pos.CENTER_LEFT);

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
        btn.getStyleClass().add(isActive ? "nav-btn-active" : "nav-btn");
        return btn;
    }
}
