package edu.tangingina.thebackroom.controller.dashboard;

import edu.tangingina.thebackroom.util.MediaItem;
import javafx.geometry.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MediaCategoryView extends BaseView{
    /*
        Resuable for different types of media
     */

    private final String mediaType;
    private final List<MediaItem> items;

    public MediaCategoryView(String mediaType, List<MediaItem> items) {
        this.mediaType = mediaType;
        this.items = MediaRepository.getAllMedia();
        buildLayout();
    }

    @Override
    protected void buildLayout() {
        root.getStyleClass().add("media-category-view");
        root.setAlignment(Pos.TOP_LEFT);
        root.setSpacing(25);
        root.setPadding(new Insets(45, 125, 60, 125));

        Map<String, List<MediaItem>> groupedByGenre = items.stream()
                .filter(item -> item.getMediaType().equalsIgnoreCase(mediaType))
                .collect(Collectors.groupingBy(
                        MediaItem::getGenre,
                        LinkedHashMap::new,
                        Collectors.toList()
        ));

        for (Map.Entry<String, List<MediaItem>> entry : groupedByGenre.entrySet()) {
            MediaSection section = new MediaSection(entry.getKey());
            section.addCards(entry.getValue()); // <-- this line fixes it
            root.getChildren().add(section);
        }
    }
}
