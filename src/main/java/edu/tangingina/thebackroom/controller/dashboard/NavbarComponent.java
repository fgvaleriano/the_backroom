package edu.tangingina.thebackroom.controller.dashboard;

import edu.tangingina.thebackroom.util.FontLoader;
import javafx.application.Platform;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

import java.net.URL;

public class NavbarComponent extends HBox {
    /*
        Handles navigation bar implementation and logic
     */

    private ImageView logo;
    private Region spacer;
    private HBox btnHolder;
    private Button homeBtn, booksBtn, gamesBtn, filmsBtn;
    private MenuItem addBtn, exportBtn, importBtn,logoutBtn;
    private final Runnable onAdd, onLogout, onExport, onImport;

    public NavbarComponent(Runnable onHome, Runnable onBooks, Runnable onGames, Runnable onFilms,
                           Runnable onAdd, Runnable onImport, Runnable onExport, Runnable onLogout) {
        //for switching stages or opening dialog boxes
        this.onAdd = onAdd;
        this.onImport = onImport;
        this.onExport = onExport;
        this.onLogout = onLogout;

        this.setAlignment(Pos.CENTER_LEFT);
        this.setPadding(new Insets(15, 30, 15, 30));
        this.setSpacing(40);
        this.getStyleClass().add("nav-bar");
        this.setPickOnBounds(false);

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

        /*Circle profileCircle = new Circle(15);
        profileCircle.getStyleClass().add("profile-circle");*/

        spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        StackPane profileDropdown = createProfileDropdown();

        this.getChildren().addAll(logo, btnHolder, spacer, searchBtn, profileDropdown);
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

    //for the dropdown thing when clicking the profile circle
    private StackPane createProfileDropdown() {
        StackPane wrapper = new StackPane();

        wrapper.setPrefWidth(120);
        wrapper.setAlignment(Pos.CENTER);

        Circle profileCircle = new Circle(20);
        profileCircle.getStyleClass().add("profile-circle");

        ContextMenu profileMenu = new ContextMenu();
        profileMenu.getStyleClass().add("nav-dropdown");

        addBtn = createDropdownButton("Add");
        addBtn.setOnAction(e -> {
            System.out.println("Opening add archive diaglog");
            profileMenu.hide();
            Platform.runLater(() -> {if (onAdd != null) onAdd.run();});
        });

        importBtn = createDropdownButton("Import");
        importBtn.setOnAction(e -> {
           System.out.println("Importing archive");
           profileMenu.hide();
           Platform.runLater(
                   () -> {if (onImport != null) onImport.run(); }
           );
        });

        exportBtn = createDropdownButton("Export");
        exportBtn.setOnAction(e-> {
            System.out.println("Exporting archive");
            profileMenu.hide();
            Platform.runLater(
                    () -> {if (onExport != null) onExport.run(); }
            );
        });

        logoutBtn = createDropdownButton("Logout");
        logoutBtn.setOnAction(e -> {
           System.out.println("Logging out");

           if (onLogout != null) {
               onLogout.run();
           }
        });

        profileMenu.getItems().addAll(addBtn, importBtn, exportBtn, new SeparatorMenuItem(), logoutBtn);

        profileCircle.setOnMouseClicked(e -> {
           profileMenu.show(profileCircle, Side.BOTTOM, 0, 5);
        });

        wrapper.getChildren().add(profileCircle);

        StackPane.setAlignment(profileCircle, Pos.CENTER);

        return wrapper;
    }

    private MenuItem createDropdownButton(String text) {
        Label btnText = new Label(text);
        btnText.setFont(FontLoader.bold(18));
        MenuItem btn = new MenuItem();
        btn.setGraphic(btnText);

        btn.getStyleClass().add("dropdown-btn");
        return btn;
    }
}
