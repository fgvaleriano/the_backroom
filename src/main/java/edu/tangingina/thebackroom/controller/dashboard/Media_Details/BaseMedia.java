package edu.tangingina.thebackroom.controller.dashboard.Media_Details;

import java.util.List;

public abstract class BaseMedia {
    /*
        Reusable detail page for any media
            - used so that hard coding each media details page is not needed
     */

    private final int id;
    private final String title;
    private final String type;
    private final String synopsis;
    private final int releaseYear;
    private final String imagePath;
    private final List<String> accessLinks;

    protected BaseMedia (int id, String type, String title, String synopsis,
                                int releaseYear, String imagePath, List<String> accessLinks) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.synopsis = synopsis;
        this.releaseYear = releaseYear;
        this.imagePath = imagePath;
        this.accessLinks = accessLinks;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public String getSynopsis() {
        return synopsis;
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
}
