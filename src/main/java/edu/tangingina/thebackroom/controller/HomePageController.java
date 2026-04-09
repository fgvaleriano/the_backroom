package edu.tangingina.thebackroom.controller;

import edu.tangingina.thebackroom.util.FontLoader;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.stage.*;

import java.net.URL;
import java.util.Objects;

public class HomePageController {
    StackPane root;
    Image image;
    ImageView imageView, titleView, logoView, titleImage, logoImage;
    VBox titleBox;
    HBox navBar, navBtns;
    Button homeBtn, bkBtn, gmBtn, fmBtn, prfBtn, searchBar;
    //TextField searchBar;

    //loading fonts
    Font bold = FontLoader.bold(27);

    public StackPane getLayout (Stage stage) {
        root = new StackPane();
        root.getStyleClass().add("home-background");
        titleImage = createTitle();

        StackPane.setAlignment(titleImage, Pos.CENTER);
        titleImage.setTranslateY(-150);

        navBar = createNavBar();
        StackPane.setAlignment(navBar, Pos.CENTER);
        navBar.setTranslateY(-335);

        root.getChildren().addAll(titleImage, navBar);

        return root;
    }

    private ImageView createTitle() {
        URL url = getClass().getResource("/edu/tangingina/thebackroom/assets/title.png");

        //tells you if javafx cannot locate the file
        if (url == null) {
            throw new RuntimeException("Resource not found");
        }

        image = new Image(url.toExternalForm());
        imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);

        imageView.setFitWidth(500);
        return imageView;
    }

    //creating main title
    private VBox createTitleSection() {
        titleView = createTitle();
        titleBox = new VBox(titleView);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setPadding(new Insets(35, 0, 35, 0));
        return titleBox;
    }

    //for the navigation bar
    private HBox createNavBar() {
        navBar = new HBox();
        navBar.setAlignment(Pos.CENTER_LEFT);
        navBar.setPadding(new Insets(20, 40, 10, 40));
        navBar.setSpacing(70);

        logoImage = createLogo();
        navBtns = createNavBtns();
        searchBar = createSearchBar();
        prfBtn = createProfileBtn();

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        navBar.getChildren().addAll(logoImage, navBtns, spacer, searchBar, prfBtn);
        return navBar;
    }

    //creating buttons for navigation bar
    private HBox createNavBtns() {
        homeBtn = new Button("Home");
        homeBtn.setFont(bold);

        bkBtn = new Button("Books");
        bkBtn.setFont(FontLoader.bold(27));

        gmBtn = new Button("Games");
        gmBtn.setFont(FontLoader.bold(27));

        fmBtn = new Button("Films and TV Shows");
        fmBtn.setFont(FontLoader.bold(27));

        homeBtn.getStyleClass().add("btn-primary");
        bkBtn.getStyleClass().add("btn-primary");
        gmBtn.getStyleClass().add("btn-primary");
        fmBtn.getStyleClass().add("btn-primary");

        //action listeners for nav bar buttons
        homeBtn.setOnAction(e -> {
            System.out.println("Home");
        });
        bkBtn.setOnAction( e -> {
            System.out.println("Go to books archive");
        });
        gmBtn.setOnAction(e -> {
            System.out.println("Go to Games archive");
        });
        fmBtn.setOnAction( e -> {
            System.out.println("Go to films and tv shows archive");
        });

        navBtns = new HBox(50, homeBtn, bkBtn, gmBtn, fmBtn);
        navBtns.setAlignment(Pos.CENTER_LEFT);

        return navBtns;
    }

    //creating serach bar
    private Button createSearchBar() {
        searchBar = new Button("Search...");
        searchBar.getStyleClass().add("search-button");
        searchBar.setPrefWidth(200);
        searchBar.setPrefHeight(40);

        //directs user to search scene
        searchBar.setOnAction(e -> {
            System.out.println("Open search page");
        });
        return searchBar;
    }

    //profile buton
    private Button createProfileBtn() {
        prfBtn = new Button();
        prfBtn.getStyleClass().add("profile-button");
        return prfBtn;
    }

    //small logo
    private ImageView createLogo() {
        URL url = getClass().getResource("/edu/tangingina/thebackroom/assets/tbr.png");
        if (url == null) {
            System.out.println("Cannot find: " + url);
        }

        image = new Image(url.toExternalForm());
        imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setFitWidth(125);
        return imageView;
    }

}
