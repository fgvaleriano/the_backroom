package edu.tangingina.thebackroom.controller.dashboard;

import edu.tangingina.thebackroom.TheBackroom;
import edu.tangingina.thebackroom.model.Media;
import edu.tangingina.thebackroom.util.MediaItem;
import javafx.geometry.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class MediaCategoryView extends BaseView{
    /*
        Resuable for different types of media
     */

    private final List<String> types;
    private Consumer<Integer> onMediaSelected;

    public MediaCategoryView(String mediaType, Consumer<Integer> onMediaSelected) {
        this.onMediaSelected = onMediaSelected;
        this.types = List.of(mediaType);
        buildLayout();
    }

    public MediaCategoryView(String type1, String type2, Consumer<Integer> onMediaSelected) {
        this.types = List.of(type1, type2);
        this.onMediaSelected = onMediaSelected;
        buildLayout();
    }

    @Override
    protected void buildLayout() {
        root.getStyleClass().add("media-category-view");
        root.setAlignment(Pos.TOP_LEFT);
        root.setSpacing(25);
        root.setPadding(new Insets(45, 125, 60, 125));

        List<Integer> targetMedia;
        String firstType = types.get(0);

        Map<String, List<Media>> groupedByGenre = null;
        ArrayList<String> topGenre = null;
        String name;

        if (this.types.size() > 1) {
            //From these list it only contains id, name, mediaType, icon
            targetMedia = TheBackroom.videoMedia; // TV + Movie
            topGenre = TheBackroom.top6VidGenre;
            name = "All Films and Tv Shows";
        } else if (firstType.equalsIgnoreCase("Books")) {
            targetMedia = TheBackroom.bookMedia;
            topGenre = TheBackroom.top6BookGenre;
            name = "All Books";
        } else {
            targetMedia = TheBackroom.gameMedia;
            topGenre = TheBackroom.top6GameGenre;
            name = "All Games";
        }

        if(topGenre != null){
            groupedByGenre = topGenre.stream()
                    .collect(Collectors.toMap(
                            genreName -> genreName,
                            genreName -> targetMedia.stream()
                                    .map(tempMedia -> TheBackroom.mediaList.get(tempMedia))
                                    .filter(m -> m != null)
                                    .filter(m -> m.getMediaGenres() != null && m.getMediaGenres().stream()
                                            .anyMatch(cat -> cat.getCategoryName().equalsIgnoreCase(genreName)))
                                    .collect(Collectors.toList()),
                            (oldValue, newValue) -> oldValue,
                            LinkedHashMap::new
                    ));



            groupedByGenre.put(name, targetMedia.stream()
                    .map(temp -> TheBackroom.mediaList.get(temp))
                    .collect(Collectors.toList())
            );
        }

        if(groupedByGenre != null){
            for (Map.Entry<String, List<Media>> entry : groupedByGenre.entrySet()) {
                MediaSection section = new MediaSection(entry.getKey(), onMediaSelected);

                // Add each real Media object to the UI
                for (Media m : entry.getValue()) {
                    section.addCard(m.getID().toString(), m.getMediaIcon());
                }

                root.getChildren().add(section);
            }
        }
    }
}
