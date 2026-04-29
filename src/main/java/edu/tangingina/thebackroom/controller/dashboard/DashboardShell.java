package edu.tangingina.thebackroom.controller.dashboard;

import edu.tangingina.thebackroom.controller.AddArchive_v2;
import edu.tangingina.thebackroom.controller.LoginController;
import edu.tangingina.thebackroom.controller.dashboard.Media_Details.BaseMedia;
import edu.tangingina.thebackroom.controller.dashboard.Media_Details.MediaDetailsPage;
import edu.tangingina.thebackroom.controller.dashboard.Media_Details.SampleMediaData;
import edu.tangingina.thebackroom.util.BaseView;
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

    //CHANGE THIS FOR FINAL IMPLEMENTATION, THIS IS ONLY FOR TESTING
    private List<BaseMedia> mediaItems = SampleMediaData.getAllMedia();

    public DashboardShell() {
        //this.setTop(new NavbarComponent());             //navigation bar will always be on top

        this.setPrefWidth(1900);
        this.setPrefHeight(1080);

        //navigation bar thing
        navBar = new NavbarComponent(
                this::showHomePage,
                () -> showCategoryPage("Books"),
                () -> showCategoryPage("Games"),
                () -> showCategoryPage("Films and TV Shows"),
                this::openAddArchiveDialog,
                this::openImportDialog,
                this::openExportDialog,
                this::logout
        );
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

        showHomePage();
    }

    //for the modular switcher
    public void setView(BaseView view) {
        setMainContent(view.getView());

    }

    private void showHomePage() {
        setView(new DashboardHomeView(mediaItems,
                media -> showMediaDetailPage(media, this::showHomePage)));
    }

    private void openAddArchiveDialog() {
        AddArchive_v2.addArchiveView();
    }

    private void openImportDialog() { ImportDialog.importDialogView(); }

    private void openExportDialog() { ExportDialog.exportDialogView(); }

    private void logout() {
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
    }

    /*private void showBooksPage() {
        BookPage bookPage = new BookPage(this::showMediaDetailPage);
        setMainContent(bookPage);
    }*/

    private void showCategoryPage(String category) {
        setView(new MediaCategoryView(
                category, mediaItems, media -> showMediaDetailPage(media, () -> showCategoryPage(category))
        ));
    }

    private void showMediaDetailPage(BaseMedia media, Runnable onBack) {
        MediaDetailsPage detailsPage = new MediaDetailsPage(
                media, onBack, () -> showUpdateMediaPage(media)
        );

        setMainContent(detailsPage);
    }

    private void setMainContent(javafx.scene.Node page) {
        contentArea.getChildren().setAll(page);
        scrollPane.setVvalue(0);
    }

    //temp update method
    private void showUpdateMediaPage(BaseMedia media) {
        System.out.println("Update clicked for: " + media.getTitle());
    }
}

