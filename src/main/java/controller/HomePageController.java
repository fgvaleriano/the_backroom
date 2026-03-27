package controller;

import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.stage.*;

import java.net.URL;
import java.util.Objects;

public class HomePageController {
    StackPane root;
    Image image;
    ImageView imageView, titleView, logoView, titleImage;
    VBox titleBox;
    HBox navBar;
    Button bkBtn, gmBtn, fmBtn, prfBtn, searchBar;
    //TextField searchBar;

    public StackPane getLayout (Stage stage) {
        root = new StackPane();
        root.getStyleClass().add("home-background");
        titleImage = createTitle();

        StackPane.setAlignment(titleImage, Pos.CENTER);
        titleImage.setTranslateY(-150);

        navBar = new HBox();

        root.getChildren().addAll(titleImage, navBar);

        return root;
    }

    private ImageView createTitle() {
        URL url = getClass().getResource("/ui/assets/title.png");

        //tells you if javafx cannot locate the file
        if (url == null) {
            throw new RuntimeException("Resource not found");
        }

        image = new Image(url.toExternalForm());
        imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);

        imageView.setFitWidth(600);
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
        navBar.setSpacing(30);
        searchBar = createSearchBar();

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        navBar.getChildren().addAll(spacer, searchBar);
        return navBar;
    }

    /*
    //creating buttons for navigation bar
    private Button createNavBtns(String text) {

    }*/

    //creating serach bar
    private Button createSearchBar() {
        searchBar = new Button();
        searchBar.getStyleClass().add("search-button");
        searchBar.setText("Search...");
        searchBar.setPrefWidth(90);
        searchBar.setPrefHeight(30);

        //directs user to search scene
        searchBar.setOnAction(e -> {
            System.out.println("Open search page");
        });
        return searchBar;
    }
    /*
    //profile buton
    private Button createProfileBtn() {

    }

    //small logo
    private ImageView createLogo() {

    }*/

}
