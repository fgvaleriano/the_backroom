package edu.tangingina.thebackroom.controller.dashboard;

import edu.tangingina.thebackroom.controller.dashboard.Media_Details.BaseMedia;
import edu.tangingina.thebackroom.controller.dashboard.Media_Details.SampleMediaData;
import edu.tangingina.thebackroom.util.BaseView;
import javafx.geometry.*;
import javafx.scene.image.*;

import javax.print.attribute.standard.Media;
import java.util.List;
import java.util.function.Consumer;

public class DashboardHomeView extends BaseView {
    /*
        Responsible for showing dashboard content of the home page
     */

    ImageView titleLogo, logo;
    MediaSection books, games, films;
    private List<BaseMedia> mediaItems = SampleMediaData.getAllMedia();

    //private final List<BaseMedia> mediaItems;
    private final Consumer<BaseMedia> onMediaClick;

    public DashboardHomeView(List<BaseMedia> mediaItems, Consumer<BaseMedia> onMediaClick){
        this.mediaItems = mediaItems;
        this.onMediaClick = onMediaClick;

        buildLayout();
    }

    @Override
    protected void buildLayout() {
        root.getStyleClass().add("dashboard-home");
        root.setAlignment(Pos.TOP_CENTER);

        titleLogo = createTitleLogo();

        books = new MediaSection("Books");
        for (BaseMedia media : mediaItems) {
            books.addCard(
                    media,
                    () -> onMediaClick.accept(media)
            );
        }

        games = new MediaSection("Games");
        //addMediatoSection(games, "games");

        films = new MediaSection("Films and TV Shows");
        //addMediatoSection(films, "Films and TV Shows");

        root.getChildren().addAll(titleLogo, books, games, films);

    }

    private void addMediatoSection(MediaSection section, String type) {
        for (BaseMedia media : mediaItems) {
            if (media.getType() != null && media.getType().equals(type)) {
                section.addCard(
                        media, () -> onMediaClick.accept(media)
                );
            }
        }
    }

    //creates the logo found at the top of the dashboard
    private ImageView createTitleLogo(){
        Image img = new Image(getClass().getResource(
                "/edu/tangingina/thebackroom/assets/title.png").toExternalForm());

        logo = new ImageView(img);
        logo.setFitWidth(460);
        logo.setPreserveRatio(true);
        logo.getStyleClass().add("big-logo");

        return logo;
    }
}
