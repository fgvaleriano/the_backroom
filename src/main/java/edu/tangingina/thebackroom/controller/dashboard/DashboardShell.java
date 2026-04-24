package edu.tangingina.thebackroom.controller.dashboard;

import javafx.geometry.*;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.control.*;

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
    private List<MediaItem> mediaItems = MediaRepository.getAllMedia();

    public DashboardShell() {
        //this.setTop(new NavbarComponent());             //navigation bar will always be on top

        //navigation bar thing
        navBar = new NavbarComponent(
                () -> setView(new DashboardHomeView()),
                () -> setView(new MediaCategoryView("Books", mediaItems)),
                () -> setView(new MediaCategoryView("Games", mediaItems)),
                () -> setView( new MediaCategoryView("Films and TV Shows", mediaItems))
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

        setView(new DashboardHomeView());
    }

    //for the modular switcher
    public void setView(BaseView view) {
        contentArea.getChildren().clear();
        contentArea.getChildren().add(view.getView());
    }
}

//temporary data provider for image paths
class MediaRepository {

    public static List<MediaItem> getAllMedia() {
        return List.of(
                new MediaItem("Addie LaRue", "/assets/addie.png", "Fiction", "Books"),
                new MediaItem("The Silent Patient", "/assets/patient.png", "Dark", "Books"),
                new MediaItem("Genshin Impact", "/assets/genshin.png", "Open World", "Games")
        );
    }
}
