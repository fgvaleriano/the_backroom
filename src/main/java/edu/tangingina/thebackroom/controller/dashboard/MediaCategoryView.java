package edu.tangingina.thebackroom.controller.dashboard;

import edu.tangingina.thebackroom.controller.dashboard.Media_Details.BaseMedia;
import edu.tangingina.thebackroom.util.BaseView;
import javafx.geometry.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class MediaCategoryView extends BaseView {
    /*
        Resuable for different types of media
     */

    private final String mediaType;
    private final List<BaseMedia> items;
    private final Consumer<BaseMedia> onMediaClick;

    public MediaCategoryView(String mediaType, List<BaseMedia> items, Consumer<BaseMedia> onMediaClick) {
        this.mediaType = mediaType;
        this.items = items;
        this.onMediaClick = onMediaClick;
        buildLayout();
    }


    @Override
    protected void buildLayout() {
        root.getStyleClass().add("media-category-view");
        root.setAlignment(Pos.TOP_LEFT);
        root.setSpacing(25);
        root.setPadding(new Insets(45, 125, 60, 125));

        Map<String, List<BaseMedia>> groupedByGenre = items.stream()
                .filter(item -> item.getType().equalsIgnoreCase(mediaType))
                .collect(Collectors.groupingBy(
                        BaseMedia::getGenre,
                        LinkedHashMap::new,
                        Collectors.toList()
        ));

        for (Map.Entry<String, List<BaseMedia>> entry : groupedByGenre.entrySet()) {
            String genre = entry.getKey();
            List<BaseMedia> mediaList = entry.getValue();

            MediaSection section = new MediaSection(genre);

            for (BaseMedia media : mediaList) {
                section.addCard(
                        media,
                        () -> onMediaClick.accept(media)
                );
            }

            root.getChildren().add(section);
        }
    }

    @Override
    public javafx.scene.Node getView() {
        return root;
    }
}
