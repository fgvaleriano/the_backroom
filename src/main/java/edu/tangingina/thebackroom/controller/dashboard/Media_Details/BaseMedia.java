package edu.tangingina.thebackroom.controller.dashboard.Media_Details;

import java.util.*;

public abstract class BaseMedia {
    /*
        Base class for all media types

        contains fields that are common between medias
     */

    private final int id;
    private final MediaType type;
    private final String title, synopsis, imagePath;
    private final int releaseYear;
    private final List<String> accessLinks, categories;

    protected BaseMedia (int id, String title, MediaType type, String synopsis,
                                int releaseYear, String imagePath, List<String> categories, List<String> accessLinks) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.synopsis = synopsis;
        this.releaseYear = releaseYear;
        this.imagePath = imagePath;

        this.categories = categories == null
                ? new ArrayList<>()
                : new ArrayList<>(categories);

        this.accessLinks = accessLinks == null
                ? new ArrayList<>()
                : new ArrayList<>(accessLinks);
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public MediaType getType() {
        return type;
    }

    public String getTypeName() {
        return type.getDisplayName();
    }

    public String getSynopsis() {
        return synopsis;
    }

    public String getGenre() {
        if (categories.isEmpty()) {
            return "Uncategorized";
        }

        return categories.get(0);
    }

    public List<String> getCategories() {
        return Collections.unmodifiableList(categories);
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public String getImagePath() {
        return imagePath;
    }

    public List<String> getAccessLinks() {
        return accessLinks;
    }

    public abstract String getMainCreator();

    public abstract List<DetailField> getDetailFields();

    public boolean isBook() {
        return type == MediaType.BOOK;
    }

    public boolean isMovie() {
        return type == MediaType.MOVIE;
    }

    public boolean isTVShow() {
        return type == MediaType.TV_SHOW;
    }

    public boolean isGame() {
        return type == MediaType.GAME;
    }
}

