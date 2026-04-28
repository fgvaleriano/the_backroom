package edu.tangingina.thebackroom.controller.dashboard.Media_Details;

import edu.tangingina.thebackroom.controller.dashboard.CardLayout;
import edu.tangingina.thebackroom.controller.dashboard.ImportDialog;
import edu.tangingina.thebackroom.util.FontLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;


public class MediaDetailsPage extends VBox {
    /*
        Reusable detail page for any media
     */

    private final BaseMedia media;
    private final Runnable onBack;
    private final Runnable onUpdate;

    private VBox card, leftSide;
    private HBox actionBar, contentLayout;
    private MediaInfoPane infoPane;
    private final ExternalLink externalLink = new ExternalLink();

    public MediaDetailsPage (BaseMedia media, Runnable onBack, Runnable onUpdate) {
        this.media = media;
        this.onBack = onBack;
        this.onUpdate = onUpdate;

        //loadStyleSheet();
        buildPage();
    }

    private void buildPage() {
        getStyleClass().add("detail-page");
        setAlignment(Pos.TOP_CENTER);

        card = new VBox(25);
        card.getStyleClass().add("detail-card");
        card.setMaxWidth(1150);

        actionBar = createActionbar();
        leftSide = createLeftSide(media);

        contentLayout = new HBox(90);
        contentLayout.getStyleClass().add("content-layout");
        contentLayout.setAlignment(Pos.TOP_CENTER);

        //left side of the details page
        //coverpane = new MediaCoverPane(media, imagePathResolver, externalLinkService);
        infoPane = new MediaInfoPane(media);

        contentLayout.getChildren().addAll(leftSide, infoPane);

        card.getChildren().addAll(actionBar, contentLayout);
        getChildren().add(card);
    }

    private HBox createActionbar() {
        actionBar = new HBox();
        actionBar.getStyleClass().add("action-bar");
        actionBar.setAlignment(Pos.CENTER_LEFT);

        Button back = new Button("<Back");
        back.setFont(FontLoader.bold(15));
        back.getStyleClass().add("btn-guest");
        back.setOnAction(e -> {
           System.out.println("Go back");

           if (onBack != null) {
               onBack.run();
           }
        });

        Button update = new Button("Update");
        update.getStyleClass().add("btn-guest");
        update.setFont(FontLoader.bold(15));
        update.setOnAction(e -> {
            System.out.println("Update media: " + media);
        });

        Region spacce = new Region();
        HBox.setHgrow(spacce, Priority.ALWAYS);

        actionBar.getChildren().addAll(back, spacce, update);
        return actionBar;
    }

    private VBox createLeftSide(BaseMedia media) {
        leftSide = new VBox(28);
        leftSide.getStyleClass().add("left-side");

        CardLayout cover = new CardLayout(media.getTitle(), media.getImagePath(), 250, 390, false);

        Button btn = new Button();
        btn.getStyleClass().add("image-button");
        Image img = new Image(ImportDialog.class.getResourceAsStream(
                "/edu/tangingina/thebackroom/assets/access_btn.png"));
        ImageView view = new ImageView(img);
        view.setPreserveRatio(true);
        view.setFitWidth(60);
        btn.setGraphic(view);

        btn.setOnAction(e -> {
            System.out.println("Open links");
            externalLink.openLinks(media.getAccessLinks());
        });

        leftSide.getChildren().addAll(cover, btn);
        return leftSide;
    }
}
