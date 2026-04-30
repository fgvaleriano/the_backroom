package edu.tangingina.thebackroom.controller.dashboard;

import edu.tangingina.thebackroom.TheBackroom;
import edu.tangingina.thebackroom.controller.AddArchive_v2;
import edu.tangingina.thebackroom.controller.LoginController;
import edu.tangingina.thebackroom.controller.UpdateArchive;
import edu.tangingina.thebackroom.util.MediaItem;
import javafx.geometry.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.List;

public class DashboardShell extends BorderPane {
    /*
        Shell for the dashboard, everything is called here
     */

    private VBox contentArea;
    private NavbarComponent navBar;
    private ScrollPane scrollPane;
    private StackPane center;
    private Image noiseImg;

    public DashboardShell() {
        //this.setTop(new NavbarComponent());             //navigation bar will always be on top

        //this.setPrefWidth(1900);
        //this.setPrefHeight(1080);

        //navigation bar thing
        navBar = new NavbarComponent(
                () -> setView(new DashboardHomeView()),
                () -> setView(new MediaCategoryView("Books")),
                () -> setView(new MediaCategoryView("Games")),
                () -> setView( new MediaCategoryView("Movie", "TvShow")));


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

        setView(new DashboardHomeView());
    }

    //for the modular switcher
    public void setView(BaseView view) {
        javafx.application.Platform.runLater(() -> {
            contentArea.getChildren().clear();
            contentArea.getChildren().add(view.getView());
        });

    }

    private void openAddArchiveDialog() {
        AddArchive_v2.addArchiveView();
    }

    private void openImportDialog() { ImportDialog.importDialogView(); }

    //private void openExportDialog() { ExportDialog.exportDialogView(); }

    //temporary for update testing
    private void openExportDialog() {
        UpdateArchive.updateArchiveView(5, "Book");
    }

    private void logout() {
        TheBackroom.sm.showLogin();

        if(TheBackroom.currUser.getRole().equals("MODERATOR")){
            //If the prev user was a moderator, then if logged out we change the mysql account
            TheBackroom.dm.resetConnection();
        }

        TheBackroom.currUser = null;

        //=====================>>>>Do the showing of medias na on the front page and for each yes <<<<=====================

        /*
        Stage stage = (Stage) this.getScene().getWindow();
        boolean maxSize = stage.isMaximized();

        double currentWid = stage.getWidth();
        double currentHi = stage.getHeight();

        LoginController logout = new LoginController();
        StackPane loginRoot = logout.getLayout(stage);

        Scene loginScene = new Scene(loginRoot, currentWid, currentHi);
        stage.setScene(loginScene);
        stage.show();
        stage.setResizable(false);
        loginScene.getStylesheets().add(getClass().
                getResource("/edu/tangingina/thebackroom/the_backroom_style.css").toExternalForm());
        stage.setScene(loginScene);
        stage.setMaximized(maxSize);
        */
    }

    private void login(){
        TheBackroom.sm.showLogin();
    }
}

