package edu.tangingina.thebackroom.controller.dashboard;

import edu.tangingina.thebackroom.TheBackroom;
import edu.tangingina.thebackroom.controller.AddArchive_v2;
import edu.tangingina.thebackroom.controller.LoginController;
import edu.tangingina.thebackroom.util.MediaItem;
import javafx.application.Platform;
import javafx.geometry.*;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.List;
import java.util.function.Consumer;

public class DashboardShell extends BorderPane {
    /*
        Shell for the dashboard, everything is called here
     */

    private VBox contentArea;
    private NavbarComponent navBar;
    private ScrollPane scrollPane;
    private StackPane center;
    private Image noiseImg;
    private Consumer<Integer> onMediaSelected;

    private double savedScrollPosition = 0.0;
    private boolean prevMediaDetails = false;

    private SearchView cachSearchView;

    public DashboardShell() {
        //this.setTop(new NavbarComponent());             //navigation bar will always be on top

        //this.setPrefWidth(1900);
        //this.setPrefHeight(1080);

        //navigation bar thing
        this.getStyleClass().add("dashboard-shell"); //a tag for this class for later refreshing....

        navBar = new NavbarComponent(
                () -> showHome(),
                () -> showCategory("Books"),
                () -> showCategory("Games"),
                () -> showCategory("Movie", "TvShow")
        );


        if(TheBackroom.currUser == null){
            navBar.setLogIn(this::login);
        }else if(TheBackroom.currUser.getRole().equals("MODERATOR")){
            navBar.setAdd(this::openAddArchiveDialog);
            navBar.setImport(this::openImportDialog);
            navBar.setExport(this::openExportDialog);
            navBar.setLogOut(this::logout);
        }else{
            navBar.setLogOut(this::logout);
        }
        //we need to verify first the current user for the userDropdown, before creating the profile dropdown
        navBar.setProfileDropdown();
        navBar.setSearch(() -> showSearch(false));

        StackPane navWrapper = new StackPane(navBar);
        navWrapper.setPadding(new Insets(0, 0, 10, 0));
        this.setTop(navWrapper);

        //main content holder but not scrollable
        contentArea = new VBox();
        contentArea.setAlignment(Pos.TOP_CENTER);
        contentArea.setBackground(Background.EMPTY);
        //contentArea.getStyleClass().add("main-content-bkg");

        //scrollable content
        scrollPane = new ScrollPane(contentArea);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.getStyleClass().add("dashboard-scroll");
        scrollPane.setBackground(Background.EMPTY);

        //fixed center bkg (noise)
        center = new StackPane();
        Region noise = new Region();
        noise.getStyleClass().add("main-content-bkg");

        noiseImg = new Image(getClass().getResource("/edu/tangingina/thebackroom/assets/noise.png")
                .toExternalForm());

        BackgroundImage noiseBkg = new  BackgroundImage(noiseImg, BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

        Region noiseLayer = new Region();
        noiseLayer.setBackground(new Background(noiseBkg));
        noiseLayer.setOpacity(0.15);
        noiseLayer.setMouseTransparent(true);

        center.getChildren().addAll(noise, noiseLayer, scrollPane);

        this.setCenter(center);
        showHome();
    }

    //for the modular switcher
    public void setView(BaseView view) {
        //this is for when we go to a media and go back, the scroll position we have before would remain...
        if (!contentArea.getChildren().isEmpty() && !prevMediaDetails) {
            double currentPos = scrollPane.getVvalue(); //we only save if the user actually moved the scrollbar
            if (currentPos > 0) {
                savedScrollPosition = currentPos;
            }
        }

            contentArea.getChildren().clear();
            Node viewNode = view.getView();
            VBox.setVgrow(viewNode, Priority.ALWAYS);

            contentArea.getChildren().add(viewNode);

        Platform.runLater(() -> {
            if (view instanceof MediaDetailsView) {
                prevMediaDetails = true;
                scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
                contentArea.setPrefHeight(Region.USE_COMPUTED_SIZE);
                scrollPane.setVvalue(0.0);
            } else {
                scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
                contentArea.prefHeightProperty().unbind();
                contentArea.setPrefHeight(Region.USE_COMPUTED_SIZE);

                if (prevMediaDetails) {
                    scrollPane.setVvalue(savedScrollPosition);
                    savedScrollPosition = 0.0;
                    prevMediaDetails = false;
                } else {
                    scrollPane.setVvalue(0.0);
                }
            }
        });

    }

    public void refreshCurrentView(){
        String current = NavbarComponent.currentView;

        if(prevMediaDetails){
            showHome();
            prevMediaDetails = false;
        }

        switch (current) {
            case "Home" -> showHome();
            case "Book" -> showCategory("Books");
            case "Game" -> showCategory("Games");
            case "Film/TvShow" -> showCategory("Movie", "TvShow");
        }
    }

    private void openAddArchiveDialog() {
        AddArchive_v2.addArchiveView();
    }

    private void openImportDialog() { ImportDialog.importDialogView(); }

    private void openExportDialog() { ExportDialog.exportDialogView(); }

    private void showSearch(boolean cacheView) {

        if(!cacheView || cachSearchView == null){
            cachSearchView = new SearchView(id -> openMediaDetails(id, () -> showSearch(true)));
        }

        setView(cachSearchView);
    }

    private void logout() {
        TheBackroom.sm.showLogin();

        if(TheBackroom.currUser.getRole().equals("MODERATOR")){
            //If the prev user was a moderator, then if logged out we change the mysql account
            TheBackroom.dm.resetConnection();
        }

        TheBackroom.currUser = null;
    }

    private void login(){
        TheBackroom.sm.showLogin();
    }

    private void openMediaDetails(int mediaID, Runnable onBack){
        MediaDetailsView mediaDetails = new MediaDetailsView(mediaID, onBack);
        setView(mediaDetails);
    }

    private void showHome() {
        setView(new DashboardHomeView(id -> openMediaDetails(id, this::showHome)));
    }

    private void showCategory(String type) {
        setView(new MediaCategoryView(type, id -> openMediaDetails(id, () -> showCategory(type))));
    }

    private void showCategory(String type1, String type2) {
        setView(new MediaCategoryView(type1, type2, id -> openMediaDetails(id, () -> showCategory(type1, type2))));
    }
}

